package org.likelion._thon.silver_navi.domain.user.entity.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum RelationRole {
    SELF("본인"),
    SPOUSE("배우자"),
    CHILD("자녀"),
    SIBLING("형제/자매");

    private final String value;

    @JsonValue
    public String getValue() {
        return value;
    }
}
