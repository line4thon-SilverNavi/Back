package org.likelion._thon.silver_navi.domain.notification.exception;

import org.likelion._thon.silver_navi.global.exception.BaseException;
import org.likelion._thon.silver_navi.global.response.code.BaseResponseCode;

public class NotificationNotFoundException extends BaseException {
    public NotificationNotFoundException() {
        super(NotificationErrorCode.NOTIFICATION_NOT_FOUND);
    }
}
