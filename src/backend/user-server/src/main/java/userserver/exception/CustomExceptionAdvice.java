package userserver.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import userserver.payload.response.ErrorResponse;

import java.nio.file.AccessDeniedException;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class CustomExceptionAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorResponse> globalException(CustomException e, HttpServletRequest request) {
        CustomUserCode code = e.getErrorCode();

        ErrorResponse errorResponse = new ErrorResponse(code.getStatus(), code.getCode(), code.getMessage(), request.getRequestURI().toString());

        return ResponseEntity.status(HttpStatus.valueOf(code.getStatus())).body(errorResponse);
    }


    @ExceptionHandler({MalformedJwtException.class, ExpiredJwtException.class, AuthenticationCredentialsNotFoundException.class, AccessDeniedException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ErrorResponse> handleJwtExceptions(Exception exception, HttpServletRequest request) {
        CustomUserCode customUserCode = null;

        if (exception instanceof MalformedJwtException) {
            customUserCode = CustomUserCode.MALFORMED_TOKEN;
        } else if (exception instanceof ExpiredJwtException) {
            customUserCode = CustomUserCode.EXPIRED_TOKEN;
        } else if (exception instanceof AuthenticationCredentialsNotFoundException) {
            customUserCode = CustomUserCode.NOT_EXIST_TOKEN;
        } else if (exception instanceof AccessDeniedException) {
            customUserCode = CustomUserCode.NOT_AUTHORIZED_TOKEN;
        }

        ErrorResponse errorResponse = new ErrorResponse(
                customUserCode.getStatus(),
                customUserCode.getCode(),
                customUserCode.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }
}