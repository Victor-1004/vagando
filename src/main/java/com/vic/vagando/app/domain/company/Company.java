package com.vic.vagando.app.domain.company;

import com.vic.vagando.app.domain.job.Job;
import com.vic.vagando.app.domain.user.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Company {
    private UUID id;
    private String cnpj;
    private User user;
    private LocalDateTime createdAt;
    private List<Job> jobs;
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

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Company update(Company updatedData){
        if(updatedData.getName() != null){
            this.setName(updatedData.getName());
        }
        if(updatedData.getDescription() != null) {
            this.setDescription(updatedData.getDescription());
        }
        return this;
    }
}
