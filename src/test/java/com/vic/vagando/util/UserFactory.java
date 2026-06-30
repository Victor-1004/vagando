package com.vic.vagando.util;

import com.vic.vagando.app.domain.user.User;
import com.vic.vagando.app.domain.user.UserRole;

import java.util.Random;
import java.util.UUID;

public class UserFactory {
    private static Random random = new Random();

    private static UserRole getRandomUserRole(){
        UserRole[] roles = UserRole.values();
        return roles[random.nextInt(roles.length)];
    }

    public static User createUser(UserRole userRole) {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setEmail("user" + UUID.randomUUID().toString().substring(0, 5) + "@example.com");
        user.setPassword("password" + UUID.randomUUID().toString().substring(0, 5) + "@example.com");
        if(userRole == null){
            user.setRole(getRandomUserRole());
        } else {
            user.setRole(userRole);
        }
        return user;
    }

}
