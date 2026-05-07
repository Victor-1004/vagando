package com.vic.vagando.infrastructure.config;

import com.vic.vagando.infrastructure.adapter.AppAdapter;
import com.vic.vagando.infrastructure.persistence.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public AppAdapter appAdapter(UserRepository userRepository) {
        return new AppAdapter(userRepository);
    }
}
