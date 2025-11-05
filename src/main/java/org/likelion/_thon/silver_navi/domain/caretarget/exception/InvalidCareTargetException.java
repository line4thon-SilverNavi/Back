package org.likelion._thon.silver_navi.domain.caretarget.exception;

import org.likelion._thon.silver_navi.global.exception.BaseException;

public class InvalidCareTargetException extends BaseException {
    public InvalidCareTargetException() {
        super(CareTargetErrorCode.INVALID_CARE_TARGET);
    }
}
