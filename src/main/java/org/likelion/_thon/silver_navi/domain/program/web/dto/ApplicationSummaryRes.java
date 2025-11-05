package org.likelion._thon.silver_navi.domain.program.web.dto;

import org.likelion._thon.silver_navi.domain.program.entity.enums.UserByProgramStatus;

import java.util.List;

public record ApplicationSummaryRes(
        int scheduledCount,
        int completedCount,
        List<UserProgramStatusRes> programs
) {
    public static ApplicationSummaryRes of(List<UserProgramStatusRes> programs) {
        long scheduled = programs.stream()
                .filter(p -> p.status() == UserByProgramStatus.SCHEDULED)
                .count();

        long completed = programs.stream()
                .filter(p -> p.status() == UserByProgramStatus.COMPLETED)
                .count();

        return new ApplicationSummaryRes((int) scheduled, (int) completed, programs);
    }
}
