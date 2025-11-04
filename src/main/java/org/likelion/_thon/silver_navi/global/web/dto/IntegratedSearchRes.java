package org.likelion._thon.silver_navi.global.web.dto;

import org.likelion._thon.silver_navi.domain.nursingfacility.web.dto.NearbyFacilityRes;
import org.likelion._thon.silver_navi.domain.program.web.dto.UserByProgramListRes;

import java.util.List;

public record IntegratedSearchRes(
        List<UserByProgramListRes> programs,
        List<NearbyFacilityRes> facilities
) {
    public static IntegratedSearchRes of(List<UserByProgramListRes> programs, List<NearbyFacilityRes> facilities) {
        return new IntegratedSearchRes(programs, facilities);
    }
}