package com.ltfullstack.apigateway.Filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

@Component
public class JwtHeaderFilter  extends AbstractGatewayFilterFactory<JwtHeaderFilter.Config> {

    public JwtHeaderFilter(){
        super(JwtHeaderFilter.Config.class);
    }

    @Override
    public GatewayFilter apply(JwtHeaderFilter.Config config) {
        return (exchange, chain) -> {
            return exchange.getPrincipal().flatMap(principal -> {
                if(principal instanceof JwtAuthenticationToken jwtAuth){
                    var claims = jwtAuth.getToken().getClaims();
                    var userId = claims.get("sub").toString();
                    var userName = claims.get("preferred_username").toString();
                    ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                            .header("X-User-Id",userId)
                            .header("X-Username",userName).build();

                    ServerWebExchange mutatedExchange = exchange.mutate().request(mutatedRequest).build();
                    return chain.filter(mutatedExchange);
                }
                return chain.filter(exchange);
            });
        };
    }

    static class Config{

    }
}
