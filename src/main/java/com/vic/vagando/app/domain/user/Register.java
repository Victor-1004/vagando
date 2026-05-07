package com.vic.vagando.app.domain.user;

import java.util.List;
import java.util.UUID;

public class Register {
    private User user;
    private Boolean isCompany;
    private String identificationNumber;

    private String companyName;
    private String companyDescription;

    private String candidateName;
    private String candidateDescription;
    private List<UUID> skills;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getIsCompany() {
        return isCompany;
    }

    public void setCompany(Boolean company) {
        isCompany = company;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyDescription() {
        return companyDescription;
    }

    public void setCompanyDescription(String companyDescription) {
        this.companyDescription = companyDescription;
    }

    public String getCandidateName() {
        return candidateName;
    }

    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }

    public String getCandidateDescription() {
        return candidateDescription;
    }

    public void setCandidateDescription(String candidateDescription) {
        this.candidateDescription = candidateDescription;
    }

    public List<UUID> getSkills() {
        return skills;
    }

    public void setSkills(List<UUID> skills) {
        this.skills = skills;
    }

    public static class User{
        private String email;
        private String password;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password){
            this.password = password;
        }
    }

}
