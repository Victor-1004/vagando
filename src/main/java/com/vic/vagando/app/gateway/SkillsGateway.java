package com.vic.vagando.app.gateway;

import com.vic.vagando.app.domain.Skills;

import java.util.Optional;
import java.util.UUID;

public interface SkillsGateway {
    Optional<Skills> findById(UUID id);
}
