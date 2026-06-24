package com.vic.vagando.infrastructure.entity;

import com.vic.vagando.app.domain.applications.ApplicationStatus;
import com.vic.vagando.infrastructure.entity.candidate.CandidateEntity;
import com.vic.vagando.infrastructure.entity.job.JobEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "applications")
@Data
public class ApplicationsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name="job_id", nullable=false)
    private JobEntity job;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name="candidate_id", nullable=false)
    private CandidateEntity candidate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationStatus status;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal score;

    @Column(nullable = false)
    private LocalDateTime createdAt;



}
