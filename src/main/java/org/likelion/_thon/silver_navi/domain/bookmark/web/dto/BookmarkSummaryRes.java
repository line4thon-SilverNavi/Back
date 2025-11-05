package org.likelion._thon.silver_navi.domain.bookmark.web.dto;

import org.likelion._thon.silver_navi.domain.nursingfacility.web.dto.NearbyFacilityRes;
import org.likelion._thon.silver_navi.domain.program.web.dto.UserByProgramListRes;
import org.likelion._thon.silver_navi.global.web.dto.IntegratedSearchRes;

import java.util.List;

public record BookmarkSummaryRes(
        int programCount,
        int facilityCount,
        IntegratedSearchRes results
) {
    public static BookmarkSummaryRes of(List<UserByProgramListRes> programs, List<NearbyFacilityRes> facilities) {
        return new BookmarkSummaryRes(
                programs.size(),
                facilities.size(),
                IntegratedSearchRes.of(programs, facilities)
        );
    }
}
