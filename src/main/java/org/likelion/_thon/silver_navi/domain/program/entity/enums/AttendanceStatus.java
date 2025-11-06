package org.likelion._thon.silver_navi.domain.program.entity.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AttendanceStatus {

    ATTENDANCE("출석"),
    ABSENT("결석");

    private final String value;

    @JsonValue
    public String getValue() {
        return value;
    }
}
