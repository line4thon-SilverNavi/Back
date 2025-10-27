package org.likelion._thon.silver_navi.domain.nursingfacility.exception;

import org.likelion._thon.silver_navi.global.exception.BaseException;

public class AffiliateCodeAlreadyIssuedException extends BaseException {
    public AffiliateCodeAlreadyIssuedException() {
        super(AffiliateCodeErrorCode.AFFILIATECODE_ALREADY_ISSUED);
    }
}
