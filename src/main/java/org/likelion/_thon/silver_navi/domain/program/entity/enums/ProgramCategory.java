package org.likelion._thon.silver_navi.domain.program.entity.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;
import org.likelion._thon.silver_navi.domain.nursingfacility.exception.nursingfacility.FacilityCategoryInvalidException;

@RequiredArgsConstructor
public enum ProgramCategory {

    HEALTH("건강"),
    CULTURE("문화"),
    TREATMENT("치료");

    private final String value;

    @JsonValue
    public String getValue() {
        return value;
    }

    public static ProgramCategory fromValue(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        for (ProgramCategory programCategory : values()) {
            if (programCategory.getValue().equals(value)) {
                return programCategory;
            }
        }
        throw new FacilityCategoryInvalidException();
    }
}
