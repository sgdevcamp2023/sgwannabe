package spring.auth.config.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import spring.auth.config.security.UserDetailsServiceImpl;

import java.io.IOException;

import static spring.auth.common.AuthStatic.AUTH_HEADER;
import static spring.auth.common.AuthStatic.BEARER;

/**
 * 토큰 인증을 위한 필터
 */
@Slf4j

public class AuthTokenFilter extends OncePerRequestFilter { // OncePerRequestFilter -> 동일 필터 중복 호출 방지
    // TODO @NoArgs로 변경
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);

            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                String email = jwtUtils.getEmailFromToken(jwt);
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        } catch (Exception e) {
            log.error("authentication 설정 실패={}", e.getMessage());
        }

        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        String authHeader = request.getHeader(AUTH_HEADER);

        if (StringUtils.hasText(authHeader) && authHeader.startsWith(BEARER)) { //AccessToken 의 맨 앞 'Bearer ' 삭제
//            return authHeader.substring(7);
            return authHeader.split(" ")[1].trim();

        }
        return null;
    }
}
