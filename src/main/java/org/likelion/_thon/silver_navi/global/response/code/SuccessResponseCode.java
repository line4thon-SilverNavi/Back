package org.likelion._thon.silver_navi.global.response.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static org.likelion._thon.silver_navi.global.constant.StaticValue.*;

@Getter
@AllArgsConstructor
public enum SuccessResponseCode implements BaseResponseCode {
    SUCCESS_OK("GLOBAL_200",OK,"호출에 성공하였습니다."),
    SUCCESS_CREATED("GLOBAL_201",CREATED,"호출에 성공하였습니다.");

    private String code;
    private int httpStatus;
    private String message;
}
