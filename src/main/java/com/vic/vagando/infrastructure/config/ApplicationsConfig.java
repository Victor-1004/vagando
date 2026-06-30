package com.vic.vagando.infrastructure.config;

import com.vic.vagando.app.interactor.ApplicationsInteractor;
import com.vic.vagando.infrastructure.adapter.*;
import com.vic.vagando.infrastructure.persistence.ApplicationsRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationsConfig {
    @Bean
    public ApplicationsAdapter  applicationsAdapter(ApplicationsRepository  applicationsRepository) {
        return new ApplicationsAdapter(applicationsRepository);
    }

    @Bean
    public ApplicationsInteractor applicationsInteractor(ApplicationsAdapter adapter, JobAdapter jobAdapter, CandidateAdapter candidateAdapter, AppAdapter  appAdapter, CompanyAdapter companyAdapter) {
        return new ApplicationsInteractor(adapter, jobAdapter, candidateAdapter, appAdapter, companyAdapter);
    }
}
