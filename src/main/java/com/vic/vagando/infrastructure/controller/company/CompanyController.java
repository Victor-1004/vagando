package com.vic.vagando.infrastructure.controller.company;

import com.vic.vagando.app.domain.company.Company;
import com.vic.vagando.app.domain.job.input.CreateCompanyJobInput;
import com.vic.vagando.app.domain.company.input.UpdateCompanyInput;
import com.vic.vagando.app.domain.job.output.JobOutput;
import com.vic.vagando.app.interactor.CompanyInteractor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public JobOutput createJob(@RequestBody CreateCompanyJobInput input){
        return companyInteractor.createJob(input);
    }

    @GetMapping("/job")
    public List<JobOutput> getJobs(){
        return companyInteractor.getJobs();
    }
}
