package com.vic.vagando.infrastructure.controller;

import com.vic.vagando.app.domain.PageModel;
import com.vic.vagando.app.domain.Skills;
import com.vic.vagando.app.interactor.SkillsInteractor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/skills")
@CrossOrigin
@Tag(name = "Skills", description = "Endpoints for managing skills")
public class SkillsController {
    private final SkillsInteractor skillsInteractor;

    public SkillsController(SkillsInteractor skillsInteractor) {
        this.skillsInteractor = skillsInteractor;
    }

    @GetMapping
    @Operation(summary = "Get a list of skills", description = "Returns a list of all available skills")
    public PageModel<Skills> find(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size){
        return skillsInteractor.find(page, size);
    }
}
