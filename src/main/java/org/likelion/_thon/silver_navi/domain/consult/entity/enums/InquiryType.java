package org.likelion._thon.silver_navi.domain.consult.entity.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum InquiryType {
    GENERAL("일반 문의"),
    FACILITY("시설 이용 문의"),
    COST("비용 문의"),
    RESERVATION("방문 예약");

    private final String value;

    @JsonValue
    public String getValue() {
        return value;
    }
}
