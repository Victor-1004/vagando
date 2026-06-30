package com.vic.vagando.app.interactor;

import com.vic.vagando.app.domain.PageModel;
import com.vic.vagando.app.domain.Skills;
import com.vic.vagando.app.domain.applications.ApplicationStatus;
import com.vic.vagando.app.domain.applications.Applications;
import com.vic.vagando.app.domain.candidate.Candidate;
import com.vic.vagando.app.domain.candidate.CandidateSkills;
import com.vic.vagando.app.domain.company.Company;
import com.vic.vagando.app.domain.job.Job;
import com.vic.vagando.app.domain.job.JobSkills;
import com.vic.vagando.app.domain.ouput.ApplicationsCandidateOutput;
import com.vic.vagando.app.domain.ouput.ApplicationsCompanyOutput;
import com.vic.vagando.app.exception.EntityNotFoundException;
import com.vic.vagando.app.gateway.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

import static com.vic.vagando.app.util.CalculateScore.calculateScore;

public class ApplicationsInteractor {
    private final ApplicationsGateway applicationsGateway;
    private final JobGateway jobGateway;
    private final CandidateGateway  candidateGateway;
    private final AppGateway appGateway;
    private final CompanyGateway companyGateway;

    public ApplicationsInteractor(ApplicationsGateway applicationsGateway, JobGateway jobGateway, CandidateGateway candidateGateway, AppGateway appGateway, CompanyGateway companyGateway) {
        this.applicationsGateway = applicationsGateway;
        this.jobGateway = jobGateway;
        this.candidateGateway = candidateGateway;
        this.appGateway = appGateway;
        this.companyGateway = companyGateway;
    }

    public ApplicationsCandidateOutput aplicar(UUID jobId){
        Job job = getJob(jobId);
        List<Skills> skillsJob = job.getSkills().stream().map(JobSkills::getSkill).toList();
        Candidate candidate = getCandidate();
        List<Skills> skillsCandidate = candidate.getSkills().stream().map(CandidateSkills::getSkill).toList();
        BigDecimal score = calculateScore(skillsJob, skillsCandidate);
        Applications applications = new Applications();
        applications.setJob(job);
        applications.setCandidate(candidate);
        applications.setScore(score);
        applications.setCreatedAt(appGateway.getCurrentDateTime());
        applications.setStatus(ApplicationStatus.PENDING);
        return new ApplicationsCandidateOutput().toOutput(applicationsGateway.save(applications));
    }

    public Job getJob(UUID jobId){
        return jobGateway.findById(jobId).orElseThrow(() -> new EntityNotFoundException("Job not found"));
    }

    public Candidate getCandidate(){
        String email = appGateway.getLoggedUserEmail();
        return candidateGateway.findByUserEmail(email).orElseThrow(() -> new EntityNotFoundException("Candidate not found"));
    }

    public Company getCompany(){
        String email = appGateway.getLoggedUserEmail();
        return companyGateway.findByUserEmail(email).orElseThrow(() -> new EntityNotFoundException("Company not found"));
    }



    public PageModel<ApplicationsCompanyOutput> getApplications(UUID jobId, int page, int size){
        Company company = getCompany();
        Job job = getJob(jobId);
        if(job.getCompany().getId() != company.getId()){
            throw new EntityNotFoundException("Job not found");
        }
        return applicationsGateway.findByJobId(jobId, page, size).map(application -> new ApplicationsCompanyOutput().toOutput(application));
    }

    public void updateApplicationStatus(UUID applicationId, ApplicationStatus applicationStatus){
        getCompany();
        Applications application = findById(applicationId);
        application.setStatus(applicationStatus);
        applicationsGateway.save(application);
    }

    public Applications findById(UUID applicationId) {
        return applicationsGateway.findById(applicationId).orElseThrow(() -> new EntityNotFoundException("Application not found"));
    }

    public List<Map<String, String>> getStatus(){
        getCompany();
        return Arrays.stream(ApplicationStatus.values())
                .map(status -> Map.of(
                        "id", status.name(),
                        "name", status.getDescription()
                )).toList();
    }

    public PageModel<ApplicationsCandidateOutput> findApplicationsByCandidateId(int page, int size){
        Candidate candidate = getCandidate();
        return applicationsGateway.findApplicationsByCandidateId(candidate.getId(), page, size).map(application -> new ApplicationsCandidateOutput().toOutput(application));
    }
}
