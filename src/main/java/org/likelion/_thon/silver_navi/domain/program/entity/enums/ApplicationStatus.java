package org.likelion._thon.silver_navi.domain.program.entity.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ApplicationStatus {

    PENDING("대기중"),
    APPROVED("승인"),
    REJECTED("거부");

    private final String value;

    @JsonValue
    public String getValue() {
        return value;
    }
}
