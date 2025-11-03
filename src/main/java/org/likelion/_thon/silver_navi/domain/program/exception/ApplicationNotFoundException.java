package org.likelion._thon.silver_navi.domain.program.exception;

import org.likelion._thon.silver_navi.global.exception.BaseException;

public class ApplicationNotFoundException extends BaseException {
    public ApplicationNotFoundException() {
        super(ApplicationErrorCode.APPLICATION_NOT_FOUND);
    }
}
