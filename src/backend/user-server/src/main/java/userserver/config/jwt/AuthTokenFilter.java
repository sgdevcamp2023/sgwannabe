package userserver.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import userserver.config.security.UserDetailsImpl;
import userserver.config.security.UserDetailsServiceImpl;
import userserver.service.UserService;

import java.io.IOException;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@RequiredArgsConstructor
public class AuthTokenFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final UserService userService;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        String header = request.getHeader(jwtUtils.getHeader());

        if (header == null || !header.startsWith(jwtUtils.getPrefix())) {
            chain.doFilter(request, response);
            return;
        }

        String token = header.replace(jwtUtils.getPrefix(), "");

        if (jwtUtils.validateToken(token)) {
            Claims claims = jwtUtils.getClaimsFromToken(token);
            String id = claims.getSubject();

            UserDetails userDetails = userDetailsService.loadUserByUsername(id);

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);

        }else{
            SecurityContextHolder.clearContext();
        }

        chain.doFilter(request, response);
    }
}
