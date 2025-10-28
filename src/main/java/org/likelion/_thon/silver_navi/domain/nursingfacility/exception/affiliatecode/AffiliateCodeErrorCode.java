package org.likelion._thon.silver_navi.domain.nursingfacility.exception.affiliatecode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.likelion._thon.silver_navi.global.response.code.BaseResponseCode;

@Getter
@AllArgsConstructor
public enum AffiliateCodeErrorCode implements BaseResponseCode {

    AFFILIATECODE_ALREADY_ISSUED("CODE_409_1", 409, "해당 시설에는 이미 제휴 코드가 발급되었습니다."),
    AFFILIATECODE_NOT_FOUND("CODE_404_1", 404, "제휴코드가 존재하지 않습니다."),
    AFFILIATECODE_MISMATCH("CODE_400_1", 400, "제휴코드가 일치하지 않습니다.");

    private final String code;
    private final int httpStatus;
    private final String message;
}
