package com.lalala.mvc.response;

import java.util.TreeMap;

import lombok.extern.slf4j.Slf4j;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.lalala.response.BaseResponse;

@ControllerAdvice
@Slf4j
public class BaseResponseBodyAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(
            MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(
            Object body,
            MethodParameter returnType,
            MediaType selectedContentType,
            Class<? extends HttpMessageConverter<?>> selectedConverterType,
            ServerHttpRequest request,
            ServerHttpResponse response) {
        // Swagger 예외 처리
        if (body.getClass() == TreeMap.class
                || selectedConverterType == ByteArrayHttpMessageConverter.class) {
            return body;
        }

        if (body instanceof BaseResponse<?>) {
            return body;
        }

        return BaseResponse.from(HttpStatus.OK.value(), "성공했습니다.", body);
    }
}
