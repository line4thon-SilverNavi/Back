package org.likelion._thon.silver_navi.domain.consult.exception;

import org.likelion._thon.silver_navi.global.exception.BaseException;

public class ConsultReplyAlreadyExistsException extends BaseException {
    public ConsultReplyAlreadyExistsException() {
        super(ConsultErrorCode.CONSULT_REPLY_ALREADY_EXISTS);
    }
}
