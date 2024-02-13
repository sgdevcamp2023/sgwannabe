package com.lalala.gateway.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "AUTH-SERVICE")
public interface FeignAuthClient {

    @PostMapping("/passport")
    String validateAndProvidedPassport(@RequestHeader("Authorization") String jwtPayload);
}
