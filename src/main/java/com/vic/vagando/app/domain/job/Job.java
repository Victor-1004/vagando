package com.vic.vagando.app.domain.job;

import com.vic.vagando.app.domain.Skills;
import com.vic.vagando.app.domain.company.Company;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Job {
    private UUID id;
    private Company company;
    private String title;
    private String description;
    private String requirements;
    private LocalDateTime createdAt;
    private Set<JobSkills> skills = new HashSet<>();
    private Boolean active;

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Set<JobSkills> getSkills() {
        return skills;
    }

    public void setSkills(Set<JobSkills> skills) {
        this.skills = skills;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }


    public Job update(Job job){
        if(job.getTitle() != null && !job.getTitle().isEmpty()){
            this.title = job.getTitle();
        }
        if(job.getDescription() != null && !job.getDescription().isEmpty()){
            this.description = job.getDescription();
        }
        if(job.getRequirements() != null && !job.getRequirements().isEmpty()){
            this.requirements = job.getRequirements();
        }
        if(job.getSkills() != null && !job.getSkills().isEmpty()){
            this.skills = job.getSkills();
        }
        return this;
    }
}
