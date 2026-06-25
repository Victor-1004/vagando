package com.vic.vagando.infrastructure.adapter;

import com.vic.vagando.app.domain.PageModel;
import com.vic.vagando.app.domain.Skills;
import com.vic.vagando.app.gateway.SkillsGateway;
import com.vic.vagando.infrastructure.adapter.mapper.PageRepositoryMapper;
import com.vic.vagando.infrastructure.adapter.mapper.SkillsMapper;
import com.vic.vagando.infrastructure.persistence.SkillsRepository;
import org.springframework.data.domain.PageRequest;

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

    @Override
    public PageModel<Skills> find(int page, int size) {
        return PageRepositoryMapper.toDomain(skillsRepository.find(PageRequest.of(page, size)).map(SkillsMapper::toDomain));
    }
}
