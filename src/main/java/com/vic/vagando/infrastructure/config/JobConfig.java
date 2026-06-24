package com.vic.vagando.infrastructure.config;

import com.vic.vagando.app.interactor.JobInteractor;
import com.vic.vagando.infrastructure.adapter.JobAdapter;
import com.vic.vagando.infrastructure.persistence.job.JobRepository;
import com.vic.vagando.infrastructure.persistence.job.JobSkillsRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobConfig {
    @Bean
    public JobAdapter jobAdapter(JobRepository jobRepository, JobSkillsRepository jobSkillsRepository) {
        return new JobAdapter(jobRepository, jobSkillsRepository);
    }

    @Bean
    public JobInteractor jobInteractor(JobAdapter jobAdapter) {
        return new JobInteractor(jobAdapter);
    }
}
