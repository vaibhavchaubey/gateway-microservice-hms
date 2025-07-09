package com.hms.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class TokenFilter extends AbstractGatewayFilterFactory<TokenFilter.Config> {

    private static final String SECRET_KEY = "vdDKQarH6iPm6xRJDbIgrIZJkCc1/4QYZK8Mmn6PthI012286h0p4wCxd4IPgq1qfL6nU044j/GjrM5eW07clg==";

    public TokenFilter() {
        super(Config.class);
    }

    public static class Config {

    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {

            String path = exchange.getRequest().getPath().toString();
            if (path.equals(("/user/login")) || path.equals(("/user/register"))) {
                return chain.filter(exchange.mutate().request(r -> r.header("X-Secret-Key", "SECRET")).build());
            }
            HttpHeaders headers = exchange.getRequest().getHeaders();

            if (!headers.containsKey(HttpHeaders.AUTHORIZATION)) {
                throw new RuntimeException("Authorization header is missing");
            }

            String authHeader = headers.getFirst(HttpHeaders.AUTHORIZATION);

            if (authHeader == null || !authHeader.startsWith("Bearer")) {
                throw new RuntimeException("Invalid Authorization header format");
            }

            // Extract the token after "Bearer
            String token = authHeader.substring(7);

            try {
                Claims claims = Jwts.parser()
                        .setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();

            } catch (Exception e) {
                throw new RuntimeException("Token is invalid");
            }

            exchange = exchange.mutate().request(r -> r.header("X-Secret-Key", "SECRET")).build();

            return chain.filter(exchange);
        };
    }

}
