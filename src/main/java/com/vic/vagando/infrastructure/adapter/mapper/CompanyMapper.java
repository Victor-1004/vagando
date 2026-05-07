package com.vic.vagando.infrastructure.adapter.mapper;

import com.vic.vagando.app.domain.company.Company;
import com.vic.vagando.infrastructure.entity.CompanyEntity;

public class CompanyMapper {
    public static Company toDomainWithoutJob(CompanyEntity entity) {
        if (entity == null) {
            return null;
        }
        Company company = new Company();
        company.setId(entity.getId());
        company.setCnpj(entity.getCnpj());
        company.setCreatedAt(entity.getCreatedAt());
        company.setUser(UserMapper.toDomain(entity.getUser()));
        company.setName(entity.getCompanyName());
        company.setDescription(entity.getDescription());
        return company;
    }
    public static Company toDomain(CompanyEntity entity) {
        if (entity == null) {
            return null;
        }
        Company company = new Company();
        company.setId(entity.getId());
        company.setCnpj(entity.getCnpj());
        company.setCreatedAt(entity.getCreatedAt());
        company.setUser(UserMapper.toDomain(entity.getUser()));
        company.setName(entity.getCompanyName());
        company.setDescription(entity.getDescription());
        if(entity.getJobs() != null){
            company.setJobs(entity.getJobs().stream().map(JobMapper::toDomainWithoutSkillsAndCompany).toList());
        }
        return company;
    }
    public static CompanyEntity toEntityWithoutJob(Company company) {
        if (company == null) {
            return null;
        }
        CompanyEntity entity = new CompanyEntity();
        entity.setId(company.getId());
        entity.setCnpj(company.getCnpj());
        entity.setCreatedAt(company.getCreatedAt());
        entity.setUser(UserMapper.toEntity(company.getUser()));
        entity.setCompanyName(company.getName());
        entity.setDescription(company.getDescription());
        return entity;
    }
    public static CompanyEntity toEntity(Company company) {
        if (company == null) {
            return null;
        }
        CompanyEntity entity = new CompanyEntity();
        entity.setId(company.getId());
        entity.setCnpj(company.getCnpj());
        entity.setCreatedAt(company.getCreatedAt());
        entity.setUser(UserMapper.toEntity(company.getUser()));
        entity.setCompanyName(company.getName());
        entity.setDescription(company.getDescription());
        if(company.getJobs() != null){
            entity.setJobs(company.getJobs().stream().map(JobMapper::toEntityWithoutSkillsAndCompany).toList());
        }

        return entity;
    }
}
