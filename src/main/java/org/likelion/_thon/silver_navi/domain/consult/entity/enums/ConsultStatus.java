package org.likelion._thon.silver_navi.domain.consult.entity.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ConsultStatus {
    WAITING("대기중"),
    CONFIRMED("확인됨"),
    COMPLETED("완료");

    private final String value;

    @JsonValue
    public String getValue() {
        return value;
    }
}
