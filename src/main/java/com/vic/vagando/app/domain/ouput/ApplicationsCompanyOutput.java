package com.vic.vagando.app.domain.ouput;

import com.vic.vagando.app.domain.applications.ApplicationStatus;
import com.vic.vagando.app.domain.applications.Applications;
import com.vic.vagando.app.domain.job.output.JobOutput;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class ApplicationsCompanyOutput {
    private UUID id;
    private CandidateOutput candidate;
    private JobOutput job;
    private ApplicationStatus status;
    private BigDecimal score;
    private LocalDateTime createdAt;
    private String cpf;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public CandidateOutput getCandidate() {
        return candidate;
    }

    public void setCandidate(CandidateOutput candidate) {
        this.candidate = candidate;
    }

    public JobOutput getJob() {
        return job;
    }

    public void setJob(JobOutput job) {
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

    public ApplicationsCompanyOutput toOutput(Applications applications){
        ApplicationsCompanyOutput output = new ApplicationsCompanyOutput();
        output.setId(applications.getId());
        output.setCandidate(new CandidateOutput().toOutput(applications.getCandidate()));
        output.setJob(new JobOutput().fromDomain(applications.getJob()));
        output.setStatus(applications.getStatus());
        output.setScore(applications.getScore());
        output.setCreatedAt(applications.getCreatedAt());
        return output;
    }


}
