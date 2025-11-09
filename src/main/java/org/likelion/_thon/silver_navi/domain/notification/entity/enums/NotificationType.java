package org.likelion._thon.silver_navi.domain.notification.entity.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum NotificationType {
    PROGRAM("프로그램 신청"),
    CONSULT("상담"),
    PROGRAM_REMINDER("프로그램 일정"),
    REVIEW_REPLY("리뷰 답변");

    private final String value;

    @JsonValue
    public String getValue() {
        return value;
    }
}
