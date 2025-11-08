package org.likelion._thon.silver_navi.domain.consult.exception;

import org.likelion._thon.silver_navi.global.exception.BaseException;

public class ConsultNotFoundException extends BaseException {
    public ConsultNotFoundException() {
        super(ConsultErrorCode.CONSULT_NOT_FOUND);
    }
}
