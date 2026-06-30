package com.vic.vagando.infrastructure.controller;

import com.vic.vagando.app.domain.PageModel;
import com.vic.vagando.app.domain.job.Job;
import com.vic.vagando.app.domain.job.output.JobOutput;
import com.vic.vagando.app.interactor.JobInteractor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/jobs")
@Tag(name = "Jobs", description = "Endpoints for managing jobs")
public class JobController {
    private final JobInteractor jobInteractor;
    public JobController(JobInteractor jobInteractor) {
        this.jobInteractor = jobInteractor;
    }


}
