package userserver.config.jwt;

import javax.crypto.SecretKey;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
@RequiredArgsConstructor
@Getter
@Slf4j
public class JwtUtils {

    @Value("${jwt.cookie-max-age}")
    private long cookieMaxAge;

    @Value("${jwt.secret-key}")
    private String secretKey;

    private final String header = "Authorization";

    private final String prefix = "Bearer ";

    public SecretKey key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
    }

    public String getIdFromToken(String token) {
        return Jwts.parser()
                .verifyWith(key())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}
