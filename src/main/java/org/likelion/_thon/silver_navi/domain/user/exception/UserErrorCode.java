package org.likelion._thon.silver_navi.domain.user.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.likelion._thon.silver_navi.global.response.code.BaseResponseCode;

@Getter
@AllArgsConstructor
public enum UserErrorCode implements BaseResponseCode {
    USER_ID_ALREADY_EXIST("SIGNUP_409_1", 409, "이미 존재하는 아이디입니다.");

    private final String code;
    private final int httpStatus;
    private final String message;
}
