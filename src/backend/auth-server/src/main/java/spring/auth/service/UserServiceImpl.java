package spring.auth.service;

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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.auth.common.exception.SuccessMessageResponse;
import spring.auth.common.exception.request.EmptyToken;
import spring.auth.common.exception.request.ExpiredToken;
import spring.auth.common.exception.response.DuplicateEmail;
import spring.auth.common.exception.response.FailLogin;
import spring.auth.config.jwt.JwtUtils;
import spring.auth.config.security.UserDetailsImpl;
import spring.auth.domain.ERole;
import spring.auth.domain.User;
import spring.auth.dto.request.ChangePasswordRequest;
import spring.auth.dto.request.SignInRequest;
import spring.auth.dto.request.SignUpRequest;
import spring.auth.dto.response.UserAndTokenResponse;
import spring.auth.dto.response.UserInfoResponse;
import spring.auth.repository.UserRepository;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final RedisService redisService;



    @Override
    @Transactional
    public ResponseEntity<UserInfoResponse> signUp(SignUpRequest request) {

        // 이메일 중복 확인
        if (userRepository.existsByEmail(request.email())) {
            throw DuplicateEmail.EXCEPTION;
        }

        User entity = request.toEntity(hashPassword(request.password()));
        entity.changeUserRole(ERole.ROLE_USER);

        userRepository.save(entity);

        return ResponseEntity.ok()
                .body(UserInfoResponse.builder().
                        username(entity.getUsername()).
                        email(entity.getEmail()).
                        build());
    }

    @Override
    @Transactional
    public ResponseEntity<UserAndTokenResponse> signIn(SignInRequest request) {
        // 이메일 존재 확인
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> FailLogin.EXCEPTION);

        // 유효한 이메일, 비밀번호인지 확인
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw FailLogin.EXCEPTION;
        }

//        Authentication authenticate = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(request.email(), request.password())
//        );
//        SecurityContextHolder.getContext().setAuthentication(authenticate);


        ResponseCookie jwtAccessCookie = jwtUtils.generateAccessJwtCookie(user);

        String refreshToken = jwtUtils.generateRefreshToken(user.getEmail());
        ResponseCookie jwtRefreshCookie = jwtUtils.generateRefreshJwtCookie(refreshToken);


        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtAccessCookie.toString())
                .header(HttpHeaders.SET_COOKIE, jwtRefreshCookie.toString())
                .body(UserAndTokenResponse.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .accessToken(jwtAccessCookie.toString())
                        .refreshToken(refreshToken)
                        .build());
    }

    @Override
    public ResponseEntity<UserAndTokenResponse> refreshToken(HttpServletRequest request) {
        String refreshToken = jwtUtils.getRefreshJwtFromCookies(request);

        if ((refreshToken != null) && (!refreshToken.isEmpty())) {
            String email = redisService.getRedisTemplateValue(refreshToken);
            return userRepository.findByEmail(email)
                    .map(user -> {
                        ResponseCookie jwtAccessCookie = jwtUtils.generateAccessJwtCookie(new UserDetailsImpl(user).getUser());
                        return ResponseEntity.ok()
                                .header(HttpHeaders.SET_COOKIE, jwtAccessCookie.toString())
                                .body(UserAndTokenResponse.builder()
                                        .id(user.getId())
                                        .accessToken(jwtAccessCookie.toString())
                                        .refreshToken(refreshToken)
                                        .username(user.getUsername())
                                        .build());
                    })
                    .orElseThrow(() -> ExpiredToken.EXCEPTION);
        }
        // refresh token 만료 -> 재로그인 필요
        throw EmptyToken.EXCEPTION;
    }

    @Override
    public ResponseEntity<?> signOut(HttpServletRequest request) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String refreshToken = jwtUtils.getRefreshJwtFromCookies(request);
        if (!principal.toString().equals("anonymousUser")) {
            redisService.deleteRedisTemplateValue(refreshToken);
        }

        ResponseCookie cleanJwtAccessCookie = jwtUtils.getCleanAccessJwtCookie();
        ResponseCookie cleanJwtRefreshCookie = jwtUtils.getCleanRefreshJwtCookie();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cleanJwtAccessCookie.toString())
                .header(HttpHeaders.SET_COOKIE, cleanJwtRefreshCookie.toString())
                .body(new SuccessMessageResponse("로그아웃 성공"));

    }

    @Override
    public String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }


    @Override
    @Transactional
    public ResponseEntity<?> changePassword(User user, ChangePasswordRequest request) {
        user.changePassword(hashPassword(request.password()));
        userRepository.save(user);

        return ResponseEntity.ok()
                .body(new SuccessMessageResponse("비밀번호 변경 성공"));
    }


    // /profile-img?image=abc
    @Override
    @Transactional
    public ResponseEntity<?> uploadProfile(User user, String upload) {
        user.changeProfileImg(upload);
        userRepository.save(user);
        return ResponseEntity.ok()
                .body(new SuccessMessageResponse("프로필 이미지 업로드 성공"));
    }

    @Override
    @Transactional
    public ResponseEntity<?> deleteProfile(User user) {
        user.changeProfileImg(null);
        userRepository.save(user);
        return ResponseEntity.ok()
                .body(new SuccessMessageResponse("프로필 이미지 삭제 성공"));
    }



}