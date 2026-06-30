package com.vic.vagando.util;

import com.vic.vagando.app.domain.Skills;

import java.util.Random;
import java.util.UUID;

public class SkillsFactory {
    private final Random random = new Random();

    public static Skills createSkill() {
        Skills skill = new Skills();
        skill.setId(UUID.randomUUID());
        skill.setName("Skill " + skill.getId());
        return skill;
    }
}
