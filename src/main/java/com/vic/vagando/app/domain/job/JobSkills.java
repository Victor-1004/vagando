package com.vic.vagando.app.domain.job;

import com.vic.vagando.app.domain.Skills;

import java.util.UUID;

public class JobSkills {
    private UUID id;
    private Job job;
    private Skills skill;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Skills getSkill() {
        return skill;
    }

    public void setSkill(Skills skill) {
        this.skill = skill;
    }
}
