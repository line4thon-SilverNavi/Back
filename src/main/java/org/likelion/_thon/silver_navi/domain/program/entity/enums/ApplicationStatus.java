package org.likelion._thon.silver_navi.domain.program.entity.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;
import org.likelion._thon.silver_navi.domain.program.exception.ApplicationStatusInvalidException;

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

    public static ApplicationStatus fromValue(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        for (ApplicationStatus applicationStatus : values()) {
            if (applicationStatus.getValue().equals(value)) {
                return applicationStatus;
            }
        }
        throw new ApplicationStatusInvalidException();
    }
}
