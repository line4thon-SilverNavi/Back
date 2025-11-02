package org.likelion._thon.silver_navi.domain.bookmark.entity.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum BookmarkType {
    PROGRAM("프로그램"),
    FACILITY("시설");

    private final String value;

    @JsonValue
    public String getValue() {
        return value;
    }
}
