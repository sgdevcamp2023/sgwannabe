package userserver.service;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMessage.RecipientType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import userserver.domain.Role;
import userserver.domain.Status;
import userserver.domain.User;
import userserver.payload.request.EmailAuthCodeRequest;
import userserver.payload.request.EmailVerifyRequest;
import userserver.payload.request.SignUpRequest;
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
    private final JavaMailSender mailSender;
    private final PasswordEncoder passwordEncoder;


    public void sendAuthCodeByEmail(EmailAuthCodeRequest request) {
        String email = request.email();
        validateEmailDuplicate(email);

        String authCode = createAuthCode();

        /*
          redis에 인증코드 ttl 3분 설정
          key: email, value: authCode
         */
        redisService.setRedisTemplate(email, authCode, Duration.ofMinutes(3));

        /*
          이메일 전송
         */
        sendMessage(email, authCode);

    }

    @Transactional
    public void signUp(SignUpRequest request) {

        String encodePassword = passwordEncoder.encode(request.password());

        User user = User.builder()
                .username(request.username())
                .email(request.email())
                .password(encodePassword) // SHA-256 암호화 적용 //TODO 몇 byte?
                .role(Role.ROLE_USER)
                .status(Status.ENABLE)
                .build();

        userRepository.save(user);

    }

    public void validateEmailDuplicate(String email) {
        userRepository.findByEmail(email)
                .ifPresent((user -> {
                    throw new RuntimeException();
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
     * TODO 하드코딩 수정
     */
    private void sendMessage(String email, String code) {
        try {
            MimeMessage message = createMessageForm(email, code);

            try {
                mailSender.send(message);
            } catch (MailException e) {
                log.error("Error sending email for email: {} and code: {}", email, code, e);
                throw new RuntimeException();
            }

        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    private MimeMessage createMessageForm(String email, String code) {
        try {
            final String ADMIN_ADDRESS = "sgwannabe2024@naver.com";

            MimeMessage message = mailSender.createMimeMessage();

            message.addRecipients(RecipientType.TO, email);
            message.setSubject("[라라라] 회원가입 인증 메일이 도착했습니다.");

            String text="";
            text+= "<div style='margin:100px;'>";
            text+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
            text+= "<h3 style='color:blue;'>회원가입 코드입니다.</h3>";
            text+= "<div style='font-size:130%'>";
            text+= "CODE : <strong>";
            text+= code +"</strong><div><br/> ";
            text+= "</div>";

            message.setText(text, "utf-8", "html");
            message.setFrom(new InternetAddress(ADMIN_ADDRESS, "LALALA"));
            return message;

        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public void verifyAuthCode(EmailVerifyRequest request) {
        String email = request.email();
        String code = request.code();
        String validateCode = redisService.getRedisTemplateValue(email);

        if (!Objects.equals(code, validateCode)) {
            throw new RuntimeException();
        }
    }





}
