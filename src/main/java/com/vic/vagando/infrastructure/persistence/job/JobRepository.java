package com.vic.vagando.infrastructure.persistence.job;

import com.vic.vagando.infrastructure.entity.job.JobEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface JobRepository extends JpaRepository<JobEntity, UUID> {
    @Query("SELECT DISTINCT j FROM JobEntity j LEFT JOIN FETCH j.skills sk LEFT JOIN FETCH sk.skill s LEFT JOIN FETCH j.company c WHERE j.company.id = :companyId")
    List<JobEntity> findByCompanyId(UUID companyId);

    // Versão paginada: fetch só do company (to-one) é seguro com Pageable.
    // skills é coleção -> carrega lazy depois (não dá pra FETCH + paginar no banco).
    @Query(value = "SELECT j FROM JobEntity j LEFT JOIN FETCH j.company c WHERE j.company.id = :companyId",
            countQuery = "SELECT COUNT(j) FROM JobEntity j WHERE j.company.id = :companyId")
    Page<JobEntity> findByCompanyId(UUID companyId, Pageable pageable);

    @Query(value = "SELECT j FROM JobEntity j WHERE j.id NOT IN (SELECT a.job.id FROM ApplicationsEntity a WHERE a.candidate.id = :candidateId)",
            countQuery = "SELECT COUNT(j) FROM JobEntity j WHERE j.id NOT IN (SELECT a.job.id FROM ApplicationsEntity a WHERE a.candidate.id = :candidateId)")
    Page<JobEntity> findJobsNotAppliedByCandidateId(UUID candidateId, Pageable pageable);

    @Query(value = "SELECT j FROM JobEntity j LEFT JOIN FETCH j.company c",
            countQuery = "SELECT COUNT(j) FROM JobEntity j")
    Page<JobEntity> find(Pageable pageable);
}
