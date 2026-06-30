package com.vic.vagando.infrastructure.persistence;

import com.vic.vagando.infrastructure.entity.ApplicationsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ApplicationsRepository extends JpaRepository<ApplicationsEntity, UUID> {

    @Query(value = "SELECT a FROM ApplicationsEntity a WHERE a.job.id = :jobId ORDER BY createdAt", countQuery = "SELECT count(a) FROM ApplicationsEntity a WHERE a.job.id = :jobId")
    Page<ApplicationsEntity> findByJobId(UUID jobId, Pageable pageable);

    @Query(value = "SELECT a FROM ApplicationsEntity a " +
            "WHERE a.job.company.id = :companyId " +
            "AND (:job IS NULL OR CAST(a.job.title as text) ILIKE CONCAT('%', CAST(:job as text), '%')) " +
            "AND (:status IS NULL OR CAST(a.status AS text) = :status) ORDER BY a.createdAt DESC",
            countQuery = "SELECT count(a) FROM ApplicationsEntity a " +
            "WHERE a.job.company.id = :companyId " +
            "AND (:job IS NULL OR CAST(a.job.title as text) ILIKE CONCAT('%', CAST(:job as text), '%'))  " +
            "AND (:status IS NULL OR CAST(a.status AS text) = :status)")
    Page<ApplicationsEntity> findApplicationsToCompanyJobs(UUID companyId, @Param("job") String jobId, @Param("status") String status, Pageable pageable);

    @Query(value = "SELECT a FROM ApplicationsEntity a WHERE a.job.id = :jobId ORDER BY a.createdAt DESC")
    List<ApplicationsEntity> listByJobId(UUID jobId);

    @Query("""
SELECT COUNT(a)
FROM ApplicationsEntity a
WHERE a.job.company.id = :companyId
  AND a.createdAt >= :since
""")
    int countApplicationsLastDayByCompanyId(
            @Param("companyId") UUID companyId,
            @Param("since") LocalDateTime since
    );

    @Query("""
SELECT COUNT(a)
FROM ApplicationsEntity a
WHERE a.job.company.id = :companyId
""")
    int countApplicationsByCompanyId(UUID companyId);


    @Query("""
SELECT COUNT(a)
FROM ApplicationsEntity a
WHERE a.job.company.id = :companyId
    AND a.status = 'PENDING'
""")
    int countPendingApplicationsByCompanyId(UUID companyId);

    @Query("""
SELECT COUNT(a)
FROM ApplicationsEntity a
WHERE a.job.company.id = :companyId
    AND a.status = 'APPROVED'
""")
    int countAprovedApplicationsByCompanyId(UUID companyId);

    @Query(value = "SELECT a FROM ApplicationsEntity a WHERE a.job.company.id = :companyId ORDER BY a.createdAt DESC")
    List<ApplicationsEntity> listByCompanyId(UUID companyId, Pageable pageable);

    @Query(value = "SELECT a FROM ApplicationsEntity a WHERE a.candidate.id = :candidateId ORDER BY a.createdAt DESC", countQuery = "SELECT count(a) FROM ApplicationsEntity a WHERE a.candidate.id = :candidateId")
    Page<ApplicationsEntity> findApplicationsByCandidateId(UUID candidateId, Pageable pageable);

    @Query("SELECT EXISTS(SELECT a FROM ApplicationsEntity a WHERE a.job.id = :jobId AND a.candidate.id = :candidateId)")
    boolean existsByJobAndCandidateId(UUID jobId, UUID candidateId);
}
