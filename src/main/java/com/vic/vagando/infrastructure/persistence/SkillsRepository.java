package com.vic.vagando.infrastructure.persistence;

import com.vic.vagando.infrastructure.entity.SkillsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface SkillsRepository extends JpaRepository<SkillsEntity, UUID> {

    @Query(value = "SELECT s FROM SkillsEntity s", countQuery = "SELECT count(s) FROM SkillsEntity s")
    Page<SkillsEntity> find(Pageable pageable);
}
