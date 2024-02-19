package com.lalala.reactive.response;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.web.reactive.HandlerResult;
import org.springframework.web.reactive.accept.RequestedContentTypeResolver;
import org.springframework.web.reactive.result.method.annotation.ResponseBodyResultHandler;
import org.springframework.web.server.ServerWebExchange;

import com.lalala.response.BaseResponse;
import reactor.core.publisher.Mono;

@Slf4j
public class BaseResponseBodyAdvice extends ResponseBodyResultHandler {
    public BaseResponseBodyAdvice(
            List<HttpMessageWriter<?>> writers, RequestedContentTypeResolver resolver) {
        super(writers, resolver);
    }

    @Override
    public boolean supports(HandlerResult result) {
        return true;
    }

    @Override
    public Mono<Void> handleResult(ServerWebExchange exchange, HandlerResult result) {
        Object body = result.getReturnValue();

        if (body instanceof BaseResponse<?>) {
            return writeBody(body, result.getReturnTypeSource().nested(), exchange);
        }

        BaseResponse<Object> baseResponse = BaseResponse.from(200, "성공했습니다.", body);
        return writeBody(baseResponse, result.getReturnTypeSource().nested(), exchange);
    }
}
