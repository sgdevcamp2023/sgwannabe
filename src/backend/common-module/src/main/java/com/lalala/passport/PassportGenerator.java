package com.lalala.passport;

import java.util.Base64;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lalala.exception.BusinessException;
import com.lalala.exception.ErrorCode;
import com.lalala.passport.component.Passport;
import com.lalala.passport.component.UserInfo;

@Component
@RequiredArgsConstructor
public class PassportGenerator {

    private final ObjectMapper objectMapper;
    private final HMACEncoder hmacEncoder;

    public String generatePassport(UserInfo userInfo) {
        String encodedPassportString;

        try {
            String userInfoString = objectMapper.writeValueAsString(userInfo);
            String integrityKey = hmacEncoder.createHMACIntegrityKey(userInfoString);

            Passport passport = new Passport(userInfo, integrityKey);
            String passportString = objectMapper.writeValueAsString(passport);
            encodedPassportString = Base64.getEncoder().encodeToString(passportString.getBytes());
        } catch (JsonProcessingException e) {
            throw new BusinessException(ErrorCode.INVALID_PASSPORT);
        }

        return encodedPassportString;
    }
}
