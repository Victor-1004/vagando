package com.vic.vagando.infrastructure.persistence;

import com.vic.vagando.infrastructure.entity.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CompanyRepository extends JpaRepository<CompanyEntity, UUID> {
    Optional<CompanyEntity> findByUserEmail(String email);
}
