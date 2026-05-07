package com.vic.vagando.infrastructure.entity.candidate;


import com.vic.vagando.infrastructure.entity.ApplicationsEntity;
import com.vic.vagando.infrastructure.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "candidates")
@Data
public class CandidateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, name = "candidate_name")
    private String name;

    private String cpf;

    @Column(length = 1000, name = "description")
    private String description;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    private String resumeUrl;

    private LocalDateTime createdAt;


    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CandidateSkillsEntity> skills = new HashSet<>();


    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ApplicationsEntity> applications = new HashSet<>();
}
