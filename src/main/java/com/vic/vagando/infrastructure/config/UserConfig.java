package com.vic.vagando.infrastructure.config;

import com.vic.vagando.app.interactor.UserInteractor;
import com.vic.vagando.infrastructure.adapter.*;
import com.vic.vagando.infrastructure.persistence.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {
    @Bean
    public UserAdapter userAdapter(UserRepository userRepository) {
        return new UserAdapter(userRepository);
    }

    @Bean
    public UserInteractor userInteractor(UserAdapter userAdapter, CandidateAdapter candidateAdapter, CompanyAdapter companyAdapter, SkillsAdapter skillsAdapter, AppAdapter appAdapter) {
        return new UserInteractor(userAdapter, candidateAdapter, companyAdapter, skillsAdapter, appAdapter);
    }
}
