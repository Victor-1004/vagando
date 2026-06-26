package com.vic.vagando.infrastructure.persistence.job;

import com.vic.vagando.infrastructure.entity.job.JobEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JobRepository extends JpaRepository<JobEntity, UUID> {
    @Query("SELECT DISTINCT j FROM JobEntity j LEFT JOIN FETCH j.skills sk LEFT JOIN FETCH sk.skill s LEFT JOIN FETCH j.company c WHERE j.company.id = :companyId")
    List<JobEntity> findByCompanyId(UUID companyId);

    @Query("SELECT DISTINCT j FROM JobEntity j LEFT JOIN FETCH j.skills sk LEFT JOIN FETCH sk.skill s LEFT JOIN FETCH j.company c WHERE j.id = :id ORDER BY j.createdAt DESC")
    Optional<JobEntity> findByIdFetchingSkills(UUID id);

    // Versão paginada: fetch só do company (to-one) é seguro com Pageable.
    // skills é coleção -> carrega lazy depois (não dá pra FETCH + paginar no banco).
    @Query(value = "SELECT j FROM JobEntity j LEFT JOIN FETCH j.company c" +
            " WHERE j.company.id = :companyId" +
            " AND (:title IS NULL OR CAST(j.title AS text) ILIKE CONCAT('%', CAST(:title as text), '%'))" +
            " ORDER BY j.createdAt DESC",
            countQuery = "SELECT COUNT(j) FROM JobEntity j WHERE j.company.id = :companyId")
    Page<JobEntity> findByCompanyId(UUID companyId, @Param("title") String title, Pageable pageable);

    @Query(value = "SELECT j FROM JobEntity j WHERE j.id NOT IN (SELECT a.job.id FROM ApplicationsEntity a WHERE a.candidate.id = :candidateId ORDER BY j.createdAt DESC)",
            countQuery = "SELECT COUNT(j) FROM JobEntity j WHERE j.id NOT IN (SELECT a.job.id FROM ApplicationsEntity a WHERE a.candidate.id = :candidateId)")
    Page<JobEntity> findJobsNotAppliedByCandidateId(UUID candidateId, Pageable pageable);

    @Query(value = "SELECT j FROM JobEntity j LEFT JOIN FETCH j.company c where j.active ORDER BY j.createdAt DESC",
            countQuery = "SELECT COUNT(j) FROM JobEntity j")
    Page<JobEntity> find(Pageable pageable);

    @Query(value = "SELECT j FROM JobEntity j LEFT JOIN FETCH j.company c " +
            "where j.active " +
            "AND (:title IS NULL OR CAST(j.title AS text) ILIKE CONCAT('%', CAST(:title as text), '%')) " +
            "ORDER BY j.createdAt DESC",
            countQuery = "SELECT COUNT(j) FROM JobEntity j")
    Page<JobEntity> find(@Param("title")String title,  Pageable pageable);

    @Query(value = "SELECT j FROM JobEntity j WHERE j.id IN (SELECT a.job.id FROM ApplicationsEntity a WHERE a.candidate.id = :candidateId) ORDER BY j.createdAt DESC",
            countQuery = "SELECT COUNT(j) FROM JobEntity j WHERE j.id IN (SELECT a.job.id FROM ApplicationsEntity a WHERE a.candidate.id = :candidateId)")
    Page<JobEntity> findJobsAppliedByCandidateId(UUID candidateId, Pageable pageable);

    @Query("SELECT COUNT(j) FROM JobEntity j WHERE j.company.id = :companyId AND j.active = true")
    int getCompanyJobsActiveCount(UUID companyId);

    @Query("SELECT COUNT(j) FROM JobEntity j WHERE j.company.id = :companyId AND j.active = false")
    int getCompanyJobsCompletedCount(UUID companyId);

    @Query("""
SELECT j
FROM JobEntity j
JOIN ApplicationsEntity a ON a.job.id = j.id
WHERE j.company.id = :companyId
GROUP BY j
ORDER BY COUNT(a) DESC
""")
    List<JobEntity> findTopJobsByApplications(UUID companyId, Pageable pageable);
}
