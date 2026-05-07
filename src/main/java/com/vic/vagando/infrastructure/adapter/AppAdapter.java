package com.vic.vagando.infrastructure.adapter;

import com.vic.vagando.app.domain.user.User;
import com.vic.vagando.app.gateway.AppGateway;
import com.vic.vagando.infrastructure.adapter.mapper.UserMapper;
import com.vic.vagando.infrastructure.persistence.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Optional;

public class AppAdapter implements AppGateway {
    private final Clock clock = Clock.systemDefaultZone();
    private final UserRepository userRepository;

    public AppAdapter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String getLoggedUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @Override
    public LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now(clock);
    }

    @Override
    public Optional<User> getLoggedUser() {
        String email = getLoggedUserEmail();
        return userRepository.findByEmail(email).map(UserMapper::toDomain);
    }


}
