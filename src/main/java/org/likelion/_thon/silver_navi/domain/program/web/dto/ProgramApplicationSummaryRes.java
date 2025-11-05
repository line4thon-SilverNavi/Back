package org.likelion._thon.silver_navi.domain.program.web.dto;

import org.likelion._thon.silver_navi.domain.program.entity.ProgramApply;
import org.likelion._thon.silver_navi.domain.program.entity.enums.AttendanceStatus;

import java.util.List;

public record ProgramApplicationSummaryRes(
        int totalApplicants, // 총 신청자 수(승인된 사람 기준)
        int attendanceCount, // 출석 인원
        double attendanceRate // 출석률 (0~1) 사이
) {
    public static ProgramApplicationSummaryRes from(List<ProgramApply> applies) {
        // 총 신청자 수
        int totalApplicants = applies.size();

        // 출석 인원 계산
        int attendanceCount = (int) applies.stream()
                .filter(apply -> AttendanceStatus.ATTENDANCE.equals(apply.getAttendanceStatus()))
                .count();

        // 출석률 계산
        double attendanceRate = (totalApplicants == 0) ? 0.0 : (double)(attendanceCount / totalApplicants);

        return new ProgramApplicationSummaryRes(
                totalApplicants,
                attendanceCount,
                attendanceRate
        );
    }
}
