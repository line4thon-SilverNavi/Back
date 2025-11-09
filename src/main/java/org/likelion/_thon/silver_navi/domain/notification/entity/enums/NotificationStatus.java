package org.likelion._thon.silver_navi.domain.notification.entity.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum NotificationStatus {
    APPROVED("승인"),
    REJECTED("거부");

    private final String value;

    @JsonValue
    public String getValue() {
        return value;
    }
}
