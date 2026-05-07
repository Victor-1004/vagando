package com.vic.vagando.app.domain.applications;

public enum ApplicationStatus {
    PENDING("Pending"),
    APPROVED("Approved"),
    REJECTED("Rejected");

    private String description;

    public String getDescription() {
        return description;
    }

    ApplicationStatus(String description) {
        this.description = description;
    }
}
