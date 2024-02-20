package com.lalala.gateway.filter;

import java.util.Arrays;
import java.util.List;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.lalala.gateway.external.feign.FeignAuthClient;

@Component
public class AuthorizationFilter extends AbstractGatewayFilterFactory<AuthorizationFilter.Config> {

    private static final String AUTHORIZATION_HEADER_NAME = "Authorization";

    private final FeignAuthClient authClient;

    public AuthorizationFilter(FeignAuthClient authClient) {
        super(Config.class);
        this.authClient = authClient;
    }

    public static class Config {}

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String uri = exchange.getRequest().getURI().getPath();

            if (isTokenPassRouting(uri)) {
                return chain.filter(exchange);
            }

            if (isWhiteList(uri)) {
                return chain.filter(exchange);
            }

            if (isRequestContainsAuthorization(exchange)) {
                String jwtToken = extractJWT(exchange);

                return authClient
                        .generatePassport(jwtToken)
                        .flatMap(
                                response -> {
                                    String passport = response.getData();

                                    ServerHttpRequest request =
                                            exchange
                                                    .getRequest()
                                                    .mutate()
                                                    .header(AUTHORIZATION_HEADER_NAME, passport)
                                                    .build();

                                    return chain.filter(exchange.mutate().request(request).build());
                                })
                        .switchIfEmpty(chain.filter(exchange));
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
        List<String> authorization = exchange.getRequest().getHeaders().get(AUTHORIZATION_HEADER_NAME);
        return authorization != null && !authorization.isEmpty();
    }

    private boolean isWhiteList(String target) {
        return Arrays.stream(WhiteListURI.values())
                .anyMatch(whiteListURI -> whiteListURI.uri.equals(target));
    }

    private boolean isTokenPassRouting(String target) {
        return Arrays.stream(JWTTokenPassURI.values())
                .anyMatch(tokenPassURI -> tokenPassURI.uri.startsWith(target));
    }
}
