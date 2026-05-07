package com.vic.vagando.infrastructure.persistence;

import com.vic.vagando.infrastructure.entity.SkillsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SkillsRepository extends JpaRepository<SkillsEntity, UUID> {
}
