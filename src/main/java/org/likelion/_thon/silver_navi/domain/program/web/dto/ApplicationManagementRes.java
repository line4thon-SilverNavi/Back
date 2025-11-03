package org.likelion._thon.silver_navi.domain.program.web.dto;

import org.likelion._thon.silver_navi.domain.program.entity.ProgramApply;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public record ApplicationManagementRes(
        ApplicationSummaryInfoRes summary,        // 요약 정보
        List<ApplicationInfoRes> applications,    // 신청 정보 리스트
        PageInfo pageInfo
) {
    public record ApplicationSummaryInfoRes(
            long totalCount,     // 총 신청 수(이번달 기준)
            long pendingCount,   // 대기중
            long approvedCount,  // 승인
            long rejectedCount   // 거부
    ) {}

    public record ApplicationInfoRes(
            Long applicationId,         // 신청 PK
            LocalDate applicationDate,  // 신청일
            String programName,         // 프로그램명
            String applicantName,       // 신청자
            String phone,               // 연락처
            String status               // 상태
    ) {
        public static ApplicationInfoRes from(ProgramApply programApply) {
            return new ApplicationInfoRes(
                    programApply.getId(),
                    programApply.getCreatedAt().toLocalDate(),
                    programApply.getProgram().getName(),
                    programApply.getUser().getName(),
                    programApply.getUser().getPhone(),
                    programApply.getStatus().getValue()
            );
        }
    }

    public record PageInfo(
            int totalPages,     // 전체 페이지 수
            long totalElements, // 전체 신청 수 개수
            int currentPage,    // 현재 페이지 번호
            int pageSize        // 페이지 당 아이템 수
    ) {
        public static PageInfo from(Page<ApplicationInfoRes> applicationInfoPage) {
            return new PageInfo(
                    applicationInfoPage.getTotalPages(),    // 전체 페이지 수
                    applicationInfoPage.getTotalElements(), // 전체 아이템 개수
                    applicationInfoPage.getNumber() + 1,    // 현재 페이지 번호
                    applicationInfoPage.getSize()           // 페이지 당 아이템 수 (5)
            );
        }
    }
}
