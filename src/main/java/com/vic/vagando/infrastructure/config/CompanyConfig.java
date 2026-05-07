package com.vic.vagando.infrastructure.config;

import com.vic.vagando.app.interactor.CompanyInteractor;
import com.vic.vagando.infrastructure.adapter.AppAdapter;
import com.vic.vagando.infrastructure.adapter.CompanyAdapter;
import com.vic.vagando.infrastructure.adapter.JobAdapter;
import com.vic.vagando.infrastructure.adapter.SkillsAdapter;
import com.vic.vagando.infrastructure.persistence.CompanyRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CompanyConfig {
    @Bean
    public CompanyAdapter companyAdapter(CompanyRepository companyRepository) {
        return new CompanyAdapter(companyRepository);
    }
    @Bean
    public CompanyInteractor companyInteractor(CompanyAdapter companyAdapter, AppAdapter appAdapter, JobAdapter jobAdapter, SkillsAdapter skillsAdapter) {
        return new CompanyInteractor(companyAdapter, appAdapter, jobAdapter, skillsAdapter);
    }
}
