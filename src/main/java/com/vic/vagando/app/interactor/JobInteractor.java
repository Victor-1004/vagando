package com.vic.vagando.app.interactor;

import com.vic.vagando.app.domain.PageModel;
import com.vic.vagando.app.domain.Skills;
import com.vic.vagando.app.domain.applications.Applications;
import com.vic.vagando.app.domain.candidate.Candidate;
import com.vic.vagando.app.domain.candidate.CandidateSkills;
import com.vic.vagando.app.domain.job.Job;
import com.vic.vagando.app.domain.job.JobSkills;
import com.vic.vagando.app.domain.job.input.CompanyJobInput;
import com.vic.vagando.app.domain.job.output.JobOutput;
import com.vic.vagando.app.exception.ProfileException;
import com.vic.vagando.app.gateway.*;
import com.vic.vagando.app.util.CalculateScore;

import java.util.List;
import java.util.stream.Collectors;

public class JobInteractor {
    private final JobGateway jobGateway;
    private final ApplicationsGateway  applicationsGateway;
    private final SkillsGateway skillsGateway;
    private final CandidateGateway  candidateGateway;
    private final AppGateway appGateway;

    public JobInteractor(JobGateway jobGateway, ApplicationsGateway applicationsGateway, SkillsGateway skillsGateway, CandidateGateway candidateGateway, AppGateway appGateway) {
        this.jobGateway = jobGateway;
        this.applicationsGateway = applicationsGateway;
        this.skillsGateway = skillsGateway;
        this.candidateGateway = candidateGateway;
        this.appGateway = appGateway;
    }

    public PageModel<JobOutput> findCandidateJobs(int page, int size, String title, String token){
        Candidate candidate;
        if(token != null && !token.isEmpty()){
            candidate = getCandidate();
        } else {
            candidate = null;
        }
        if(title != null && title.isEmpty()){
            title = null;
        }
        return jobGateway.findJobs(page, size, title).map(j -> {
            Boolean applied = candidate != null && applicationsGateway.existsByJobAndCandidateId(j.getId(), candidate.getId());
            return new JobOutput().fromDomain(j, applied);
        });
    }

    public JobOutput update(CompanyJobInput input){
        Job job = jobGateway.findById(input.getJobId()).orElseThrow(() -> new RuntimeException("Job not found"));
        List<Applications> applications = applicationsGateway.listByJobId(job.getId());
        job.update(inputToDomain(input));
        List<Skills> skillsJob = job.getSkills().stream().map(JobSkills::getSkill).toList();
        applications.forEach(application -> {
            application.setScore(CalculateScore.calculateScore(skillsJob, application.getCandidate().getSkills().stream().map(CandidateSkills::getSkill).toList()));
            applicationsGateway.save(application);
        });
        jobGateway.createJob(job);
        return new JobOutput().fromDomain(job);
    }

    public Job inputToDomain(CompanyJobInput input){
        Job job = new Job();
        job.setId(input.getJobId());
        job.setTitle(input.getTitle());
        job.setDescription(input.getDescription());
        job.setRequirements(input.getRequirements());
        List<Skills> skills = input.getSkills().stream().map(skillId -> skillsGateway.findById(skillId).orElseThrow(() -> new RuntimeException("Skill not found"))).toList();
        job.setSkills(skills.stream().map(skill -> {
            JobSkills jobSkills = new JobSkills();
            jobSkills.setJob(job);
            jobSkills.setSkill(skill);
            return jobSkills;
        }).collect(Collectors.toSet()));
        return job;
    }

    public Candidate getCandidate(){
        String email = appGateway.getLoggedUserEmail();
        return candidateGateway.findByUserEmail(email).orElseThrow(() -> new ProfileException("Candidate not found"));
    }
}
