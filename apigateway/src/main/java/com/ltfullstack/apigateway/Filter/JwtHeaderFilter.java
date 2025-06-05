package com.ltfullstack.apigateway.Filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtHeaderFilter extends AbstractGatewayFilterFactory<JwtHeaderFilter.Config> {

    public JwtHeaderFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            return exchange.getPrincipal().flatMap(principal -> {
                if (principal instanceof JwtAuthenticationToken jwtAuth) {
                    var claims = jwtAuth.getToken().getClaims();
                    var userId = claims.get("sub") != null ? claims.get("sub").toString() : "";
                    var username = claims.get("preferred_username") != null ? claims.get("preferred_username").toString() : "";


                    System.out.println("Setting headers X-User-Id: "+userId +" X-Username:" + username);

                    ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                            .header("X-User-Id", userId)
                            .header("X-Username", username)
                            .build();

                    ServerWebExchange mutatedExchange = exchange.mutate().request(mutatedRequest).build();
                    return chain.filter(mutatedExchange);
                }
                return chain.filter(exchange);
            });
        };
    }

    public static class Config {
        // put configuration properties here if needed
    }
}
