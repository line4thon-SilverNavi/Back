package org.likelion._thon.silver_navi.domain.nursingfacility.exception.affiliatecode;

import org.likelion._thon.silver_navi.global.exception.BaseException;

public class AffiliateCodeMisMatchException extends BaseException {
    public AffiliateCodeMisMatchException() {
        super(AffiliateCodeErrorCode.AFFILIATECODE_MISMATCH);
    }
}
