package com.vic.vagando.infrastructure.controller.candidate;

import com.vic.vagando.app.domain.candidate.Candidate;
import com.vic.vagando.app.domain.candidate.input.UpdateCandidateInput;
import com.vic.vagando.app.interactor.CandidateInteractor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/candidate")
public class CandidateController {
    private final CandidateInteractor candidateInteractor;

    public CandidateController(CandidateInteractor candidateInteractor) {
        this.candidateInteractor = candidateInteractor;
    }

    @PatchMapping("/update")
    public ResponseEntity<Candidate> update(@RequestBody UpdateCandidateInput input){
        try {
            Candidate updatedCandidate = candidateInteractor.update(input);
            return ResponseEntity.ok(updatedCandidate);
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).build();
        }
    }
}
