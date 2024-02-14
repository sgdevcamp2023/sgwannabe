package com.lalala.gateway.external.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.lalala.response.BaseResponse;

@FeignClient(name = "AUTH-SERVICE")
public interface FeignAuthClient {

    @PostMapping("/passport")
    BaseResponse<String> validateAndProvidedPassport(
            @RequestHeader("Authorization") String jwtPayload);
}
