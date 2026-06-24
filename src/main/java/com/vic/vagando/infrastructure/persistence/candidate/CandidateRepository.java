package com.vic.vagando.infrastructure.persistence.candidate;

import com.vic.vagando.infrastructure.entity.candidate.CandidateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface CandidateRepository extends JpaRepository<CandidateEntity, UUID> {

    @Query("SELECT DISTINCT c FROM CandidateEntity c " +
            "LEFT JOIN FETCH c.skills cs LEFT JOIN FETCH cs.skill s " +
            "LEFT JOIN FETCH c.user u WHERE u.email = :email")
    Optional<CandidateEntity> findByUserEmail(String email);
}
