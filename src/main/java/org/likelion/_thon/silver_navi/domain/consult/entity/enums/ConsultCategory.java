package org.likelion._thon.silver_navi.domain.consult.entity.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ConsultCategory {
    GENERAL("일반상담"),
    GRADE("상담");

    private final String value;

    @JsonValue
    public String getValue() {
        return value;
    }
}
