package com.vic.vagando.infrastructure.config;

import com.vic.vagando.infrastructure.adapter.SkillsAdapter;
import com.vic.vagando.infrastructure.persistence.SkillsRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SkillsConfig {
    @Bean
    public SkillsAdapter skillsAdapter(SkillsRepository skillsRepository) {
        return new SkillsAdapter(skillsRepository);
    }
}
