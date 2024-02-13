package com.lalala.passport;

import java.util.Base64;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lalala.exception.BusinessException;
import com.lalala.exception.ErrorCode;

@Component
@RequiredArgsConstructor
public class PassportValidator {

    private static final String USER_INFO = "userInfo";
    private static final String INTEGRITY_KEY = "integrityKey";
    private final ObjectMapper objectMapper;
    private final HMACEncoder hmacEncoder;

    public void validatePassport(String requestedPassport) {
        String encodedUserInfo;
        String integrityKey;

        try {
            String passportJSONStr = new String(Base64.getDecoder().decode(requestedPassport));
            String userInfoString = objectMapper.readTree(passportJSONStr).get(USER_INFO).toString();
            encodedUserInfo = hmacEncoder.createHMACIntegrityKey(userInfoString);
            integrityKey = objectMapper.readTree(passportJSONStr).get(INTEGRITY_KEY).asText();

            isEqualByRequestedPassport(integrityKey, encodedUserInfo);
        } catch (BusinessException e) {
            throw new BusinessException(ErrorCode.INVALID_PASSPORT_INTEGRITY);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.INVALID_PASSPORT);
        }
    }

    private void isEqualByRequestedPassport(String integrityKey, String encodedUserInfo) {
        if (!encodedUserInfo.equals(integrityKey)) {
            throw new BusinessException(ErrorCode.INVALID_PASSPORT_INTEGRITY);
        }
    }
}
