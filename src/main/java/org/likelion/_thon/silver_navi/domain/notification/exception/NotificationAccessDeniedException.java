package org.likelion._thon.silver_navi.domain.notification.exception;

import org.likelion._thon.silver_navi.global.exception.BaseException;
import org.likelion._thon.silver_navi.global.response.code.BaseResponseCode;

public class NotificationAccessDeniedException extends BaseException {
    public NotificationAccessDeniedException() {
        super(NotificationErrorCode.NOTIFICATION_ACCESS_DENIED);
    }
}
