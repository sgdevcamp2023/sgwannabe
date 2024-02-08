package userserver.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import userserver.config.jwt.AuthEntryPoint;
import userserver.config.jwt.AuthTokenFilter;
import userserver.config.jwt.JwtAccessDeniedHandler;
import userserver.config.jwt.JwtUtils;


@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;
    private final JwtUtils jwtUtils;
    private final AuthEntryPoint authEntryPointHandler;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    private static final String[] USER_WHITELIST={
            "/v1/api/email","/v1/api/verification","/v1/api/signup", "/v1/api/test/all", "/v1/api/test/user", "/h2-console/**"
    };
    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter(jwtUtils, userDetailsService);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exception -> exception.authenticationEntryPoint(authEntryPointHandler))
                .exceptionHandling(exception -> exception.accessDeniedHandler(jwtAccessDeniedHandler))
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers(USER_WHITELIST).permitAll()
                                .anyRequest().authenticated());

        http.headers(h -> h.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)); // h2-console 사용

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }
}
