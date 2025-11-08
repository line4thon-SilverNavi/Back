package org.likelion._thon.silver_navi.domain.notification.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.likelion._thon.silver_navi.global.response.code.BaseResponseCode;

@Getter
@AllArgsConstructor
public enum NotificationErrorCode implements BaseResponseCode {
    NOTIFICATION_ACCESS_DENIED("NOTIFICATION_403_1", 403, "해당 알림에 접근할 수 없습니다."),
    NOTIFICATION_NOT_FOUND("NOTIFICATION_404_1", 404, "해당 알림을 찾을 수 없습니다.");

    private final String code;
    private final int httpStatus;
    private final String message;
}
