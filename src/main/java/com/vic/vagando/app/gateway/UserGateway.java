package com.vic.vagando.app.gateway;

import com.vic.vagando.app.domain.user.User;

import java.util.Optional;

public interface UserGateway {
    User saveUser(User user);
    Optional<User> findByEmail(String email);
}
