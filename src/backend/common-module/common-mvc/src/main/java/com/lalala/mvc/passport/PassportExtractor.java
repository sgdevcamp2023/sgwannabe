package com.lalala.mvc.passport;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lalala.exception.BusinessException;
import com.lalala.exception.ErrorCode;
import com.lalala.passport.PassportValidator;
import com.lalala.passport.component.Passport;
import com.lalala.passport.component.UserInfo;
import jakarta.servlet.http.HttpServletRequest;

@Component
@RequiredArgsConstructor
public class PassportExtractor {

    private static final String USER_INFO = "userInfo";
    private static final String AUTHORIZATION_HEADER_NAME = "Authorization";
    private static final String BEARER = "Bearer";
    private final ObjectMapper objectMapper;
    private final PassportValidator passportValidator;

    public Passport getPassportFromRequestHeader(HttpServletRequest httpServletRequest) {
        try {
            String authorization = httpServletRequest.getHeader(AUTHORIZATION_HEADER_NAME);
            if (authorization == null) {
                throw new BusinessException(ErrorCode.INVALID_PASSPORT);
            }

            if (authorization.startsWith(BEARER + " ")) {
                authorization = authorization.split(" ")[1];
            }

            return objectMapper.readValue(
                    new String(Base64.getDecoder().decode(authorization), StandardCharsets.UTF_8),
                    Passport.class);
        } catch (JsonProcessingException e) {
            throw new BusinessException(ErrorCode.INVALID_PASSPORT);
        }
    }

    public UserInfo getUserInfoByPassport(Passport passport) {
        try {
            String passportString = new String(Base64.getDecoder().decode(passport.toString()));

            passportValidator.validatePassport(passportString);

            String userInfoString = objectMapper.readTree(passportString).get(USER_INFO).toString();
            return objectMapper.readValue(userInfoString, UserInfo.class);
        } catch (JsonProcessingException e) {
            throw new BusinessException(ErrorCode.INVALID_PASSPORT);
        }
    }
}
