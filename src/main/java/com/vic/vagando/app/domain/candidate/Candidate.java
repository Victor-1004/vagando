package com.vic.vagando.app.domain.candidate;

import com.vic.vagando.app.domain.Skills;
import com.vic.vagando.app.domain.user.User;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Candidate {
    private UUID id;
    private String cpf;
    private User user;
    private String resumeUrl;
    private LocalDateTime createdAt;
    private Set<CandidateSkills> skills = new HashSet<>();
    private String name;
    private String description;

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

    public Set<CandidateSkills> getSkills() {
        return skills;
    }

    public void setSkills(Set<CandidateSkills> skills) {
        this.skills = skills;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getResumeUrl() {
        return resumeUrl;
    }

    public void setResumeUrl(String resumeUrl) {
        this.resumeUrl = resumeUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void update(Candidate updatedData){
        if(updatedData.getName() != null){
            this.setName(updatedData.getName());
        }
        if(updatedData.getDescription() != null) {
            this.setDescription(updatedData.getDescription());
        }
        if(updatedData.getResumeUrl() != null){
            this.setResumeUrl(updatedData.getResumeUrl());
        }
        if(updatedData.getSkills() != null && !updatedData.getSkills().isEmpty()){
            this.setSkills(updatedData.getSkills());
        }

    }
}
