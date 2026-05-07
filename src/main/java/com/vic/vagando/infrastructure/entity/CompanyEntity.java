package com.vic.vagando.infrastructure.entity;

import com.vic.vagando.infrastructure.entity.job.JobEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "companies")
@Data
public class CompanyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String cnpj;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false, name = "company_name")
    private String companyName;

    @Column(length = 1000)
    private String description;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JobEntity> jobs;
}
