package com.vic.vagando.app.interactor;

import com.vic.vagando.app.domain.PageModel;
import com.vic.vagando.app.domain.candidate.Candidate;
import com.vic.vagando.app.domain.candidate.input.UpdateCandidateInput;
import com.vic.vagando.app.domain.job.Job;
import com.vic.vagando.app.domain.job.output.JobOutput;
import com.vic.vagando.app.domain.ouput.CandidateOutput;
import com.vic.vagando.app.exception.BusinessException;
import com.vic.vagando.app.exception.EntityNotFoundException;
import com.vic.vagando.app.gateway.AppGateway;
import com.vic.vagando.app.gateway.CandidateGateway;
import com.vic.vagando.app.gateway.JobGateway;

import java.util.List;

public class CandidateInteractor{
    private final CandidateGateway candidateGateway;
    private final AppGateway appGateway;
    private final JobGateway jobGateway;

    public CandidateInteractor(CandidateGateway candidateGateway, AppGateway appGateway, JobGateway jobGateway) {
        this.candidateGateway = candidateGateway;
        this.appGateway = appGateway;
        this.jobGateway = jobGateway;
    }

    public CandidateOutput update(UpdateCandidateInput input){
        if(input.getNome() == null && input.getDescricao() == null){
            throw new BusinessException("At least one field must be provided for update");
        }
        Candidate candidate = getCandidate();
        candidate.update(input.toDomain());
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
