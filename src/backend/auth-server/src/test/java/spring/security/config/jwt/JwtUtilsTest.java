package spring.security.config.jwt;

import io.jsonwebtoken.security.Keys;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.SecretKey;
import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JwtUtilsTest {

    @Value("${jwt.secret}")
    private String secretKeyPlain;

    @Test
    void 시크릿키_존재_확인() {
        assertThat(secretKeyPlain).isNotNull();
    }

    @Test
    @DisplayName("hmac 암호화 알고리즘을 통해 SecretKey 원문 -> SecretKey 객체 생성")
    void hmacShaKeyFor() {
        // 키를 Base64 인코딩
        String keyBase64Encoded = Base64.getEncoder().encodeToString(secretKeyPlain.getBytes());
        // Base64 인코딩된 키를 이용하여 SecretKey 객체를 만든다.
        SecretKey secretKey = Keys.hmacShaKeyFor(keyBase64Encoded.getBytes());

        assertThat(secretKey).isNotNull();
    }
}