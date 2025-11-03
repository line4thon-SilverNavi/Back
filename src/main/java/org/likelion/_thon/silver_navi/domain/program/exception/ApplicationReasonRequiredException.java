package org.likelion._thon.silver_navi.domain.program.exception;

import org.likelion._thon.silver_navi.global.exception.BaseException;

public class ApplicationReasonRequiredException extends BaseException {
    public ApplicationReasonRequiredException() {
        super(ApplicationErrorCode.APPLICATION_REASON_REQUIRED);
    }
}
