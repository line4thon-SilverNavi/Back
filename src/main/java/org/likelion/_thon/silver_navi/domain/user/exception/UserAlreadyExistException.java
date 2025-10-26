package org.likelion._thon.silver_navi.domain.user.exception;

import org.likelion._thon.silver_navi.global.exception.BaseException;

public class UserAlreadyExistException extends BaseException {
    public UserAlreadyExistException(){
        super(UserErrorCode.USER_ID_ALREADY_EXIST);
    }
}
