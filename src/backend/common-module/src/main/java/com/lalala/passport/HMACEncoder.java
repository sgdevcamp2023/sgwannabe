package com.lalala.passport;

import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class HMACEncoder {

    private final String hMacAlgorithm;
    private final String passportSecretKey;

    protected String createHMACIntegrityKey(String userInfoString) {
        SecretKeySpec secretKeySpec = new SecretKeySpec(passportSecretKey.getBytes(), hMacAlgorithm);
        Mac mac;
        try {
            mac = Mac.getInstance(hMacAlgorithm);
            mac.init(secretKeySpec);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        return Base64.getEncoder().encodeToString(mac.doFinal(userInfoString.getBytes()));
    }
}
