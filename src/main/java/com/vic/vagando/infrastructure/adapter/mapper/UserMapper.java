package com.vic.vagando.infrastructure.adapter.mapper;

import com.vic.vagando.app.domain.user.User;
import com.vic.vagando.infrastructure.entity.UserEntity;

public class UserMapper {
    public static User toDomain(UserEntity entity) {
        if (entity == null) {
            return null;
        }
        User user = new User();
        user.setId(entity.getId());
        user.setEmail(entity.getEmail());
        user.setRole(entity.getRole());
        user.setPassword(entity.getPassword());
        return user;
    }

    public static UserEntity toEntity(User user) {
        if (user == null) {
            return null;
        }
        UserEntity entity = new UserEntity();
        entity.setId(user.getId());
        entity.setEmail(user.getEmail());
        entity.setRole(user.getRole());
        entity.setPassword(user.getPassword());
        return entity;
    }
}
