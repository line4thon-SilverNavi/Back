package org.likelion._thon.silver_navi.domain.program.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.likelion._thon.silver_navi.global.response.code.BaseResponseCode;

@Getter
@AllArgsConstructor
public enum ApplicationErrorCode implements BaseResponseCode {

    APPLICATION_STATUS_INVALID("APPLICATION_400_1", 400, "유효하지 않은 상태 값입니다."),
    APPLICATION_REASON_REQUIRED("APPLICATION_400_2", 400, "거부 사유를 입력해주세요."),
    APPLICATION_ACCESS_DENIED("APPLICATION_403_1", 403, "소속된 시설의 신청만 조작할 수 있습니다."),
    APPLICATION_NOT_FOUND("APPLICATION_404_1", 404, "해당 신청을 찾을 수 없습니다."),
    APPLICATION_ALREADY_PROCESSED("APPLICATION_409_1", 409, "이미 처리된(승인 또는 거절된) 신청입니다.");

    private final String code;
    private final int httpStatus;
    private final String message;
}
