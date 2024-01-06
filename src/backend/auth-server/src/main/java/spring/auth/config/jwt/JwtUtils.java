package spring.auth.config.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.WebUtils;
import spring.auth.common.exception.request.ExpiredToken;
import spring.auth.domain.User;
import spring.auth.service.RedisService;

import java.security.Key;
import java.time.Duration;
import java.util.Date;
import java.util.UUID;

/**
 * JWT 파싱, 생성, 검증 제공
 */
@Slf4j
@Component
public class JwtUtils {


    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.access-expiration}") //1분
    private long jwtAccessExpiration;

    @Value("${jwt.refresh-expiration}") //2분
    private long jwtRefreshExpiration;

    @Value("${jwt.access-cookie-name}")
    private String jwtAccessCookie;

    @Value("${jwt.refresh-cookie-name}")
    private String jwtRefreshCookie;

    private final RedisService redisService;


    /**
     * secret key 객체로 변환
     */
    public JwtUtils(@Value("${jwt.secret}") String secretKey,
                    @Value("${jwt.access-expiration}") long jwtAccessExpiration,
                    @Value("${jwt.refresh-expiration}") long jwtRefreshExpiration,
                    RedisService redisService) {
        this.secretKey = secretKey;
        this.jwtAccessExpiration = jwtAccessExpiration;
        this.jwtRefreshExpiration = jwtRefreshExpiration;
        this.redisService = redisService;
    }
    public Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
    }


    /**
     * 클라이언트 http cookie 관리
     */
    public ResponseCookie generateAccessJwtCookie(User user) {
        String jwt = generateAccessTokenFromEmail(user.getEmail());
        return generateCookie(jwtAccessCookie, jwt, "/api");
    }

    public ResponseCookie generateRefreshJwtCookie(String refreshToken) {
        return generateCookie(jwtRefreshCookie, refreshToken, "/api");
    }

    public String getAccessJwtFromCookies(HttpServletRequest request) {
        return getCookieValueByName(request, jwtAccessCookie);
    }

    public String getRefreshJwtFromCookies(HttpServletRequest request) {
        return getCookieValueByName(request, jwtRefreshCookie);
    }

    private ResponseCookie generateCookie(String name, String value, String path) {
        return ResponseCookie.from(name, value).path(path).maxAge(24 * 60 * 60).httpOnly(true).build(); // 1일
    }

    private String getCookieValueByName(HttpServletRequest request, String name) {
        Cookie cookie = WebUtils.getCookie(request, name);
        if (cookie != null) {
            return cookie.getValue();
        }else{
            return null;
        }
    }

    public ResponseCookie getCleanAccessJwtCookie() {
        ResponseCookie cookie = ResponseCookie.from(jwtAccessCookie, null).path("/api").build();
        return cookie;
    }
    public ResponseCookie getCleanRefreshJwtCookie() {
        ResponseCookie cookie = ResponseCookie.from(jwtRefreshCookie, null).path("/api").build();
        return cookie;
    }

    /**
     * Token 관리
     */
    public String generateAccessTokenFromEmail(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtAccessExpiration))
                .signWith(key(), SignatureAlgorithm.HS512)
                .compact();
    }

    @Transactional
    public String generateRefreshToken(String email) {
        String refreshToken = UUID.randomUUID().toString();

//        long expiredTime = 30 * 1000; // 30초
        redisService.setRedisTemplate(refreshToken, email, Duration.ofMillis(jwtRefreshExpiration)); // TODO test 용
        return refreshToken;
    }


    public String getEmailFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }


    public boolean validateJwtToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parse(token);
            return true;
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token={}", e.getMessage());

        } catch (ExpiredJwtException e) {
//            log.error("Jwt token is expired={}", e.getMessage());
            throw ExpiredToken.EXCEPTION;
        } catch (UnsupportedJwtException e) {
            log.error("Jwt token is unsupported={}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("Jwt claims string is empty={}", e.getMessage());
        }
        return false;
    }
}
