package com.lalala.user.controller;

import com.lalala.user.payload.request.EmailAuthCodeRequest;
import com.lalala.user.payload.request.EmailVerifyRequest;
import com.lalala.user.payload.request.PasswordChangeRequest;
import com.lalala.user.payload.request.ProfileChangeRequest;
import com.lalala.user.payload.request.SignUpRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.lalala.aop.AuthenticationContext;
import com.lalala.aop.PassportAuthentication;
import com.lalala.response.BaseResponse;
import com.lalala.user.domain.User;
import com.lalala.user.service.UserService;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /** 이메일 인증 코드 전송 */
    @PostMapping("/email")
    public BaseResponse<Boolean> sendAuthCodeByEmail(@RequestBody EmailAuthCodeRequest request) {
        return userService.sendAuthCodeByEmail(request.email());
    }

    /** 인증 코드 입력 검증 */
    @PostMapping("/verification")
    public BaseResponse<Boolean> verifyAuthCode(
            @Validated @RequestBody EmailVerifyRequest request, Errors errors) {
        return userService.verifyAuthCode(request.email(), request.code());
    }

    /** 회원가입 */
    @PostMapping("/signup")
    public BaseResponse<User> signup(@RequestBody SignUpRequest request) {
        return userService.signUp(request.email(), request.password(), request.nickname());
    }

    /** 비밀번호 변경 */
    @PostMapping("/password-change")
    @PassportAuthentication
    public BaseResponse<Boolean> passwordChange(@RequestBody PasswordChangeRequest request) {
        return userService.passwordChange(AuthenticationContext.getUserInfo(), request.password());
    }

    /** 프로필 이미지 변경 */
    @PostMapping("/profile-change")
    @PassportAuthentication
    public BaseResponse<Boolean> profileChange(@RequestBody ProfileChangeRequest request) {
        return userService.profileChange(AuthenticationContext.getUserInfo(), request.profile());
    }
}
