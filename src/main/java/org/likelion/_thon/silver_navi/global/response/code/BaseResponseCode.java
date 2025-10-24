package org.likelion._thon.silver_navi.global.response.code;

public interface BaseResponseCode {
    String getCode();
    int getHttpStatus();
    String getMessage();
}
