package com.vic.vagando.app.interactor;

import com.vic.vagando.app.domain.PageModel;
import com.vic.vagando.app.domain.Skills;
import com.vic.vagando.app.gateway.SkillsGateway;

public class SkillsInteractor {
    private final SkillsGateway skillsGateway;

    public SkillsInteractor(SkillsGateway skillsGateway) {
        this.skillsGateway = skillsGateway;
    }

    public PageModel<Skills> find(int page, int size) {
        return skillsGateway.find(page, size);
    }
}
