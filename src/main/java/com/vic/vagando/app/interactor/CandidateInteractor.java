package com.vic.vagando.app.interactor;

import com.vic.vagando.app.domain.PageModel;
import com.vic.vagando.app.domain.Skills;
import com.vic.vagando.app.domain.candidate.Candidate;
import com.vic.vagando.app.domain.candidate.CandidateSkills;
import com.vic.vagando.app.domain.candidate.input.UpdateCandidateInput;
import com.vic.vagando.app.domain.job.Job;
import com.vic.vagando.app.domain.job.output.JobOutput;
import com.vic.vagando.app.domain.ouput.ApplicationsCandidateOutput;
import com.vic.vagando.app.domain.ouput.CandidateOutput;
import com.vic.vagando.app.exception.BusinessException;
import com.vic.vagando.app.exception.EntityNotFoundException;
import com.vic.vagando.app.gateway.AppGateway;
import com.vic.vagando.app.gateway.CandidateGateway;
import com.vic.vagando.app.gateway.JobGateway;
import com.vic.vagando.app.gateway.SkillsGateway;

import java.util.List;
import java.util.stream.Collectors;

public class CandidateInteractor{
    private final CandidateGateway candidateGateway;
    private final AppGateway appGateway;
    private final JobGateway jobGateway;
    private final SkillsGateway skillsGateway;
    public CandidateInteractor(CandidateGateway candidateGateway, AppGateway appGateway, JobGateway jobGateway, SkillsGateway skillsGateway) {
        this.candidateGateway = candidateGateway;
        this.appGateway = appGateway;
        this.jobGateway = jobGateway;
        this.skillsGateway = skillsGateway;
    }

    public CandidateOutput update(UpdateCandidateInput input){
        if(input.getNome() == null && input.getDescricao() == null){
            throw new BusinessException("At least one field must be provided for update");
        }
        List<Skills> skills = input.getSkills().stream().map(skillsId -> skillsGateway.findById(skillsId).orElseThrow(() -> new EntityNotFoundException("Skill not found"))).toList();
        Candidate candidate = getCandidate();
        candidate.update(input.toDomain());
        candidate.setSkills(skills.stream().map(s -> {
            CandidateSkills  candidateSkills = new CandidateSkills();
            candidateSkills.setCandidate(candidate);
            candidateSkills.setSkill(s);
            return candidateSkills;
        }).collect(Collectors.toSet()));
        candidateGateway.saveCandidate(candidate);
        return new CandidateOutput().toOutput(candidate);
    }

    public PageModel<Job> find(int page, int size){
        return jobGateway.findJobsNotAppliedByCandidateId(getCandidate().getId(), page, size);
    }

    public Candidate getCandidate(){
        String email = appGateway.getLoggedUserEmail();
        return candidateGateway.findByUserEmail(email).orElseThrow(() -> new EntityNotFoundException("Candidate not found"));
    }

    public PageModel<JobOutput> findJobsAppliedByCandidateId(int page, int size){
        return jobGateway.findJobsAppliedByCandidateId(getCandidate().getId(), page, size).map(job -> new JobOutput().fromDomain(job));
    }
}
