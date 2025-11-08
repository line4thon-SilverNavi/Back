package org.likelion._thon.silver_navi.domain.consult.exception;

import org.likelion._thon.silver_navi.global.exception.BaseException;

public class ConsultAccessDeniedException extends BaseException {
    public ConsultAccessDeniedException() {
        super(ConsultErrorCode.CONSULT_ACCESS_DENIED);
    }
}
