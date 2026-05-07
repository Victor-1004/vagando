package com.vic.vagando.app.gateway;

import com.vic.vagando.app.domain.candidate.Candidate;
import com.vic.vagando.app.domain.candidate.CandidateSkills;

import java.util.Optional;
import java.util.UUID;

public interface CandidateGateway {
    Candidate saveCandidate(Candidate candidate);
    Optional<CandidateSkills> findBySkillId(UUID skillId);
    Optional<Candidate> findByUserEmail(String email);
    CandidateSkills saveCandidateSkills(CandidateSkills candidateSkills);
}
