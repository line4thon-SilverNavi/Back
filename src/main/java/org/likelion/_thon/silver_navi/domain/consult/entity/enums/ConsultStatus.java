package org.likelion._thon.silver_navi.domain.consult.entity.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;
import org.likelion._thon.silver_navi.domain.consult.exception.ConsultStatusInvalidException;
import org.likelion._thon.silver_navi.domain.program.entity.enums.ApplicationStatus;
import org.likelion._thon.silver_navi.domain.program.exception.ApplicationStatusInvalidException;

@RequiredArgsConstructor
public enum ConsultStatus {
    WAITING("대기중"),
    CONFIRMED("확인됨"),
    COMPLETED("완료"),
    REJECTED("거부");

    private final String value;

    @JsonValue
    public String getValue() {
        return value;
    }

    public static ConsultStatus fromValue(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        for (ConsultStatus consultStatus : values()) {
            if (consultStatus.getValue().equals(value)) {
                return consultStatus;
            }
        }
        throw new ConsultStatusInvalidException();
    }
}
