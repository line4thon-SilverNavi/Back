package org.likelion._thon.silver_navi.domain.consult.exception;

import org.likelion._thon.silver_navi.global.exception.BaseException;

public class ConsultModifyNotAllowedException extends BaseException {
    public ConsultModifyNotAllowedException() {
        super(ConsultErrorCode.CONSULT_MODIFY_NOT_ALLOWED);
    }
}
