package authserver.service;

import authserver.config.jwt.JwtUtils;
import authserver.domain.User;
import authserver.payload.request.SignInRequest;
import authserver.payload.response.UserAndTokenResponse;
import authserver.repository.AuthRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
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

    @Override
    @Transactional
    public ResponseEntity<UserAndTokenResponse> signIn(SignInRequest request) {
        User user = authRepository.findByEmail(request.email()).orElseThrow(
                ()-> new UsernameNotFoundException("이메일을 찾을 수 없습니다"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new RuntimeException();
        }

        ResponseCookie jwtAccessCookie = jwtUtils.generateAccessJwtCookie(user);

        String refreshToken = jwtUtils.generateRefreshToken(String.valueOf(user.getId()));
        ResponseCookie jwtRefreshCookie = jwtUtils.generateRefreshJwtCookie(refreshToken);

        user.changeLastAccess(LocalDateTime.now());
        authRepository.save(user);

        return ResponseEntity.ok()
                        .header(HttpHeaders.SET_COOKIE, jwtAccessCookie.toString())
                        .header(HttpHeaders.SET_COOKIE, jwtRefreshCookie.toString())
                        .body(UserAndTokenResponse.builder()
                                .id(user.getId())
                                .nickname(user.getNickname())
                                .access_token(jwtAccessCookie.toString())
                                .refresh_token(refreshToken)
                                .build());
    }

    @Override
    @Transactional
    public ResponseEntity<?> signOut(HttpServletRequest request) {
        String accessToken = jwtUtils.getAccessJwtFromCookies(request);
        String id = jwtUtils.getIdFromToken(accessToken);

        jwtUtils.deleteRefreshToken(id);

        ResponseCookie cleanJwtAccessCookie = jwtUtils.getCleanAccessJwtCookie();
        ResponseCookie cleanJwtRefreshCookie = jwtUtils.getCleanRefreshJwtCookie();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cleanJwtAccessCookie.toString())
                .header(HttpHeaders.SET_COOKIE, cleanJwtRefreshCookie.toString())
                .body(null);
    }
}
