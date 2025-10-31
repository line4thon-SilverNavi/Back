package org.likelion._thon.silver_navi.domain.program.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.likelion._thon.silver_navi.global.response.code.BaseResponseCode;

@Getter
@AllArgsConstructor
public enum ProgramErrorCode implements BaseResponseCode {

    PROGRAM_CATEGORY_INVALID("PROGRAM_400_1", 400, "유효하지 않은 카테고리 값입니다."),
    PROGRAM_ACCESS_DENIED("PROGRAM_403_1", 403, "소속된 시설의 프로그램만 조회할 수 있습니다."),
    PROGRAM_NOT_FOUND("PROGRAM_404_1", 404, "해당 프로그램을 찾을 수 없습니다.");

    private final String code;
    private final int httpStatus;
    private final String message;
}
