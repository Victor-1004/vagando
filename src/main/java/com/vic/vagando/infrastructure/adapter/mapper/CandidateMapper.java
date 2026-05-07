package com.vic.vagando.infrastructure.adapter.mapper;

import com.vic.vagando.app.domain.candidate.Candidate;
import com.vic.vagando.app.domain.candidate.CandidateSkills;
import com.vic.vagando.infrastructure.entity.candidate.CandidateEntity;
import com.vic.vagando.infrastructure.entity.candidate.CandidateSkillsEntity;

import java.util.stream.Collectors;

public class CandidateMapper {
    public static CandidateEntity toEntity(Candidate candidate) {
       CandidateEntity entity = toEntityWithoutSkills(candidate);
        if(candidate != null && candidate.getSkills() != null) {
            entity.setSkills(candidate.getSkills().stream().map(CandidateMapper::toEntitySkillLink).collect(Collectors.toSet()));
        }
        return entity;
    }

    public static CandidateEntity toEntityWithoutSkills(Candidate candidate) {
        if (candidate == null) {
            return null;
        }
        CandidateEntity entity = new CandidateEntity();
        entity.setId(candidate.getId());
        entity.setCpf(candidate.getCpf());
        entity.setUser(UserMapper.toEntity(candidate.getUser()));
        entity.setResumeUrl(candidate.getResumeUrl());
        entity.setCreatedAt(candidate.getCreatedAt());
        entity.setName(candidate.getName());
        entity.setDescription(candidate.getDescription());
        return entity;
    }


    public static Candidate toDomain(CandidateEntity entity) {
        Candidate candidate = toDomainWithoutSkills(entity);
        if(entity != null && entity.getSkills() != null) {
            candidate.setSkills(entity.getSkills().stream().map(CandidateMapper::toDomainSkillLink).collect(Collectors.toSet()));
        }
        return candidate;
    }

    public static Candidate toDomainWithoutSkills(CandidateEntity entity) {
        if (entity == null) {
            return null;
        }
        Candidate candidate = new Candidate();
        candidate.setId(entity.getId());
        candidate.setCpf(entity.getCpf());
        candidate.setUser(UserMapper.toDomain(entity.getUser()));
        candidate.setResumeUrl(entity.getResumeUrl());
        candidate.setCreatedAt(entity.getCreatedAt());
        candidate.setName(entity.getName());
        candidate.setDescription(entity.getDescription());
        return candidate;
    }


    public static CandidateSkills toDomain(CandidateSkillsEntity entity) {
        return toDomainSkillLink(entity);
    }




    public static CandidateSkillsEntity toEntity(CandidateSkills candidateSkills) {
        return toEntitySkillLink(candidateSkills);
    }

    private static CandidateSkills toDomainSkillLink(CandidateSkillsEntity entity) {
        if (entity == null) {
            return null;
        }
        CandidateSkills candidateSkills = new CandidateSkills();
        candidateSkills.setId(entity.getId());
        candidateSkills.setCandidate(toDomainWithoutSkills(entity.getCandidate()));
        candidateSkills.setSkill(SkillsMapper.toDomain(entity.getSkill()));
        return candidateSkills;
    }

    private static CandidateSkillsEntity toEntitySkillLink(CandidateSkills candidateSkills) {
        if (candidateSkills == null) {
            return null;
        }
        CandidateSkillsEntity entity = new CandidateSkillsEntity();
        entity.setId(candidateSkills.getId());
        entity.setCandidate(toEntityWithoutSkills(candidateSkills.getCandidate()));
        entity.setSkill(SkillsMapper.toEntity(candidateSkills.getSkill()));
        return entity;
    }
}
