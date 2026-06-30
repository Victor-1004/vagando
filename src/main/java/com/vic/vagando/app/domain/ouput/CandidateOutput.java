package com.vic.vagando.app.domain.ouput;

import com.vic.vagando.app.domain.candidate.Candidate;

import java.util.List;
import java.util.UUID;

public class CandidateOutput {
    private UUID id;
    private String cpf;
    private String email;
    private String resumeUrl;
    private String name;
    private String description;
    private List<CandidateSkillsOutput> skills;

    public List<CandidateSkillsOutput> getSkills() {
        return skills;
    }

    public void setSkills(List<CandidateSkillsOutput> skills) {
        this.skills = skills;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getResumeUrl() {
        return resumeUrl;
    }

    public void setResumeUrl(String resumeUrl) {
        this.resumeUrl = resumeUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static class CandidateSkillsOutput{
        private UUID id;
        private String name;

        public UUID getId() {
            return id;
        }

        public void setId(UUID id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public CandidateOutput toOutput(Candidate candidate){
        this.id = candidate.getId();
        this.cpf = candidate.getCpf();
        this.email = candidate.getUser().getEmail();
        this.resumeUrl = candidate.getResumeUrl();
        this.name = candidate.getName();
        this.description = candidate.getDescription();
        this.skills = candidate.getSkills().stream().map(skill -> {
            CandidateSkillsOutput skillOutput = new CandidateSkillsOutput();
            skillOutput.setId(skill.getSkill().getId());
            skillOutput.setName(skill.getSkill().getName());
            return skillOutput;
        }).toList();
        return this;
    }


}
