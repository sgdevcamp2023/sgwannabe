package userserver.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import userserver.payload.response.ErrorResponse;

@RestControllerAdvice
@RequiredArgsConstructor
public class CustomExceptionAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorResponse> globalException(CustomException e, HttpServletRequest request) {
        CustomUserCode code = e.getErrorCode();

        ErrorResponse errorResponse = new ErrorResponse(code.getStatus(), code.getCode(), code.getMessage(), request.getRequestURI().toString());

        return ResponseEntity.status(HttpStatus.valueOf(code.getStatus())).body(errorResponse);
    }


    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ErrorResponse> handleSignatureException(HttpServletRequest request) {
        CustomUserCode invalidSignature = CustomUserCode.INVALID_SIGNATURE_TOKEN;
        ErrorResponse errorResponse = new ErrorResponse(invalidSignature.getStatus(), invalidSignature.getCode(), invalidSignature.getMessage(), request.getRequestURI().toString());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<ErrorResponse> handleMalformedJwtException(HttpServletRequest request) {
        CustomUserCode malformedToken = CustomUserCode.MALFORMED_TOKEN;
        ErrorResponse errorResponse = new ErrorResponse(malformedToken.getStatus(), malformedToken.getCode(), malformedToken.getMessage(), request.getRequestURI().toString());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorResponse> handleExpiredJwtException(HttpServletRequest request) {
        CustomUserCode expiredToken = CustomUserCode.EXPIRED_TOKEN;
        ErrorResponse errorResponse = new ErrorResponse(expiredToken.getStatus(), expiredToken.getCode(), expiredToken.getMessage(), request.getRequestURI().toString());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }
}