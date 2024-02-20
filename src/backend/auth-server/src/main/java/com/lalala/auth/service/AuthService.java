package com.lalala.auth.service;

import com.lalala.auth.domain.Status;
import com.lalala.auth.config.jwt.JwtUtils;
import com.lalala.auth.payload.request.SignInRequest;
import com.lalala.auth.payload.request.TokenRefreshRequest;
import com.lalala.auth.payload.response.UserAndTokenResponse;
import com.lalala.auth.repository.AuthRepository;
import com.lalala.exception.BusinessException;
import com.lalala.exception.ErrorCode;
import com.lalala.passport.PassportGenerator;
import com.lalala.passport.component.UserInfo;
import java.time.LocalDateTime;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lalala.auth.domain.User;
import jakarta.servlet.http.HttpServletRequest;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final PassportGenerator passportGenerator;
    private final RedisService redisService;

    @Transactional
    public ResponseEntity<UserAndTokenResponse> signIn(SignInRequest request) {
        User user = authRepository.findByEmail(request.email())
                        .orElseThrow(() -> new BusinessException(ErrorCode.EMAIL_NOT_FOUND));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new BusinessException(ErrorCode.LOGIN_FAILED);
        }

        String jwtAccess = jwtUtils.generateAccessJwt(user);
        ResponseCookie jwtAccessCookie = jwtUtils.generateAccessJwtCookie(jwtAccess);


        String refreshToken = jwtUtils.generateRefreshToken(String.valueOf(user.getId()));

        ResponseCookie jwtRefreshCookie = jwtUtils.generateRefreshJwtCookie(refreshToken);

        user.changeLastAccess(LocalDateTime.now());
        authRepository.save(user);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtAccessCookie.toString())
                .header(HttpHeaders.SET_COOKIE, jwtRefreshCookie.toString())
                .body(
                        UserAndTokenResponse.builder()
                                .id(user.getId())
                                .nickname(user.getNickname())
                                .access_token(jwtAccess)
                                .refresh_token(refreshToken)
                                .build());
    }

    @Transactional
    public ResponseEntity<Boolean> signOut(HttpServletRequest request) {
        String accessToken = jwtUtils.getAccessJwtFromCookies(request);
        String id = jwtUtils.getIdFromToken(accessToken);

        jwtUtils.deleteRefreshToken(id);

        ResponseCookie cleanJwtAccessCookie = jwtUtils.getCleanAccessJwtCookie();
        ResponseCookie cleanJwtRefreshCookie = jwtUtils.getCleanRefreshJwtCookie();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cleanJwtAccessCookie.toString())
                .header(HttpHeaders.SET_COOKIE, cleanJwtRefreshCookie.toString())
                .body(true);
    }

    public String generatePassport(String jwtPayload) {
        String userId = jwtUtils.getIdFromToken(jwtPayload);

        User user = authRepository.findById(Long.parseLong(userId))
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        return passportGenerator.generatePassport(
                new UserInfo(
                        user.getId(),
                        user.getEmail(),
                        user.getNickname(),
                        user.getRole().name(),
                        user.getStatus() == Status.ACTIVE,
                        user.getLastAccess().toString(),
                        user.getCreatedAt().toString(),
                        user.getUpdatedAt().toString()
                )
        );
    }

    @Transactional
    public ResponseEntity<UserAndTokenResponse> accessTokenRefresh(TokenRefreshRequest request) {

        String userId = jwtUtils.getIdFromToken(request.accessToken());

        User user = authRepository.findById(Long.parseLong(userId))
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        if (!redisService.isExistKey(userId)) {
            throw new BusinessException(ErrorCode.INVALID_TOKEN);
        }


        String jwtAccess = jwtUtils.generateAccessJwt(user);
        ResponseCookie jwtAccessCookie = jwtUtils.generateAccessJwtCookie(jwtAccess);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtAccessCookie.toString())
                .body(
                        UserAndTokenResponse.builder()
                                .id(user.getId())
                                .nickname(user.getNickname())
                                .access_token(jwtAccess)
                                .refresh_token(request.refreshToken())
                                .build());
    }
}
