package com.lalala.reactive.exception;

import java.nio.file.AccessDeniedException;

import lombok.extern.slf4j.Slf4j;

import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.MethodNotAllowedException;

import com.lalala.exception.BusinessException;
import com.lalala.exception.ErrorCode;
import com.lalala.exception.ErrorResponse;
import reactor.core.publisher.Mono;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    private Mono<ErrorResponse> handleException(Exception e, ErrorCode errorCode) {
        return Mono.just(ErrorResponse.of(errorCode));
    }

    /**
     * javax.validation.Valid or @Validated 으로 binding error 발생시 발생한다. HttpMessageConverter 에서 등록한
     * HttpMessageConverter binding 못할경우 발생 주로 @RequestBody, @RequestPart 어노테이션에서 발생
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected Mono<ErrorResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        log.error("handleMethodArgumentNotValidException", e);
        return handleException(e, ErrorCode.INVALID_PARAMETER);
    }

    /**
     * @ModelAttribut 으로 binding error 발생시 BindException 발생한다. ref
     * https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-ann-modelattrib-method-args
     */
    @ExceptionHandler(BindException.class)
    protected Mono<ErrorResponse> handleBindException(BindException e) {
        log.error("handleBindException", e);
        return handleException(e, ErrorCode.INVALID_PARAMETER);
    }

    /** enum type 일치하지 않아 binding 못할 경우 발생 주로 @RequestParam enum으로 binding 못했을 경우 발생 */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected Mono<ErrorResponse> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException e) {
        log.error("handleMethodArgumentTypeMismatchException", e);
        return handleException(e, ErrorCode.INVALID_PARAMETER);
    }

    /** 지원하지 않은 HTTP method 호출 할 경우 발생 */
    @ExceptionHandler(MethodNotAllowedException.class)
    protected Mono<ErrorResponse> handleMethodNotAllowedException(MethodNotAllowedException e) {
        log.error("handle MethodNotAllowedException", e);
        return handleException(e, ErrorCode.METHOD_NOT_ALLOWED);
    }

    /** Authentication 객체가 필요한 권한을 보유하지 않은 경우 발생합 */
    @ExceptionHandler(AccessDeniedException.class)
    protected Mono<ErrorResponse> handleAccessDeniedException(AccessDeniedException e) {
        log.error("handleAccessDeniedException", e);
        return handleException(e, ErrorCode.HANDLE_ACCESS_DENIED);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected Mono<ErrorResponse> handleIllegalArgumentException(Exception e) {
        log.error("handleIllegalArgumentException", e);
        return handleException(e, ErrorCode.INVALID_PARAMETER);
    }

    @ExceptionHandler(IllegalStateException.class)
    protected Mono<ErrorResponse> handleIllegalStateException(Exception e) {
        log.error("handleIllegalStateException", e);
        return handleException(e, ErrorCode.INVALID_PARAMETER);
    }

    @ExceptionHandler(BusinessException.class)
    protected Mono<ErrorResponse> handleBusinessException(final BusinessException e) {
        log.error("handleBusinessException", e);
        return handleException(e, e.getErrorCode());
    }

    @ExceptionHandler(Exception.class)
    protected Mono<ErrorResponse> handleException(Exception e) {
        log.error("handleException", e);
        return handleException(e, ErrorCode.UNKNOWN_ERROR);
    }
}
