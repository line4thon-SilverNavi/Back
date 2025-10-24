package org.likelion._thon.silver_navi.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.likelion._thon.silver_navi.global.response.code.BaseResponseCode;

@Getter
@AllArgsConstructor
public class BaseException extends RuntimeException{
    private final BaseResponseCode baseResponseCode;
}
