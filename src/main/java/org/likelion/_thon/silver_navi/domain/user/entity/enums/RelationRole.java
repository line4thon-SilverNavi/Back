package org.likelion._thon.silver_navi.domain.user.entity.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum RelationRole {
    PARENT("parent"),   // 부모
    SPOUSE("spouse");    // 배우자

    private final String value;

    @JsonValue
    public String getValue() {
        return value;
    }
}
