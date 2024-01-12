package userserver.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import userserver.payload.request.EmailAuthCodeRequest;
import userserver.payload.request.EmailVerifyRequest;
import userserver.payload.request.SignUpRequest;
import userserver.service.UserService;


@Slf4j
@RestController
@RequestMapping("/v1/user-service")
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;

    /**
     * 이메일 인증 코드 전송
     */
    @PostMapping("/email")
    public ResponseEntity<?> sendAuthCodeByEmail(@Validated @RequestBody EmailAuthCodeRequest request) {
        log.info("POST 이메일 인증 코드 전송");
        userService.sendAuthCodeByEmail(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 인증 코드 입력 검증
     */
    @PostMapping("/verification")
    public ResponseEntity<?> verifyAuthCode(@Validated @RequestBody EmailVerifyRequest request) {
        log.info("POST 이메일 코드 검증 진행");
        userService.verifyAuthCode(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Validated @RequestBody SignUpRequest request) {
        log.info("POST 회원가입 요청");
        userService.signUp(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
