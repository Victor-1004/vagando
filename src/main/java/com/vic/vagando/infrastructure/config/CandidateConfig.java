package com.vic.vagando.infrastructure.config;

import com.vic.vagando.app.interactor.CandidateInteractor;
import com.vic.vagando.infrastructure.adapter.AppAdapter;
import com.vic.vagando.infrastructure.adapter.CandidateAdapter;
import com.vic.vagando.infrastructure.adapter.JobAdapter;
import com.vic.vagando.infrastructure.adapter.SkillsAdapter;
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
    public CandidateInteractor candidateInteractor(CandidateAdapter candidateAdapter, AppAdapter appAdapter, JobAdapter jobAdapter, SkillsAdapter skillsAdapter) {
        return new CandidateInteractor(candidateAdapter, appAdapter, jobAdapter, skillsAdapter);
    }
}
