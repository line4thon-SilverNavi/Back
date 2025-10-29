package org.likelion._thon.silver_navi.domain.caretarget.entity.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Gender {
    MALE("male"),     // 남성
    FEMALE("female"); // 여성

    private final String value;

    @JsonValue
    public String getValue() {
        return value;
    }
}
