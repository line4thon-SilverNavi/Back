package org.likelion._thon.silver_navi.domain.program.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.likelion._thon.silver_navi.global.response.code.BaseResponseCode;

@Getter
@AllArgsConstructor
public enum ProgramErrorCode implements BaseResponseCode {

    PROGRAM_CATEGORY_INVALID("PROGRAM_400_1", 400, "유효하지 않은 카테고리 값입니다.");

    private final String code;
    private final int httpStatus;
    private final String message;
}
