package com.vic.vagando.infrastructure.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
@CrossOrigin
@Tag(name = "Health", description = "Endpoint for checking the health of the application")
public class Health {

    @GetMapping
    @Operation(summary = "Check the health of the application", description = "Returns OK if the application is running")
    public String health() {
        return "OK";
    }
}
