package com.vic.vagando.infrastructure.config;

import com.vic.vagando.app.interactor.CandidateInteractor;
import com.vic.vagando.infrastructure.adapter.AppAdapter;
import com.vic.vagando.infrastructure.adapter.CandidateAdapter;
import com.vic.vagando.infrastructure.persistence.candidate.CandidateRepository;
import com.vic.vagando.infrastructure.persistence.candidate.CandidateSkillsRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CandidateConfig {
    @Bean
    public CandidateAdapter candidateAdapter(CandidateRepository candidateRepository, CandidateSkillsRepository candidateSkillsRepository) {
        return new CandidateAdapter(candidateRepository, candidateSkillsRepository);
    }

    @Bean
    public CandidateInteractor candidateInteractor(CandidateAdapter candidateAdapter, AppAdapter appAdapter) {
        return new CandidateInteractor(candidateAdapter, appAdapter);
    }
}
