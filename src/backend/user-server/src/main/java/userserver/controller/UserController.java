package userserver.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import userserver.config.security.UserDetailsImpl;
import userserver.exception.CustomException;
import userserver.exception.CustomUserCode;
import userserver.payload.request.*;
import userserver.service.UserService;


@Slf4j
@RestController
@RequestMapping("/v1/api")
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;

    /**
     * 이메일 인증 코드 전송
     */
    @PostMapping("/email")
    public ResponseEntity<?> sendAuthCodeByEmail(@Validated @RequestBody EmailAuthCodeRequest request) {
        return userService.sendAuthCodeByEmail(request);
    }

    /**
     * 인증 코드 입력 검증
     */
    @PostMapping("/verification")
    public ResponseEntity<?> verifyAuthCode(@Validated @RequestBody EmailVerifyRequest request) {
        return userService.verifyAuthCode(request);
    }

    /**
     * 회원가입
     */
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Validated @RequestBody SignUpRequest request) {

        return userService.signUp(request);
    }

    /**
     * 비밀번호 변경
     */
    @PostMapping("/password-change")
    public ResponseEntity<?> passwordChange(@AuthenticationPrincipal UserDetailsImpl userDetails, @Validated @RequestBody PasswordChangeRequest request) {
        if (userDetails == null) {
            throw new CustomException(CustomUserCode.CLIENT_UNAUTHORIZED);
        }
        return userService.passwordChange(userDetails.getUser(), request);
    }

    /**
     * 프로필 이미지 변경
     */
    @PostMapping("/profile-change")
    public ResponseEntity<?> profileChange(@AuthenticationPrincipal UserDetailsImpl userDetails, @Validated @RequestBody ProfileChangeRequest request) {
        if (userDetails == null) {
            throw new CustomException(CustomUserCode.CLIENT_UNAUTHORIZED);
        }
        return userService.profileChange(userDetails.getUser(), request);
    }
}
