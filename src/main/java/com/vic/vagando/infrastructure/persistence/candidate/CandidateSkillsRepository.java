package com.vic.vagando.infrastructure.persistence.candidate;

import com.vic.vagando.infrastructure.entity.candidate.CandidateSkillsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CandidateSkillsRepository extends JpaRepository<CandidateSkillsEntity, UUID> {
    Optional<CandidateSkillsEntity> findBySkillId(UUID skillId);
}
