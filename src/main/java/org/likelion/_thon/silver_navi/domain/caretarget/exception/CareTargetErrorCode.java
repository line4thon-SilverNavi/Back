package org.likelion._thon.silver_navi.domain.caretarget.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.likelion._thon.silver_navi.global.response.code.BaseResponseCode;

@Getter
@AllArgsConstructor
public enum CareTargetErrorCode implements BaseResponseCode {
    OCR_REQUEST_FAILED("OCR_502_1", 502, "Clova OCR API 요청 중 오류가 발생했습니다.");

    private final String code;
    private final int httpStatus;
    private final String message;
}
