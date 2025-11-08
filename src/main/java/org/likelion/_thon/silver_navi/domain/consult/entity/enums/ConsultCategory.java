package org.likelion._thon.silver_navi.domain.consult.entity.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;
import org.likelion._thon.silver_navi.domain.consult.exception.ConsultCategoryInvalidException;
import org.likelion._thon.silver_navi.domain.consult.exception.ConsultStatusInvalidException;

@RequiredArgsConstructor
public enum ConsultCategory {
    GENERAL("일반상담"),
    GRADE("시설상담");

    private final String value;

    @JsonValue
    public String getValue() {
        return value;
    }

    public static ConsultCategory fromValue(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        for (ConsultCategory consultCategory : values()) {
            if (consultCategory.getValue().equals(value)) {
                return consultCategory;
            }
        }
        throw new ConsultCategoryInvalidException();
    }
}
