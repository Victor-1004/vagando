package com.vic.vagando.infrastructure.adapter.mapper;

import com.vic.vagando.app.domain.Skills;
import com.vic.vagando.infrastructure.entity.SkillsEntity;

public class SkillsMapper {
    public static SkillsEntity toEntity(Skills skills) {
        SkillsEntity skillsEntity = new SkillsEntity();
        skillsEntity.setId(skills.getId());
        skillsEntity.setName(skills.getName());
        return skillsEntity;
    }

    public static Skills toDomain(SkillsEntity skillsEntity) {
        Skills skills = new Skills();
        skills.setId(skillsEntity.getId());
        skills.setName(skillsEntity.getName());
        return skills;
    }
}
