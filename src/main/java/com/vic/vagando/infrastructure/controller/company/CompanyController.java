package com.vic.vagando.infrastructure.controller.company;

import com.vic.vagando.app.domain.PageModel;
import com.vic.vagando.app.domain.company.Company;
import com.vic.vagando.app.domain.job.input.CompanyJobInput;
import com.vic.vagando.app.domain.company.input.UpdateCompanyInput;
import com.vic.vagando.app.domain.job.output.JobOutput;
import com.vic.vagando.app.interactor.CompanyInteractor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/company")
@RestController
@CrossOrigin
public class CompanyController {
    private final CompanyInteractor companyInteractor;

    public CompanyController(CompanyInteractor companyInteractor) {
        this.companyInteractor = companyInteractor;
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
}
