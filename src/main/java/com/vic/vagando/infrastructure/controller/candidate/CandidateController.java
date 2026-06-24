package com.vic.vagando.infrastructure.controller.candidate;

import com.vic.vagando.app.domain.PageModel;
import com.vic.vagando.app.domain.candidate.input.UpdateCandidateInput;
import com.vic.vagando.app.domain.job.output.JobOutput;
import com.vic.vagando.app.domain.ouput.ApplicationsCompanyOutput;
import com.vic.vagando.app.domain.ouput.CandidateOutput;
import com.vic.vagando.app.interactor.ApplicationsInteractor;
import com.vic.vagando.app.interactor.CandidateInteractor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/candidate")
public class CandidateController {
    private final CandidateInteractor candidateInteractor;
    private final ApplicationsInteractor  applicationsInteractor;

    public CandidateController(CandidateInteractor candidateInteractor, ApplicationsInteractor applicationsInteractor) {
        this.candidateInteractor = candidateInteractor;
        this.applicationsInteractor = applicationsInteractor;
    }

    @GetMapping("/")
    public CandidateOutput findById(){
        return  new CandidateOutput().toOutput(candidateInteractor.getCandidate());
    }


    @GetMapping("/applieds-jobs")
    public PageModel<JobOutput> findJobsAppliedByCandidateId(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        return candidateInteractor.findJobsAppliedByCandidateId(page, size);
    }

    @PatchMapping("/update")
    public ResponseEntity<CandidateOutput> update(@RequestBody UpdateCandidateInput input){
        try {
            CandidateOutput updatedCandidate = candidateInteractor.update(input);
            return ResponseEntity.ok(updatedCandidate);
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).build();
        }
    }

    @PostMapping("/{jobId}/aplicar")
    public ResponseEntity<?> aplicar(@PathVariable("jobId") UUID jobId){
        try {
            ApplicationsCompanyOutput applications = applicationsInteractor.aplicar(jobId);
            return ResponseEntity.ok(applications);
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(Map.of(
                    "message", e.getMessage()
            ));
        }
    }



}
