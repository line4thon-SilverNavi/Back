package org.likelion._thon.silver_navi.domain.user.exception;

import org.likelion._thon.silver_navi.global.exception.BaseException;

public class InvalidCredentialsException extends BaseException {
    public InvalidCredentialsException( ) {
        super(UserErrorCode.INVALID_CREDENTIALS);}
}
