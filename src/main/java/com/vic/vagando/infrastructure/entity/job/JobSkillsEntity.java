package com.vic.vagando.infrastructure.entity.job;

import com.vic.vagando.infrastructure.entity.SkillsEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.UUID;

@Entity
@Table(name = "job_skills")
@Data
public class JobSkillsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "skill_id", referencedColumnName = "id")
    private SkillsEntity skill;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "job_id", referencedColumnName = "id")
    private JobEntity job;


}
