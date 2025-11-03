package org.likelion._thon.silver_navi.domain.search.web.dto;

import org.likelion._thon.silver_navi.domain.nursingfacility.web.dto.NearbyFacilityRes;
import org.likelion._thon.silver_navi.domain.program.web.dto.UserByProgramListRes;

import java.util.List;

public record SearchResultRes(
        List<UserByProgramListRes> programs,
        List<NearbyFacilityRes> facilities
) {
    public static SearchResultRes of(List<UserByProgramListRes> programs, List<NearbyFacilityRes> facilities) {
        return new SearchResultRes(programs, facilities);
    }
}