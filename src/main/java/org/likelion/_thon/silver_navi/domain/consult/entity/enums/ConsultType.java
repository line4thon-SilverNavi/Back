package org.likelion._thon.silver_navi.domain.consult.entity.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ConsultType {
    FACE_TO_FACE("대면"),
    NON_FACE_TO_FACE("비대면");

    private final String value;

    @JsonValue
    public String getValue() {
        return value;
    }
}
