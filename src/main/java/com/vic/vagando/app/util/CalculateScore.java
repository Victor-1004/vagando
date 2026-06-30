package com.vic.vagando.app.util;

import com.vic.vagando.app.domain.Skills;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class CalculateScore {
    public static BigDecimal calculateScore(
            List<Skills> skillsJob,
            List<Skills> skillsCandidate) {

        if (skillsJob.isEmpty()) {
            return BigDecimal.valueOf(100);
        }

        Set<UUID> jobSkillIds = skillsJob.stream()
                .map(Skills::getId)
                .collect(Collectors.toSet());

        long matches = skillsCandidate.stream()
                .map(Skills::getId)
                .filter(jobSkillIds::contains)
                .count();

        return BigDecimal.valueOf(matches)
                .multiply(BigDecimal.valueOf(100))
                .divide(
                        BigDecimal.valueOf(skillsJob.size()),
                        2,
                        RoundingMode.HALF_UP
                );
    }
}
