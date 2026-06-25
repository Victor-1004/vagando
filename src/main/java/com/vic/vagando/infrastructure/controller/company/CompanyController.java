package com.vic.vagando.infrastructure.controller.company;

import com.vic.vagando.app.domain.PageModel;
import com.vic.vagando.app.domain.applications.ApplicationStatus;
import com.vic.vagando.app.domain.company.Company;
import com.vic.vagando.app.domain.filter.ApplicationsCompanyFilter;
import com.vic.vagando.app.domain.job.input.CompanyJobInput;
import com.vic.vagando.app.domain.company.input.UpdateCompanyInput;
import com.vic.vagando.app.domain.job.output.JobOutput;
import com.vic.vagando.app.domain.ouput.ApplicationsCompanyOutput;
import com.vic.vagando.app.interactor.ApplicationsInteractor;
import com.vic.vagando.app.interactor.CompanyInteractor;
import com.vic.vagando.app.interactor.JobInteractor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/company")
@RestController
@CrossOrigin
@Tag(name = "Company", description = "Endpoints for managing company data, job postings, and applications")
public class CompanyController {
    private final CompanyInteractor companyInteractor;
    private final ApplicationsInteractor applicationsInteractor;
    private final JobInteractor  jobInteractor;
    public CompanyController(CompanyInteractor companyInteractor, ApplicationsInteractor applicationsInteractor, JobInteractor jobInteractor) {
        this.companyInteractor = companyInteractor;
        this.applicationsInteractor = applicationsInteractor;
        this.jobInteractor = jobInteractor;
    }

    @PatchMapping("/update")
    @Operation(summary = "Update Company Information", description = "Update company information based on the provided input.")
    public Company update(@RequestBody UpdateCompanyInput input){
        return companyInteractor.update(input);
    }

    @PostMapping("/job")
    @Operation(summary = "Create Job Posting", description = "Create a new job posting based on the provided input.")
    public JobOutput createJob(@RequestBody CompanyJobInput input){
        return companyInteractor.createJob(input);
    }

    @GetMapping("/job")
    @Operation(summary = "Get Jobs", description = "Retrieve a paginated list of jobs posted by the company.")
    public PageModel<JobOutput> getJobs(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size){
        return companyInteractor.getJobs(page, size);
    }

    @PatchMapping("/job")
    @Operation(summary = "Update Job Posting", description = "Update an existing job posting based on the provided job ID and input.")
    public JobOutput updateJob(@RequestBody CompanyJobInput input){
        return jobInteractor.update(input);
    }

    @GetMapping("/{jobId}/applications")
    @Operation(summary = "Get Applications for a Job", description = "Retrieve a paginated list of applications for a specific job posting based on the provided job ID.")
    public PageModel<ApplicationsCompanyOutput> getApplications(@PathVariable("jobId") UUID jobId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        return applicationsInteractor.getApplications(jobId, page, size);
    }

    @PatchMapping("/{jobId}/applications/{applicationId}")
    @Operation(summary = "Update Application Status", description = "Update the status of a specific application for a job posting based on the provided job ID, application ID, and new status.")
    public void updateApplicationStatus(@PathVariable("jobId") UUID jobId,
                                                             @PathVariable("applicationId") UUID applicationId,
                                                             @RequestParam("status") ApplicationStatus status){
        applicationsInteractor.updateApplicationStatus(applicationId, status);
    }

    @GetMapping("/applications")
    public PageModel<ApplicationsCompanyOutput> getApplicationsToCompanyJobs(ApplicationsCompanyFilter filter,
                                                                             @RequestParam(defaultValue = "0") int page,
                                                                             @RequestParam(defaultValue = "10") int size){
        return companyInteractor.getApplicationsToCompanyJobs(filter, page, size);
    }
}
