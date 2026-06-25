package com.vic.vagando.app.domain.applications;

public enum ApplicationStatus {
    PENDING("Pendente"),
    APPROVED("Aprovado"),
    REJECTED("Rejeitado");

    private String description;

    public String getDescription() {
        return description;
    }

    ApplicationStatus(String description) {
        this.description = description;
    }
}
