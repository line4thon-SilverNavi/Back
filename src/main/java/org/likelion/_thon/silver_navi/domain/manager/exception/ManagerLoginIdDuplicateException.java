package org.likelion._thon.silver_navi.domain.manager.exception;

import org.likelion._thon.silver_navi.global.exception.BaseException;

public class ManagerLoginIdDuplicateException extends BaseException {
    public ManagerLoginIdDuplicateException() {
        super(ManagerErrorCode.MANAGER_LOGINID_DUPLICATE);
    }
}
