package org.likelion._thon.silver_navi.domain.manager.exception;

import org.likelion._thon.silver_navi.global.exception.BaseException;

public class ManagerPasswordInvalidException extends BaseException {
    public ManagerPasswordInvalidException() { super(ManagerErrorCode.MANAGER_PASSWORD_INVALID); }
}
