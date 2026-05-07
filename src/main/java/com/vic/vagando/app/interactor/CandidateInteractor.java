package com.vic.vagando.app.interactor;

import com.vic.vagando.app.domain.candidate.Candidate;
import com.vic.vagando.app.domain.candidate.input.UpdateCandidateInput;
import com.vic.vagando.app.exception.BusinessException;
import com.vic.vagando.app.exception.EntityNotFoundException;
import com.vic.vagando.app.gateway.AppGateway;
import com.vic.vagando.app.gateway.CandidateGateway;

public class CandidateInteractor{
    private final CandidateGateway candidateGateway;
    private final AppGateway appGateway;

    public CandidateInteractor(CandidateGateway candidateGateway, AppGateway appGateway) {
        this.candidateGateway = candidateGateway;
        this.appGateway = appGateway;
    }

    public Candidate update(UpdateCandidateInput input){
        if(input.getNome() == null && input.getDescricao() == null){
            throw new BusinessException("At least one field must be provided for update");
        }
        String email = appGateway.getLoggedUserEmail();
        Candidate candidate = candidateGateway.findByUserEmail(email).orElseThrow(() -> new EntityNotFoundException("Candidate not found"));
        candidate.update(input.toDomain());
        candidateGateway.saveCandidate(candidate);
        return candidate;
    }
}
