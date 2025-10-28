package org.likelion._thon.silver_navi.domain.caretarget.exception;

import org.likelion._thon.silver_navi.global.exception.BaseException;
import org.likelion._thon.silver_navi.global.response.code.BaseResponseCode;

public class OcrRequestFailedException extends BaseException {
    public OcrRequestFailedException() {
        super(CareTargetErrorCode.OCR_REQUEST_FAILED);
    }
}
