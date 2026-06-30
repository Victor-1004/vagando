package com.vic.vagando.infrastructure.controller;

import com.vic.vagando.app.domain.applications.ApplicationStatus;
import com.vic.vagando.app.interactor.ApplicationsInteractor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/applications")
@CrossOrigin
@Tag(name = "Applications", description = "Endpoints for managing applications")
public class ApplicationController {
    private final ApplicationsInteractor interactor;

    public ApplicationController(ApplicationsInteractor interactor) {
        this.interactor = interactor;
    }

    @GetMapping("/status")
    @Operation(summary = "Get the status of all applications", description = "Returns a list of application statuses")
    public List<Map<String, String>> getApplicationStatus() {
        return interactor.getStatus();
    }
}
