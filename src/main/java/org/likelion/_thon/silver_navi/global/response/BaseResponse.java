package org.likelion._thon.silver_navi.global.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.likelion._thon.silver_navi.global.response.code.BaseResponseCode;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@ToString
@RequiredArgsConstructor
public class BaseResponse {
    private final Boolean isSuccess;
    private final String code;
    private final String message;
    private final String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

    public BaseResponse of(Boolean isSuccess, BaseResponseCode baseResponseCode) {
        return new BaseResponse(isSuccess, baseResponseCode.getCode(), baseResponseCode.getMessage());
    }

    public BaseResponse of(Boolean isSuccess, BaseResponseCode baseResponseCode, String message) {
        return new BaseResponse(isSuccess, baseResponseCode.getCode(), message);
    }

    public BaseResponse of(Boolean isSuccess, String code, String message){
        return new BaseResponse(isSuccess, code, message);
    }
}
