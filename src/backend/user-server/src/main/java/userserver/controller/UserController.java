package userserver.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import userserver.payload.request.EmailAuthCodeRequest;
import userserver.service.RedisService;
import userserver.service.UserService;
import userserver.service.UserServiceImpl;


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
}
