package authserver.exception;

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
import authserver.payload.response.ErrorResponse;

@RestControllerAdvice
@RequiredArgsConstructor
public class CustomExceptionAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorResponse> globalException(CustomException e, HttpServletRequest request) {
        CustomUserCode code = e.getErrorCode();

        ErrorResponse errorResponse = new ErrorResponse(code.getStatus(), code.getCode(), code.getMessage(), request.getRequestURI().toString());

        return ResponseEntity.status(HttpStatus.valueOf(code.getStatus())).body(errorResponse);
    }
}
