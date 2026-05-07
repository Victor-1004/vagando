package com.vic.vagando.app.domain.user;

public enum UserRole {
    ADMIN("ADMIN"),
    CANDIDATE("CANDIDATE"),
    COMPANY("COMPANY");
    private final String value;

    UserRole(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
