package com.vic.vagando.app.domain.applications;

import com.vic.vagando.app.domain.candidate.Candidate;
import com.vic.vagando.app.domain.job.Job;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Applications {
    private UUID id;
    private Candidate candidate;
    private Job job;
    private ApplicationStatus status;
    private BigDecimal score;
    private LocalDateTime createdAt;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
