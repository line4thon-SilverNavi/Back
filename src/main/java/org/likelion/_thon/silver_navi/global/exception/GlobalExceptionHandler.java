package org.likelion._thon.silver_navi.global.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.likelion._thon.silver_navi.global.response.ErrorResponse;
import org.likelion._thon.silver_navi.global.response.code.ErrorResponseCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.springframework.validation.BindException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.resource.NoResourceFoundException;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    private final View error;

    public GlobalExceptionHandler(View error) {
        this.error = error;
    }

    // 필드에 선언된 검증 조건 위반 시 발생
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse<?>>handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        log.error("MethodArgumentNotValidException : {}",e.getMessage(), e);
        ErrorResponse<?> errorResponse = ErrorResponse.of(ErrorResponseCode.INVALID_HTTP_MESSAGE_BODY,e.getFieldError().getDefaultMessage());
        return  ResponseEntity.status(errorResponse.getHttpStatus()).body(errorResponse);
    }

    // 선언된 검증 조건 위반 시 발생
    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse<?>>handleBindException(BindException e){
        log.error("BindException : {}",e.getMessage(), e);
        ErrorResponse<?> errorResponse = ErrorResponse.of(ErrorResponseCode.INVALID_HTTP_MESSAGE_BODY,e.getFieldError().getDefaultMessage());
        return ResponseEntity.status(errorResponse.getHttpStatus()).body(errorResponse);
    }

    // RequestBody 등으로 전달 받은 JSON 바디의 파싱이 실패 했을 때
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse<?>>handleHttpMessageNotReadableException(HttpMessageNotReadableException e){
        log.error("HttpMessageNotReadableException : {}",e.getMessage(), e);

        // Enum 타입 오류인지 확인하고 공통 메시지 반환
        if (isEnumInvalidationError(e)) {
            ErrorResponse<?> errorResponse = ErrorResponse.of(
                    ErrorResponseCode.INVALID_HTTP_MESSAGE_BODY,
                    "입력된 값이 유효하지 않습니다. Enum 타입의 철자나 대소문자를 다시 확인해주세요."
            );
            return  ResponseEntity.status(errorResponse.getHttpStatus()).body(errorResponse);
        }

        ErrorResponse<?> errorResponse = ErrorResponse.from(ErrorResponseCode.INVALID_HTTP_MESSAGE_BODY);
        return  ResponseEntity.status(errorResponse.getHttpStatus()).body(errorResponse);
    }

    // 요청 파라미터 타입 변환에 실패 했을 때
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse<?>>handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e){
        log.error("MethodArgumentTypeMismatchException : {}",e.getMessage(), e);
        ErrorResponse<?> errorResponse = ErrorResponse.from(ErrorResponseCode.INVALID_HTTP_MESSAGE_PARAMETER);
        return  ResponseEntity.status(errorResponse.getHttpStatus()).body(errorResponse);
    }

    // Request Part 누락 시 발생
    @ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseEntity<ErrorResponse<?>>handleMissingServletRequestPartException(MissingServletRequestPartException e){
        log.error("MissingServletRequestPartException : {}",e.getMessage(), e);
        ErrorResponse<?> errorResponse = ErrorResponse.from(ErrorResponseCode.INVALID_HTTP_MESSAGE_PARAMETER);
        return  ResponseEntity.status(errorResponse.getHttpStatus()).body(errorResponse);
    }

    // 지원하지 않는 HTTP 메소드를 호출할 경우
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse<?>>handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e){
        log.error("HttpRequestMethodNotSupportedException : {}",e.getMessage(), e);
        ErrorResponse<?> errorResponse = ErrorResponse.from(ErrorResponseCode.UNSUPPORTED_HTTP_METHOD);
        return  ResponseEntity.status(errorResponse.getHttpStatus()).body(errorResponse);
    }

    // 존재하지 않는 앤드포인트 호출 시 발생
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse<?>>handleNoHandlerFoundException(NoHandlerFoundException e){
        log.error("NoHandlerFoundException : {}",e.getMessage(), e);
        ErrorResponse<?> errorResponse = ErrorResponse.from(ErrorResponseCode.NOT_FOUND_ENDPOINT);
        return  ResponseEntity.status(errorResponse.getHttpStatus()).body(errorResponse);
    }

    // 정적 리소드도 찾지 못했을 때 발생
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse<?>>handleNoResourceFoundException(NoResourceFoundException e){
        log.error("NoResourceFoundException : {}",e.getMessage(), e);
        ErrorResponse<?> errorResponse = ErrorResponse.from(ErrorResponseCode.NOT_FOUND_ENDPOINT);
        return  ResponseEntity.status(errorResponse.getHttpStatus()).body(errorResponse);
    }

    // 비즈니스 로직 에러
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse<?>>handleBaseException(BaseException e){
        log.error("BaseException : {}",e.getBaseResponseCode().getMessage(), e);
        ErrorResponse<?> errorResponse = ErrorResponse.from(e.getBaseResponseCode());
        return  ResponseEntity.status(errorResponse.getHttpStatus()).body(errorResponse);
    }

    // Multipart 요청 자체가 아닐 때 (Content-Type 틀림 등)
    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<ErrorResponse<?>> handleMultipartException(MultipartException e) {
        log.error("MultipartException : {}", e.getMessage(), e);
        ErrorResponse<?> errorResponse = ErrorResponse.of(
                ErrorResponseCode.INVALID_HTTP_MESSAGE_PARAMETER,
                "요청이 multipart/form-data 형식이 아니거나 파일이 누락되었습니다."
        );
        return ResponseEntity.status(errorResponse.getHttpStatus()).body(errorResponse);
    }

    // RequestParam 누락 시 발생
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse<?>> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.error("MissingServletRequestParameterException : {}", e.getMessage(), e);
        String parameterName = e.getParameterName();

        ErrorResponse<?> errorResponse = ErrorResponse.of(
                ErrorResponseCode.INVALID_HTTP_MESSAGE_PARAMETER,
                String.format("필수 요청 파라미터 '%s'가 누락되었습니다.", parameterName)
        );
        return ResponseEntity.status(errorResponse.getHttpStatus()).body(errorResponse);
    }

    // RequestParam, PathVariable 등 검증(@Validated) 실패 시 발생
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse<?>> handleConstraintViolationException(ConstraintViolationException e) {
        log.error("ConstraintViolationException : {}", e.getMessage(), e);

        // 여러 위반 중 첫 번째 메시지 선택
        String message = e.getConstraintViolations().stream()
                .findFirst()
                .map(v -> v.getMessage())
                .orElse("유효하지 않은 요청 파라미터입니다.");

        ErrorResponse<?> errorResponse = ErrorResponse.of(
                ErrorResponseCode.INVALID_HTTP_MESSAGE_PARAMETER,
                message
        );
        return ResponseEntity.status(errorResponse.getHttpStatus()).body(errorResponse);
    }

    // 나머지 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse<?>>handleException(Exception e){
        log.error("Exception : {}",e.getMessage(), e);
        ErrorResponse<?> errorResponse = ErrorResponse.from(ErrorResponseCode.SERVER_ERROR);
        return  ResponseEntity.status(errorResponse.getHttpStatus()).body(errorResponse);
    }

    private boolean isEnumInvalidationError(HttpMessageNotReadableException e) {
        Throwable rootCause = e.getRootCause();
        if (rootCause instanceof com.fasterxml.jackson.databind.exc.InvalidFormatException) {
            // 메시지에서 'value not one of declared' 패턴이 있는지 확인하여 Enum 오류임을 확신
            String message = rootCause.getMessage();
            return message != null && (message.contains("value not one of declared") || message.contains("not one of the values accepted for Enum class"));
        }
        return false;
    }
}
