package org.likelion._thon.silver_navi.domain.program.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.likelion._thon.silver_navi.global.response.code.BaseResponseCode;

@Getter
@AllArgsConstructor
public enum ApplicationErrorCode implements BaseResponseCode {

    APPLICATION_STATUS_INVALID("APPLICATION_400_1", 400, "유효하지 않은 상태 값입니다.");

    private final String code;
    private final int httpStatus;
    private final String message;
}
