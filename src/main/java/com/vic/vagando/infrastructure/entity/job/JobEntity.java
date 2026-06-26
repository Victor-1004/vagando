package com.vic.vagando.infrastructure.entity.job;

import com.vic.vagando.infrastructure.entity.ApplicationsEntity;
import com.vic.vagando.infrastructure.entity.CompanyEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "jobs")
@Data
public class JobEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name="company_id", nullable=false)
    private CompanyEntity company;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    private String requirements;

    @Column(nullable = false, name = "created_at")
    private LocalDateTime createdAt;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<JobSkillsEntity> skills = new HashSet<>();

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ApplicationsEntity> applications = new HashSet<>();

    private Boolean active;
}
