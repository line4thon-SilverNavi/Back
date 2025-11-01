package org.likelion._thon.silver_navi.domain.consult.entity.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ConsultTime {
    MORNING("오전"),
    AFTERNOON("오후"),
    EVENING("저녁");

    private final String value;

    @JsonValue
    public String getValue() {
        return value;
    }
}
