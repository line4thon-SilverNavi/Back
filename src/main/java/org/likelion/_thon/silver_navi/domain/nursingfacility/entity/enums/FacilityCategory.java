package org.likelion._thon.silver_navi.domain.nursingfacility.entity.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum FacilityCategory {

    NURSING_HOME("nursing_home"),           // 요양원
    NURSING_HOSPITAL("nursing_hospital"),   // 요양병원
    CARE_CENTER("care_center");             // 보호센터

    private final String value;

    @JsonValue
    public String getValue() {
        return value;
    }
}
