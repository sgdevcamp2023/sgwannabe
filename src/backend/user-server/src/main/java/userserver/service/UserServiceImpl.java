package userserver.service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import userserver.domain.Role;
import userserver.domain.Status;
import userserver.domain.User;
import userserver.exception.CustomException;
import userserver.exception.CustomUserCode;
import userserver.payload.request.EmailAuthCodeRequest;
import userserver.payload.request.EmailVerifyRequest;
import userserver.payload.request.SignUpRequest;
import userserver.payload.response.SuccessMessageResponse;
import userserver.repository.UserRepository;

import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final RedisService redisService;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;


    public ResponseEntity<?> sendAuthCodeByEmail(EmailAuthCodeRequest request) {
        String email = request.email();
        // 이메일 중복 검사
        validateEmailDuplicate(email);

        String authCode = createAuthCode();

        // redis에 인증코드 ttl 3분 설정 -> key: email, value: authCode
        redisService.setRedisTemplate(email, authCode, Duration.ofMinutes(3));

        // 이메일 전송
        sendMail(email, authCode);

        return ResponseEntity.ok().body(new SuccessMessageResponse(CustomUserCode.SUCCESS_MAIL_SEND.getMessage()));

    }

    @Transactional
    public ResponseEntity<?> signUp(SignUpRequest request) {
        // TODO 프론트에서 이메일 인증을 한 경우에만 회원가입 버튼이 활성화가 되는 건 지 논의 필요
        // 이메일 중복 검사
        validateEmailDuplicate(request.email());

        String encodePassword = passwordEncoder.encode(request.password());

        User user = User.builder()
                .nickname(request.nickname())
                .email(request.email())
                .password(encodePassword) // bcrypt 암호화 적용
                .role(Role.USER)
                .status(Status.ACTIVE)
                .build();

        userRepository.save(user);

        return ResponseEntity.ok().body(new SuccessMessageResponse(CustomUserCode.SUCCESS_SIGNUP.getMessage()));

    }

    public void validateEmailDuplicate(String email) {
        userRepository.findByEmail(email)
                .ifPresent((user -> {
                    throw new CustomException(CustomUserCode.DUPLICATE_EMAIL);
                }));
    }

    /**
     * 6자리 난수 생성
     */
    public String createAuthCode() {
        return Integer.toString(ThreadLocalRandom.current().nextInt(100000, 1000000));
    }

    /**
     * 이메일로 인증번호 전송
     * TODO 비동기 처리
     */
    private void sendMail(String email, String code) {
        try{
            emailService.createMessageForm(email, "[라라라] 회원가입 인증 이메일", "인증 코드: "+ code);
        } catch (Exception e) {
            throw new CustomException(CustomUserCode.SEND_EMAIL_ERROR);
        }
    }

    public  ResponseEntity<?> verifyAuthCode(EmailVerifyRequest request) {
        String email = request.email();
        String code = request.code();
        String validateCode = redisService.getRedisTemplateValue(email);

        if (!Objects.equals(code, validateCode)) {
            throw new CustomException(CustomUserCode.NOT_VALID_CODE);
        }
        return ResponseEntity.ok().body(new SuccessMessageResponse(CustomUserCode.SUCCESS_CODE_CHECK.getMessage()));
    }


}