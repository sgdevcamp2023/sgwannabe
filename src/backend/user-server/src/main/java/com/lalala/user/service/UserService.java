package com.lalala.user.service;

import com.lalala.user.payload.response.UserInfoResponse;
import com.lalala.user.repository.UserRepository;
import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lalala.exception.BusinessException;
import com.lalala.exception.ErrorCode;
import com.lalala.passport.component.UserInfo;
import com.lalala.response.BaseResponse;
import com.lalala.user.domain.Role;
import com.lalala.user.domain.Status;
import com.lalala.user.domain.User;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final RedisService redisService;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    public BaseResponse<Boolean> sendAuthCodeByEmail(String email) {
        validateEmailDuplicate(email);

        String authCode = createAuthCode();

        redisService.setRedisTemplate(email, authCode, Duration.ofMinutes(3));

        emailService.sendSignUpMail(email, authCode);

        return BaseResponse.from(HttpStatus.OK.value(), "메일 전송에 성공했습니다.", true);
    }

    @Transactional
    public BaseResponse<User> signUp(String email, String password, String nickname) {
        validateEmailDuplicate(email);

        String encodePassword = passwordEncoder.encode(password);

        User user =
                User.builder()
                        .email(email)
                        .password(encodePassword)
                        .nickname(nickname)
                        .role(Role.USER)
                        .status(Status.ACTIVE)
                        .build();

        userRepository.save(user);

        return BaseResponse.from(HttpStatus.OK.value(), "회원가입에 성공했습니다.", user);
    }

    public void validateEmailDuplicate(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new BusinessException(ErrorCode.DUPLICATE_EMAIL);
        }
    }

    public String createAuthCode() {
        return Integer.toString(ThreadLocalRandom.current().nextInt(100000, 1000000));
    }

    public BaseResponse<Boolean> verifyAuthCode(String email, String code) {
        String validateCode = redisService.getRedisTemplateValue(email);

        if (!code.equals(validateCode)) {
            throw new BusinessException(ErrorCode.NOT_VALID_CODE);
        }

        redisService.deleteRedisTemplateValue(email);

        return BaseResponse.from(HttpStatus.OK.value(), "인증코드가 일치합니다", true);
    }

    @Transactional
    public BaseResponse<Boolean> passwordChange(UserInfo userInfo, String newPassword) {
        User user =
                userRepository
                        .findById(userInfo.id())
                        .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        String encodePassword = passwordEncoder.encode(newPassword);
        user.changePassword(encodePassword);

        userRepository.save(user);

        return BaseResponse.from(HttpStatus.OK.value(), "비밀번호 변경에 성공했습니다.", true);
    }

    @Transactional
    public BaseResponse<Boolean> profileChange(UserInfo userInfo, String profile) {
        User user =
                userRepository
                        .findById(userInfo.id())
                        .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        user.changeProfile(profile);

        userRepository.save(user);

        return BaseResponse.from(HttpStatus.OK.value(), "프로필 이미지 변경에 성공했습니다.", true);
    }

    public BaseResponse<UserInfoResponse> userInfo(UserInfo userInfo) {
        User user =
                userRepository
                        .findById(userInfo.id())
                        .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));


        UserInfoResponse userInfoResponse = UserInfoResponse.builder()
                .id(user.getId())
                .profile(user.getProfile())
                .nickname(user.getNickname())
                .build();

        return BaseResponse.from(HttpStatus.OK.value(), "회원정보 조회에 성공했습니다.", userInfoResponse);
    }
}
