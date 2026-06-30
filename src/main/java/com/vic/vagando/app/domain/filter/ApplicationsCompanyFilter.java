package com.vic.vagando.app.domain.filter;

import com.vic.vagando.app.domain.applications.ApplicationStatus;

import java.util.UUID;

public class ApplicationsCompanyFilter {
    private ApplicationStatus status;
    private String job;

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String jobId) {
        this.job = jobId;
    }
}
