package authserver.config.jwt;

import authserver.domain.User;
import authserver.exception.CustomException;
import authserver.exception.CustomUserCode;
import authserver.service.RedisService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.WebUtils;

import java.security.Key;
import java.time.Duration;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtils {

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.access-expiration}") //30초
    private long jwtAccessExpiration;

    @Value("${jwt.refresh-expiration}") //2분
    private long jwtRefreshExpiration;

    @Value("${jwt.access-cookie-name}")
    private String jwtAccessCookie;

    @Value("${jwt.refresh-cookie-name}")
    private String jwtRefreshCookie;

    @Value("${jwt.cookie-max-age}")
    private long cookieMaxAge;

    private final RedisService redisService;

    private final String cookiePath = "/v1";

    /**
     * 문자열 secret key를 객체로 변환
     */
    public Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
    }


    /**
     * 클라이언트 http cookie 관리
     */
    public ResponseCookie generateAccessJwtCookie(User user) {
        String jwt = generateAccessTokenFromId(String.valueOf(user.getId()));
        return generateCookie(jwtAccessCookie, jwt, "/v1");
    }

    public ResponseCookie generateRefreshJwtCookie(String refreshToken) {
        return generateCookie(jwtRefreshCookie, refreshToken, "/v1/auth-service");
    }

    public String getAccessJwtFromCookies(HttpServletRequest request) {
        return getCookieValueByName(request, jwtAccessCookie);
    }

    public String getRefreshJwtFromCookies(HttpServletRequest request) {
        return getCookieValueByName(request, jwtRefreshCookie);
    }

    private ResponseCookie generateCookie(String name, String value, String path) {
        return ResponseCookie.from(name, value).path(path).maxAge(cookieMaxAge).httpOnly(true).build(); // 1일
    }


    public ResponseCookie getCleanAccessJwtCookie() {
        ResponseCookie cookie = ResponseCookie.from(jwtAccessCookie, null).path("/v1").build();
        return cookie;
    }
    public ResponseCookie getCleanRefreshJwtCookie() {
        ResponseCookie cookie = ResponseCookie.from(jwtRefreshCookie, null).path("/v1/auth-service").build();
        return cookie;
    }

    /**
     * Token 관리
     */
    public String generateAccessTokenFromId(String id) {
        return Jwts.builder()
                .setSubject(id)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtAccessExpiration))
                .signWith(key(), SignatureAlgorithm.HS512)
                .compact();
    }

    @Transactional
    public String generateRefreshToken(String id) {
        String refreshToken = UUID.randomUUID().toString();

        redisService.setRedisTemplate(id, refreshToken, Duration.ofMillis(jwtRefreshExpiration));
        return refreshToken;
    }

    @Transactional
    public void deleteRefreshToken(String id) {
        redisService.deleteRedisTemplateValue(id);
    }


    public String getIdFromToken(String token) {
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
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    public String getJwtFromCookies(HttpServletRequest request) {
        return getCookieValueByName(request, jwtAccessCookie);
    }

    private String getCookieValueByName(HttpServletRequest request, String name) {
        Cookie cookie = WebUtils.getCookie(request, name);
        if (cookie != null) {
            return cookie.getValue();
        }else{
            return null;
        }
    }


}
