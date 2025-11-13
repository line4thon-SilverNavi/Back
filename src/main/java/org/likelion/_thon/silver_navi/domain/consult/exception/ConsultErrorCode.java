package org.likelion._thon.silver_navi.domain.consult.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.likelion._thon.silver_navi.global.response.code.BaseResponseCode;

@Getter
@AllArgsConstructor
public enum ConsultErrorCode implements BaseResponseCode {

    CONSULT_STATUS_INVALID("CONSULT_400_1", 400, "유효하지 않은 상태 값입니다."),
    CONSULT_CATEGORY_INVALID("CONSULT_400_2", 400, "유효하지 않은 카테고리 값입니다."),
    CONSULT_ACCESS_DENIED("CONSULT_403_1", 403, "소속된 시설의 상담만 조작할 수 있습니다."),
    CONSULT_NOT_FOUND("CONSULT_404_1", 404, "해당 상담을 찾을 수 없습니다."),
    CONSULT_REPLY_ALREADY_EXISTS("CONSULT_409_1", 409, "이미 해당 상담에 답변을 작성하였습니다."),
    CONSULT_MODIFY_NOT_ALLOWED("CONSULT_409_2", 409, "답변이 등록된 상담은 정보를 변경할 수 없습니다.");

    private final String code;
    private final int httpStatus;
    private final String message;
}
