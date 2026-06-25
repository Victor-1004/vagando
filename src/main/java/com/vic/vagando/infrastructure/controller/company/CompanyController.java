package com.vic.vagando.infrastructure.controller.company;

import com.vic.vagando.app.domain.PageModel;
import com.vic.vagando.app.domain.applications.ApplicationStatus;
import com.vic.vagando.app.domain.company.Company;
import com.vic.vagando.app.domain.job.input.CompanyJobInput;
import com.vic.vagando.app.domain.company.input.UpdateCompanyInput;
import com.vic.vagando.app.domain.job.output.JobOutput;
import com.vic.vagando.app.domain.ouput.ApplicationsCompanyOutput;
import com.vic.vagando.app.interactor.ApplicationsInteractor;
import com.vic.vagando.app.interactor.CompanyInteractor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/company")
@RestController
@CrossOrigin
public class CompanyController {
    private final CompanyInteractor companyInteractor;
    private final ApplicationsInteractor applicationsInteractor;

    public CompanyController(CompanyInteractor companyInteractor, ApplicationsInteractor applicationsInteractor) {
        this.companyInteractor = companyInteractor;
        this.applicationsInteractor = applicationsInteractor;
    }

    @PatchMapping("/update")
    public Company update(@RequestBody UpdateCompanyInput input){
        return companyInteractor.update(input);
    }

    @PostMapping("/job")
    public JobOutput createJob(@RequestBody CompanyJobInput input){
        return companyInteractor.createJob(input);
    }

    @GetMapping("/job")
    public PageModel<JobOutput> getJobs(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size){
        return companyInteractor.getJobs(page, size);
    }

    @PatchMapping("/job")
    public JobOutput updateJob(@RequestParam(name="job", required = true) UUID jobId, @RequestBody CompanyJobInput input){
        return null;
    }

    @GetMapping("/{jobId}/applications")
    public PageModel<ApplicationsCompanyOutput> getApplications(@PathVariable("jobId") UUID jobId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        return applicationsInteractor.getApplications(jobId, page, size);
    }

    @PatchMapping("/{jobId}/applications/{applicationId}")
    public void updateApplicationStatus(@PathVariable("jobId") UUID jobId,
                                                             @PathVariable("applicationId") UUID applicationId,
                                                             @RequestParam("status") ApplicationStatus status){
        applicationsInteractor.updateApplicationStatus(applicationId, status);
    }
}
