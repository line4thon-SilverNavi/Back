package org.likelion._thon.silver_navi.domain.program.entity.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@RequiredArgsConstructor
public enum UserByProgramStatus {
    SCHEDULED("예정"),
    COMPLETED("완료");

    private final String value;

    @JsonValue
    public String getValue() {
        return value;
    }

    public static UserByProgramStatus from(LocalDate endDate) {
        // 종료일이 오늘 날짜보다 이전이면 (즉, 기한이 지났으면) 완료
        return endDate.isBefore(LocalDate.now())
                ? COMPLETED
                : SCHEDULED; // 아니면 예정
    }
}
