package com.vic.vagando.app.domain.candidate;

import com.vic.vagando.app.domain.Skills;

import java.util.UUID;

public class CandidateSkills {
    private UUID id;
    private Candidate candidate;
    private Skills skill;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public Skills getSkill() {
        return skill;
    }

    public void setSkill(Skills skill) {
        this.skill = skill;
    }
}
