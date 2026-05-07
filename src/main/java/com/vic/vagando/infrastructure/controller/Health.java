package com.vic.vagando.infrastructure.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
@CrossOrigin
public class Health {

    @GetMapping
    public String health() {
        return "OK";
    }
}
