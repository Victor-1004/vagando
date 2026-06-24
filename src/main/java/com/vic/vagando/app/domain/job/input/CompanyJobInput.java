package com.vic.vagando.app.domain.job.input;

import com.vic.vagando.app.domain.job.Job;
import com.vic.vagando.app.exception.BusinessException;

import java.util.List;
import java.util.UUID;

public class CompanyJobInput {
    private String title;
    private String description;
    private String requirements;
    private List<UUID> skills;

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

    public List<UUID> getSkills() {
        return skills;
    }

    public void setSkills(List<UUID> skills) {
        this.skills = skills;
    }

    public Job toDomain(){
        if(this.getTitle() == null){
            throw new BusinessException("Job title is required");
        }
        if(this.getRequirements() == null){
            throw new BusinessException("Job requirements are required");
        }
        Job job = new Job();
        job.setTitle(this.getTitle());
        job.setDescription(this.getDescription());
        job.setRequirements(this.getRequirements());
        return job;
    }
}
