package org.likelion._thon.silver_navi.domain.review.web.dto;

import org.likelion._thon.silver_navi.domain.program.web.dto.ProgramListRes;
import org.likelion._thon.silver_navi.domain.program.web.dto.ProgramSummaryInfoRes;
import org.springframework.data.domain.Page;

import java.util.List;

public record ReviewPageRes(
        ReviewSummaryRes summary,
        List<ReviewInfoRes> reviews,
        PageInfo pageInfo
) {

    public record PageInfo(
            int totalPages,     // 전체 페이지 수
            long totalElements, // 전체 프로그램 개수
            int currentPage,    // 현재 페이지 번호
            int pageSize        // 페이지 당 아이템 수
    ) {
        public static PageInfo from(Page<ReviewInfoRes> reviewPage) {
            return new PageInfo(
                    reviewPage.getTotalPages(),    // 전체 페이지 수
                    reviewPage.getTotalElements(), // 전체 아이템 개수
                    reviewPage.getNumber() + 1,    // 현재 페이지 번호
                    reviewPage.getSize()           // 페이지 당 아이템 수 (5)
            );
        }
    }
}
