package org.likelion._thon.silver_navi.domain.consult.exception;

import org.likelion._thon.silver_navi.global.exception.BaseException;

public class ConsultStatusInvalidException extends BaseException {
    public ConsultStatusInvalidException() {
        super(ConsultErrorCode.CONSULT_STATUS_INVALID);
    }
}
