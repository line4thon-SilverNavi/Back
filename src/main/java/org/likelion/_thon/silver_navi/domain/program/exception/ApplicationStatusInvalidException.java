package org.likelion._thon.silver_navi.domain.program.exception;

import org.likelion._thon.silver_navi.global.exception.BaseException;

public class ApplicationStatusInvalidException extends BaseException {
    public ApplicationStatusInvalidException() {
        super(ApplicationErrorCode.APPLICATION_STATUS_INVALID);
    }
}
