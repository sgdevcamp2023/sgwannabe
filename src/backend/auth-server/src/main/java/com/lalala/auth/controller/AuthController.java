package com.lalala.auth.controller;

import com.lalala.auth.service.AuthService;
import com.lalala.response.BaseResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lalala.auth.payload.request.SignInRequest;
import com.lalala.auth.payload.response.UserAndTokenResponse;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<UserAndTokenResponse> singInUser(
            @Validated @RequestBody SignInRequest request) {
        return authService.signIn(request);
    }

    @PostMapping("signout")
    public ResponseEntity<?> signOutUser(HttpServletRequest request) {
        return authService.signOut(request);
    }

    @PostMapping("/passport")
    public BaseResponse<String> generatePassport(@RequestHeader("Authorization") String jwtPayload) {
        return BaseResponse.from(
                HttpStatus.OK,
                "패스포트를 발급했습니다.",
                authService.generatePassport(jwtPayload)
        );
    }
}
