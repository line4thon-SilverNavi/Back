package org.likelion._thon.silver_navi.domain.nursingfacility.exception.affiliatecode;

import org.likelion._thon.silver_navi.global.exception.BaseException;

public class AffiliateCodeNotFoundException extends BaseException {
    public AffiliateCodeNotFoundException() {
        super(AffiliateCodeErrorCode.AFFILIATECODE_NOT_FOUND);
    }
}
