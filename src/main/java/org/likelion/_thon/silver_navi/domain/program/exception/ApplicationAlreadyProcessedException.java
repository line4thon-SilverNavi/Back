package org.likelion._thon.silver_navi.domain.program.exception;

import org.likelion._thon.silver_navi.global.exception.BaseException;

public class ApplicationAlreadyProcessedException extends BaseException {
    public ApplicationAlreadyProcessedException() {
        super(ApplicationErrorCode.APPLICATION_ALREADY_PROCESSED);
    }
}
