package org.likelion._thon.silver_navi.domain.program.entity.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ProgramStatus {

    RECRUITING("모집중"),
    CLOSED("마감"),
    COMPLETED("종료");

    private final String value;

    @JsonValue
    public String getValue() { return value; }
}
