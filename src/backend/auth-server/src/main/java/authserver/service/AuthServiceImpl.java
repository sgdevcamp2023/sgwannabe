package authserver.service;

import authserver.config.jwt.JwtUtils;
import authserver.domain.User;
import authserver.exception.CustomException;
import authserver.exception.CustomUserCode;
import authserver.payload.request.SignInRequest;
import authserver.payload.response.SuccessMessageResponse;
import authserver.payload.response.UserAndTokenResponse;
import authserver.repository.AuthRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final RedisService redisService; //TODO redisservice jwtutils로 분리
    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public ResponseEntity<UserAndTokenResponse> signIn(SignInRequest request) {
        // DB에 이메일 조회
        User user = authRepository.findByEmail(request.email()).orElseThrow(
                ()-> new CustomException(CustomUserCode.FAIL_LOGIN));

        // 이메일과 비밀번호 정보가 일치하는지 확인
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new CustomException(CustomUserCode.FAIL_LOGIN);
        }
        // 계정 활성화 여부 확인

        // AccessToken 생성
        ResponseCookie jwtAccessCookie = jwtUtils.generateAccessJwtCookie(user);

        // Refresh Token 생성 후 redis에 저장
        String refreshToken = jwtUtils.generateRefreshToken(String.valueOf(user.getId()));
        ResponseCookie jwtRefreshCookie = jwtUtils.generateRefreshJwtCookie(refreshToken);

        // DB에 최근 접속 시각 업데이트
        user.changeLastAccess(LocalDateTime.now());
        authRepository.save(user);


        return ResponseEntity.ok()
                        .header(HttpHeaders.SET_COOKIE, jwtAccessCookie.toString())
                        .header(HttpHeaders.SET_COOKIE, jwtRefreshCookie.toString())
                        .body(UserAndTokenResponse.builder()
                                .id(user.getId())
                                .nickname(user.getNickname())
                                .accessToken(jwtAccessCookie.toString()) //TODO accessToken 파싱
                                .refreshToken(refreshToken)
                                .build());
    }


}
