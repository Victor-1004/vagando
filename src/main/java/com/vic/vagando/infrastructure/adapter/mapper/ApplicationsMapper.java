package com.vic.vagando.infrastructure.adapter.mapper;

import com.vic.vagando.app.domain.applications.Applications;
import com.vic.vagando.infrastructure.entity.ApplicationsEntity;

public class ApplicationsMapper {
    public static Applications toDomain(ApplicationsEntity entity){
        if(entity == null){
            return null;
        }
        Applications applications = new Applications();
        applications.setId(entity.getId());
        applications.setJob(JobMapper.toDomain(entity.getJob()));
        applications.setCandidate(CandidateMapper.toDomain(entity.getCandidate()));
        applications.setStatus(entity.getStatus());
        applications.setScore(entity.getScore());
        applications.setCreatedAt(entity.getCreatedAt());
        return applications;
    }
    public static ApplicationsEntity toEntity(Applications applications){
        if(applications == null){
            return null;
        }
        ApplicationsEntity entity = new ApplicationsEntity();
        entity.setId(applications.getId());
        entity.setJob(JobMapper.toEntity(applications.getJob()));
        entity.setCandidate(CandidateMapper.toEntity(applications.getCandidate()));
        entity.setStatus(applications.getStatus());
        entity.setScore(applications.getScore());
        entity.setCreatedAt(applications.getCreatedAt());
        return entity;
    }
}
