package org.likelion._thon.silver_navi.domain.nursingfacility.exception.nursingfacility;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.likelion._thon.silver_navi.global.response.code.BaseResponseCode;

@Getter
@AllArgsConstructor
public enum NursingFacilityErrorCode implements BaseResponseCode {

    FACILITY_NOT_FOUND("FACILITY_404_1", 404, "해당 요양 시설을 찾을 수 없습니다.");

    private final String code;
    private final int httpStatus;
    private final String message;
}
