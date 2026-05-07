package com.vic.vagando.infrastructure.persistence.candidate;

import com.vic.vagando.infrastructure.entity.candidate.CandidateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CandidateRepository extends JpaRepository<CandidateEntity, UUID> {

    Optional<CandidateEntity> findByUserEmail(String email);
}
