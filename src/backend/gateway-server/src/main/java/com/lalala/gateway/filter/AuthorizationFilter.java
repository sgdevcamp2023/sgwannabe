package com.lalala.gateway.filter;

import java.util.Arrays;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.lalala.gateway.external.feign.FeignAuthClient;
import com.lalala.response.BaseResponse;

@Component
public class AuthorizationFilter extends AbstractGatewayFilterFactory<AuthorizationFilter.Config> {

    private static final String AUTHORIZATION_HEADER_NAME = "Authorization";
    private final FeignAuthClient feignAuthClient;

    public AuthorizationFilter(@Lazy FeignAuthClient feignAuthClient) {
        super(Config.class);
        this.feignAuthClient = feignAuthClient;
    }

    public static class Config {}

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String uri = exchange.getRequest().getURI().getPath();

            if (isWhiteList(uri)) {
                return chain.filter(exchange);
            }

            if (isRequestContainsAuthorization(exchange)) {
                System.out.println(extractJWT(exchange));
                BaseResponse<String> response = feignAuthClient.generatePassport(extractJWT(exchange));
                String passport = response.getData();

                ServerHttpRequest request =
                        exchange.getRequest().mutate().header(AUTHORIZATION_HEADER_NAME, passport).build();

                return chain.filter(exchange.mutate().request(request).build());
            }
            return chain.filter(exchange);
        };
    }

    private String extractJWT(ServerWebExchange exchange) {
        String authorization = exchange.getRequest().getHeaders().get(AUTHORIZATION_HEADER_NAME).get(0);

        if (!authorization.startsWith("Bearer ")) {
            return authorization;
        }

        int spaceIndex = authorization.indexOf(" ");
        return authorization.substring(spaceIndex + 1);
    }

    private boolean isRequestContainsAuthorization(ServerWebExchange exchange) {
        return exchange.getRequest().getHeaders().get(AUTHORIZATION_HEADER_NAME).size() == 1;
    }

    private boolean isWhiteList(String target) {
        return Arrays.stream(WhiteListURI.values())
                .anyMatch(whiteListURI -> whiteListURI.uri.equals(target));
    }
}
