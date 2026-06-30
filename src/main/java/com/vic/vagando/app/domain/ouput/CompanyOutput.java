package com.vic.vagando.app.domain.ouput;


import com.vic.vagando.app.domain.company.Company;

public class CompanyOutput {
    private String name;
    private String description;
    public String getName() {
        return name;
    }
    public void setName(String name) {}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CompanyOutput toOutput(Company company){
        this.name = company.getName();
        this.description = company.getDescription();
        return this;
    }
}


