package authserver.service;

import authserver.domain.User;
import authserver.exception.CustomUserCode;
import authserver.payload.request.SignInRequest;
import authserver.payload.response.SuccessMessageResponse;
import authserver.payload.response.UserAndTokenResponse;
import authserver.repository.AuthRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final AuthRepository authRepository;
    @Override
    @Transactional
    public ResponseEntity<UserAndTokenResponse> signIn(SignInRequest request) {

        User user = authRepository.findByEmail(request.email()).orElseThrow(
                ()-> new UsernameNotFoundException("이메일이 존재하지 않습니다"));

        user.changeLastAccess(LocalDateTime.now());
        authRepository.save(user);

        return ResponseEntity.ok().body(UserAndTokenResponse.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .build());
    }

    @Override
    public ResponseEntity<?> signOut(HttpServletRequest request) {
        return ResponseEntity.ok().body(new SuccessMessageResponse(CustomUserCode.SUCCESS_SIGNOUT.getMessage()));
    }
}
