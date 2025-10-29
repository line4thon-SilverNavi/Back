package org.likelion._thon.silver_navi.domain.user.exception;

import org.likelion._thon.silver_navi.global.exception.BaseException;
import org.likelion._thon.silver_navi.global.response.code.BaseResponseCode;

public class UserNotFoundException extends BaseException {
    public UserNotFoundException() {
        super(UserErrorCode.USER_NOT_FOUND);
    }
}
