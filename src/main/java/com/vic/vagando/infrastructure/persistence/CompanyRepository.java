package com.vic.vagando.infrastructure.persistence;

import com.vic.vagando.infrastructure.entity.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface CompanyRepository extends JpaRepository<CompanyEntity, UUID> {
    Optional<CompanyEntity> findByUserEmail(String email);

    @Query("SELECT c FROM CompanyEntity c WHERE c.cnpj = :cnpj")
    Optional<CompanyEntity> findByCNPJ(String cnpj);


}
