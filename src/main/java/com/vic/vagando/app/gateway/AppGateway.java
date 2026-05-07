package com.vic.vagando.app.gateway;

import com.vic.vagando.app.domain.user.User;

import java.time.LocalDateTime;
import java.util.Optional;

public interface AppGateway {
    String getLoggedUserEmail();
    LocalDateTime getCurrentDateTime();
    Optional<User> getLoggedUser();
}
