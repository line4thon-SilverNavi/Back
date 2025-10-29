package org.likelion._thon.silver_navi.domain.manager.exception;

import org.likelion._thon.silver_navi.global.exception.BaseException;

public class ManagerNotFoundException extends BaseException {
    public ManagerNotFoundException() {
        super(ManagerErrorCode.MANAGER_NOT_FOUND);
    }
}
