package org.likelion._thon.silver_navi.domain.caretarget.entity.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CareGrade {
    LEVEL_1("1등급"),
    LEVEL_2("2등급"),
    LEVEL_3("3등급"),
    LEVEL_4("4등급"),
    LEVEL_5("5등급"),
    COGNITIVE_SUPPORT("인지지원등급");

    private final String label;

    @JsonValue
    public String getLabel() {
        return label;
    }
}
