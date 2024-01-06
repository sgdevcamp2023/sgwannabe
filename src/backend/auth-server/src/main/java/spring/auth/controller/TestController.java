package spring.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.auth.common.annotation.ApiErrorException;
import spring.auth.common.exception.SuccessMessageResponse;
import spring.auth.common.exception.docs.OtherServiceExceptionDocs;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
    @GetMapping("/all")
    public ResponseEntity<?> allAccess() {
        return ResponseEntity.ok().body(null);
    }

    @Operation(summary = "Get User Page", description = "회원 전용 페이지입니다")
    @ApiErrorException(OtherServiceExceptionDocs.class)
    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> userAccess() {
        return ResponseEntity.ok().body(new SuccessMessageResponse("User Content"));
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> adminAccess() {
        return ResponseEntity.ok().body(null);
    }
}