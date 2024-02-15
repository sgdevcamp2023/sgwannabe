package com.lalala.gateway.external.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.lalala.response.BaseResponse;

@FeignClient(name = "AUTH-SERVER")
public interface FeignAuthClient {

    @PostMapping("/v1/api/passport")
    BaseResponse<String> generatePassport(
            @RequestHeader("Authorization") String jwtPayload
    );
}
