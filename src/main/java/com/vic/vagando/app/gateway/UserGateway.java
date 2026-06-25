package com.vic.vagando.app.gateway;

import com.vic.vagando.app.domain.user.User;

import java.util.Optional;
import java.util.UUID;

public interface UserGateway {
    User saveUser(User user);
    Optional<User> findByEmail(String email);
    void deleteUserByEmail(String email);
    void deleteById(UUID id);
}
