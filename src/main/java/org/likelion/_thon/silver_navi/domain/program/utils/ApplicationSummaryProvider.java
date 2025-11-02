package org.likelion._thon.silver_navi.domain.program.utils;

import org.likelion._thon.silver_navi.domain.program.entity.enums.ApplicationStatus;
import org.likelion._thon.silver_navi.domain.program.repository.ProgramApplyRepository;
import org.likelion._thon.silver_navi.domain.program.web.dto.ApplicationManagementRes.ApplicationSummaryInfoRes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

public class ApplicationSummaryProvider {

    public static ApplicationSummaryInfoRes getMonthlySummary(
            List<Long> programIds, ProgramApplyRepository programApplyRepository) {
        LocalDate now = LocalDate.now();
        LocalDateTime startOfMonth = now.withDayOfMonth(1).atStartOfDay();
        LocalDateTime endOfMonth = now.with(TemporalAdjusters.lastDayOfMonth()).atTime(LocalTime.MAX);

        // 대기중 개수
        long pendingCount = programApplyRepository.countByProgramIdInAndStatusAndCreatedAtBetween(
                programIds, ApplicationStatus.PENDING, startOfMonth, endOfMonth);
        // 승인 개수
        long approvedCount = programApplyRepository.countByProgramIdInAndStatusAndCreatedAtBetween(
                programIds, ApplicationStatus.APPROVED, startOfMonth, endOfMonth);
        // 거절 개수
        long rejectedCount = programApplyRepository.countByProgramIdInAndStatusAndCreatedAtBetween(
                programIds, ApplicationStatus.REJECTED, startOfMonth, endOfMonth);
        // 총 개수
        long totalCount = pendingCount + approvedCount + rejectedCount;

        return new ApplicationSummaryInfoRes(totalCount, pendingCount, approvedCount, rejectedCount);
    }
}
