package org.likelion._thon.silver_navi.domain.program.exception;

import org.likelion._thon.silver_navi.global.exception.BaseException;

public class ApplicationAccessDeniedException extends BaseException {
    public ApplicationAccessDeniedException() {
        super(ApplicationErrorCode.APPLICATION_ACCESS_DENIED);
    }
}
