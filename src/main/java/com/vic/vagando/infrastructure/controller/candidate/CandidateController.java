package com.vic.vagando.infrastructure.controller.candidate;

import com.vic.vagando.app.domain.PageModel;
import com.vic.vagando.app.domain.candidate.input.UpdateCandidateInput;
import com.vic.vagando.app.domain.job.output.JobOutput;
import com.vic.vagando.app.domain.ouput.ApplicationsCandidateOutput;
import com.vic.vagando.app.domain.ouput.ApplicationsCompanyOutput;
import com.vic.vagando.app.domain.ouput.CandidateOutput;
import com.vic.vagando.app.interactor.ApplicationsInteractor;
import com.vic.vagando.app.interactor.CandidateInteractor;
import com.vic.vagando.app.interactor.JobInteractor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/candidate")
@Tag(name = "Candidate", description = "Endpoints for managing candidate data and job applications")
public class CandidateController {
    private final CandidateInteractor candidateInteractor;
    private final ApplicationsInteractor  applicationsInteractor;
    private final JobInteractor  jobInteractor;

    public CandidateController(CandidateInteractor candidateInteractor, ApplicationsInteractor applicationsInteractor, JobInteractor jobInteractor) {
        this.candidateInteractor = candidateInteractor;
        this.applicationsInteractor = applicationsInteractor;
        this.jobInteractor = jobInteractor;
    }

    @GetMapping("/")
    @Operation(summary = "Get Candidate by ID", description = "Retrieve candidate information based on the provided ID.")
    public CandidateOutput findById(){
        return  new CandidateOutput().toOutput(candidateInteractor.getCandidate());
    }

    @GetMapping("/applieds-jobs")
    @Operation(summary = "Get Jobs Applied by Candidate", description = "Retrieve a paginated list of jobs that the candidate has applied to.")
    public PageModel<JobOutput> findJobsAppliedByCandidateId(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        return candidateInteractor.findJobsAppliedByCandidateId(page, size);
    }

    @GetMapping("/applications")
    @Operation(summary = "Get Candidate Applications", description = "Retrieve a paginated list of applications made by the candidate.")
    public PageModel<ApplicationsCandidateOutput> findApplicationsByCandidateId(@RequestParam(defaultValue
    = "0") int page, @RequestParam(defaultValue = "10") int size){
            return applicationsInteractor.findApplicationsByCandidateId(page, size);
        }

    @PatchMapping("/update")
    @Operation(summary = "Update Candidate Information", description = "Update candidate information based on the provided input.")
    public CandidateOutput update(@RequestBody UpdateCandidateInput input){
        return candidateInteractor.update(input);
    }

    @PostMapping("/{jobId}/aplicar")
    @Operation(summary = "Apply to a Job", description = "Allows the candidate to apply for a job based on the provided job ID.")
    public ApplicationsCandidateOutput aplicar(@PathVariable("jobId") UUID jobId){
        return applicationsInteractor.aplicar(jobId);
    }


    @GetMapping("/jobs")
    @Operation(summary = "Get a paginated list of jobs", description = "Returns a paginated list of jobs with the specified page number and size")
    public PageModel<JobOutput> getCandidateJobs(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size, @RequestParam(required = false) String title, @RequestHeader(name = "Authorization", defaultValue = "") String token){
        if(token.isEmpty())
            token = null;
        return jobInteractor.findCandidateJobs(page, size, title, token);
    }
}
