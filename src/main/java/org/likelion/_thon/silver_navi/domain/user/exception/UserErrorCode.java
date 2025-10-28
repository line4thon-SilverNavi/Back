package org.likelion._thon.silver_navi.domain.user.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.likelion._thon.silver_navi.global.response.code.BaseResponseCode;

@Getter
@AllArgsConstructor
public enum UserErrorCode implements BaseResponseCode {
    INVALID_CREDENTIALS("USER_401_1", 401, "아이디 또는 비밀번호를 다시 확인해주세요"),
    USER_NOT_FOUND("USER_404_1",404,"존재하지 않는 사용자입니다."),
    USER_ID_ALREADY_EXIST("SIGNUP_409_1", 409, "이미 존재하는 아이디입니다.");

    private final String code;
    private final int httpStatus;
    private final String message;
}
