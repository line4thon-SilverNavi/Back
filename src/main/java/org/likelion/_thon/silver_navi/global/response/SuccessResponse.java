package org.likelion._thon.silver_navi.global.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.likelion._thon.silver_navi.global.response.code.BaseResponseCode;
import org.likelion._thon.silver_navi.global.response.code.SuccessResponseCode;

@Getter
@ToString
@JsonPropertyOrder({"isSuccess","timestamp","code","httpStatus","message","data"})
public class SuccessResponse<T> extends BaseResponse{
    private final int httpStatus;
    private final T data;

    @Builder
    public SuccessResponse(T data, BaseResponseCode baseResponseCode) {
        super(true,baseResponseCode.getCode(),baseResponseCode.getMessage());
        this.httpStatus = baseResponseCode.getHttpStatus();
        this.data = data;
    }
    @Builder
    public SuccessResponse(T data, BaseResponseCode baseResponseCode, String message) {
        super(true,baseResponseCode.getCode(),message);
        this.httpStatus = baseResponseCode.getHttpStatus();
        this.data = data;
    }

    public static <T> SuccessResponse<T> created(T data) {
        return new SuccessResponse<>(data, SuccessResponseCode.SUCCESS_CREATED);
    }

    public static <T> SuccessResponse<T> from(T data){
        return new SuccessResponse<>(data, SuccessResponseCode.SUCCESS_OK);
    }

    public static SuccessResponse<?> empty(){
        return new SuccessResponse<>(null, SuccessResponseCode.SUCCESS_OK);
    }

    public static SuccessResponse<?> emptyCustom(String message){
        return new SuccessResponse<>(null, SuccessResponseCode.SUCCESS_OK, message);
    }

    public static <T> SuccessResponse<T> of(T data, BaseResponseCode baseResponseCode){
        return new SuccessResponse<>(data, baseResponseCode);
    }

    public static <T> SuccessResponse<T> from(BaseResponseCode baseResponseCode){
        return new SuccessResponse<>(null, baseResponseCode);
    }
}
