package org.likelion._thon.silver_navi.domain.review.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.likelion._thon.silver_navi.global.response.code.BaseResponseCode;

@Getter
@AllArgsConstructor
public enum ReviewErrorCode implements BaseResponseCode {
    REVIEW_ALREADY_EXISTS("REVIEW_409_1", 409, "이미 해당 시설에 리뷰를 작성하셨습니다.");

    private final String code;
    private final int httpStatus;
    private final String message;
}
