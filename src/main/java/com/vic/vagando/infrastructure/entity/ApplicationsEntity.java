package com.vic.vagando.infrastructure.entity;

import com.vic.vagando.app.domain.applications.ApplicationStatus;
import com.vic.vagando.infrastructure.entity.candidate.CandidateEntity;
import com.vic.vagando.infrastructure.entity.job.JobEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "applications")
@Data
public class ApplicationsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name="job_id", nullable=false)
    private JobEntity job;

    @ManyToOne
    @JoinColumn(name="candidate_id", nullable=false)
    private CandidateEntity candidate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationStatus status;

    private Double score;

    @Column(nullable = false)
    private LocalDateTime createdAt;



}
