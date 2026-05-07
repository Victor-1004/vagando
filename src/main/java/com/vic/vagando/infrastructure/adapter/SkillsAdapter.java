package com.vic.vagando.infrastructure.adapter;

import com.vic.vagando.app.domain.Skills;
import com.vic.vagando.app.gateway.SkillsGateway;
import com.vic.vagando.infrastructure.adapter.mapper.SkillsMapper;
import com.vic.vagando.infrastructure.persistence.SkillsRepository;

import java.util.Optional;
import java.util.UUID;

public class SkillsAdapter implements SkillsGateway {
    private final SkillsRepository skillsRepository;

    public SkillsAdapter(SkillsRepository skillsRepository) {
        this.skillsRepository = skillsRepository;
    }

    @Override
    public Optional<Skills> findById(UUID id) {
        return skillsRepository.findById(id).map(SkillsMapper::toDomain);
    }
}
