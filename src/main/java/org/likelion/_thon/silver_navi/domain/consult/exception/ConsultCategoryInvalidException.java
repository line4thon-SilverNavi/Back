package org.likelion._thon.silver_navi.domain.consult.exception;

import org.likelion._thon.silver_navi.global.exception.BaseException;

public class ConsultCategoryInvalidException extends BaseException {
    public ConsultCategoryInvalidException() {
        super(ConsultErrorCode.CONSULT_CATEGORY_INVALID);
    }
}
