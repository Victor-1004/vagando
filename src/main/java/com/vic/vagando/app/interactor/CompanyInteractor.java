package com.vic.vagando.app.interactor;

import com.vic.vagando.app.domain.PageModel;
import com.vic.vagando.app.domain.company.Company;
import com.vic.vagando.app.domain.filter.ApplicationsCompanyFilter;
import com.vic.vagando.app.domain.job.input.CompanyJobInput;
import com.vic.vagando.app.domain.company.input.UpdateCompanyInput;
import com.vic.vagando.app.domain.job.Job;
import com.vic.vagando.app.domain.job.JobSkills;
import com.vic.vagando.app.domain.job.output.JobOutput;
import com.vic.vagando.app.domain.ouput.ApplicationsCompanyOutput;
import com.vic.vagando.app.domain.ouput.CandidateOutput;
import com.vic.vagando.app.domain.ouput.Dashboard;
import com.vic.vagando.app.exception.BusinessException;
import com.vic.vagando.app.exception.EntityNotFoundException;
import com.vic.vagando.app.exception.ProfileException;
import com.vic.vagando.app.gateway.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class CompanyInteractor {
    private final CompanyGateway companyGateway;
    private final AppGateway appGateway;
    private final JobGateway jobGateway;
    private final SkillsGateway skillsGateway;
    private final ApplicationsGateway applicationsGateway;

    public CompanyInteractor(CompanyGateway companyGateway, AppGateway appGateway, JobGateway jobGateway, SkillsGateway skillsGateway, ApplicationsGateway applicationsGateway) {
        this.companyGateway = companyGateway;
        this.appGateway = appGateway;
        this.jobGateway = jobGateway;
        this.skillsGateway = skillsGateway;
        this.applicationsGateway = applicationsGateway;
    }

    public Company update(UpdateCompanyInput input){
        if(input.getNome() == null && input.getDescricao() == null){
            throw new BusinessException("At least one field must be provided for update");
        }
        Company company = getCompany();
        company.setName(input.getNome() != null ? input.getNome() : company.getName());
        company.setDescription(input.getDescricao() != null ? input.getDescricao() : company.getDescription());
        return companyGateway.save(company);
    }

    public JobOutput createJob(CompanyJobInput input){
        Company company = getCompany();
        Job job = input.toDomain();
        job.setCompany(company);
        job.setCreatedAt(appGateway.getCurrentDateTime());
        job.setActive(true);
        Set<JobSkills> jobSkillsSet = new HashSet<>();
        for(UUID skillId : input.getSkills()){
            JobSkills jobSkills = new JobSkills();
            jobSkills.setJob(job);
            var skill = skillsGateway.findById(skillId)
                    .orElseThrow(() -> new EntityNotFoundException("Skill with id " + skillId + " not found"));
            jobSkills.setSkill(skill);
            jobSkillsSet.add(jobSkills);
        }
        job.setSkills(jobSkillsSet);
        Job savedJob = jobGateway.createJob(job);
        JobOutput output = new JobOutput();
        return output.fromDomain(savedJob);
    }

    public PageModel<JobOutput> getJobs(int page, int size, String title){
        Company company = getCompany();
        PageModel<Job> jobs = jobGateway.getCompanyJobs(company.getId(), page, size, title);
        return jobs.map(job -> new JobOutput().fromDomain(job));
    }


    public Company getCompany(){
        String email = appGateway.getLoggedUserEmail();
        return companyGateway.findByUserEmail(email).orElseThrow(() -> new ProfileException("Company not found"));
    }

    public PageModel<ApplicationsCompanyOutput> getApplicationsToCompanyJobs(ApplicationsCompanyFilter filter, int page, int size){
        Company company = getCompany();
        return applicationsGateway.findApplicationsToCompanyJobs(company.getId(), filter != null && filter.getJob() != null ? filter.getJob() : null, filter != null && filter.getStatus() != null ? filter.getStatus().name() : null, page, size)
                .map(applications -> {
                    ApplicationsCompanyOutput output = new ApplicationsCompanyOutput();
                    output.setId(applications.getId());
                    output.setCreatedAt(applications.getCreatedAt());
                    output.setStatus(applications.getStatus());
                    output.setScore(applications.getScore());
                    JobOutput jobOutput = new JobOutput();
                    jobOutput.setJobId(applications.getJob().getId());
                    jobOutput.setTitle(applications.getJob().getTitle());
                    jobOutput.setSkills(applications.getJob().getSkills().stream().map(js -> {
                        JobOutput.JobSkillOutput  jobSkillOutput = new JobOutput.JobSkillOutput();
                        jobSkillOutput.setId(js.getSkill().getId());
                        jobSkillOutput.setName(js.getSkill().getName());
                        return jobSkillOutput;
                    }).collect(Collectors.toSet()));
                    output.setJob(jobOutput);
                    CandidateOutput candidateOutput = new CandidateOutput();
                    candidateOutput.setId(applications.getCandidate().getId());
                    candidateOutput.setName(applications.getCandidate().getName());
                    candidateOutput.setResumeUrl(applications.getCandidate().getResumeUrl());
                    candidateOutput.setEmail(applications.getCandidate().getUser().getEmail());
                    candidateOutput.setCpf(applications.getCandidate().getCpf());
                    candidateOutput.setSkills(applications.getCandidate().getSkills().stream().map(cs -> {
                        CandidateOutput.CandidateSkillsOutput skillOutput = new CandidateOutput.CandidateSkillsOutput();
                        skillOutput.setId(cs.getSkill().getId());
                        skillOutput.setName(cs.getSkill().getName());
                        return skillOutput;
                    }).toList());
                    output.setCandidate(candidateOutput);
                    return output;
                });
    }

    public Dashboard dashboard() {
        Company company = getCompany();
        Dashboard dashboard = new Dashboard();
        Dashboard.CardInfo cardInfo = new Dashboard.CardInfo();
        int vagasAtivas = jobGateway.getCompanyJobsActiveCount(company.getId());
        int vagasFinalizadas = jobGateway.getCompanyJobsCompletedCount(company.getId());
        int totalCandidaturas = applicationsGateway.countApplicationsByCompanyId(company.getId());
        int candidaturasPendentes = applicationsGateway.countPendingApplicationsByCompanyId(company.getId());
        int candidaturasAprovadas = applicationsGateway.countAprovedApplicationsByCompanyId(company.getId());
        int candidaturasUltimas24h = applicationsGateway.countApplicationsLastDayByCompanyId(company.getId());
        cardInfo.setVagasAtivas(vagasAtivas);
        cardInfo.setCandidaturasTotais(totalCandidaturas);
        cardInfo.setCandidaturasPendentes(candidaturasPendentes);
        cardInfo.setCandidaturasAprovadas(candidaturasAprovadas);
        cardInfo.setCandidaturasTotalUltimoDia(candidaturasUltimas24h);
        dashboard.setCardInfo(cardInfo);
        List<ApplicationsCompanyOutput> candidaturasRecentes = applicationsGateway.listByCompanyId(company.getId()).stream().map(a -> {
            return new ApplicationsCompanyOutput().toOutput(a);
        }).toList();
        dashboard.setCandidaturasRecentes(candidaturasRecentes);
        List<JobOutput> vagasEmDestaque = jobGateway.listJobsWithMoreApplications(company.getId()).stream().map(j -> new JobOutput().fromDomain(j)).toList();
        dashboard.setVagasEmDestaque(vagasEmDestaque);
        return dashboard;
    }
}
