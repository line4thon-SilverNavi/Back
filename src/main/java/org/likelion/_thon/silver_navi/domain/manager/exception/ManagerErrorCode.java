package org.likelion._thon.silver_navi.domain.manager.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.likelion._thon.silver_navi.global.response.code.BaseResponseCode;

@Getter
@RequiredArgsConstructor
public enum ManagerErrorCode implements BaseResponseCode {

    MANAGER_PASSWORD_INVALID("MANAGER_401_1", 401, "비밀번호가 일치하지 않습니다."),
    MANAGER_NOT_FOUND("MANAGER_404_1",404,"존재하지 않는 시설 관리자입니다."),
    MANAGER_LOGINID_DUPLICATE("MANAGER_409_1", 409, "이미 존재하는 아이디입니다.");

    private final String code;
    private final int httpStatus;
    private final String message;
}
