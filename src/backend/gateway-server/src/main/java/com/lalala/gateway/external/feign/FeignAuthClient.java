package com.lalala.gateway.external.feign;

import com.lalala.response.BaseResponse;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import reactor.core.publisher.Mono;

@Headers("Authorization: {token}")
public interface FeignAuthClient {

    @RequestLine("POST /v1/api/passport")
    Mono<BaseResponse<String>> generatePassport(@Param("token") String token);
}
