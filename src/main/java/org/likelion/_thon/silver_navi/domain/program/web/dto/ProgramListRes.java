package org.likelion._thon.silver_navi.domain.program.web.dto;

import org.springframework.data.domain.Page;

import java.util.List;

public record ProgramListRes(
        List<ProgramSummaryInfoRes> programs,
        PageInfo pageInfo
) {
    public record PageInfo(
            int totalPages,     // 전체 페이지 수
            long totalElements, // 전체 프로그램 개수
            int currentPage,    // 현재 페이지 번호
            int pageSize        // 페이지 당 아이템 수
    ) {
        public static PageInfo from(Page<ProgramSummaryInfoRes> summaryPage) {
            return new PageInfo(
                    summaryPage.getTotalPages(),    // 전체 페이지 수
                    summaryPage.getTotalElements(), // 전체 아이템 개수
                    summaryPage.getNumber() + 1,    // 현재 페이지 번호
                    summaryPage.getSize()           // 페이지 당 아이템 수 (6)
            );
        }
    }
}
