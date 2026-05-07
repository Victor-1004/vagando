package com.vic.vagando.infrastructure.adapter;

import com.vic.vagando.app.domain.user.User;
import com.vic.vagando.app.gateway.UserGateway;
import com.vic.vagando.infrastructure.adapter.mapper.UserMapper;
import com.vic.vagando.infrastructure.persistence.UserRepository;

import java.util.Optional;

public class UserAdapter implements UserGateway {
    private final UserRepository userRepository;

    public UserAdapter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User saveUser(User user) {
        return UserMapper.toDomain(userRepository.save(UserMapper.toEntity(user)));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email).map(UserMapper::toDomain);
    }
}
