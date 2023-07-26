package vn.edu.iuh.gatewayservice.config;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import vn.edu.iuh.gatewayservice.utils.JwtUtil;

import java.util.Map;

@RefreshScope
@Component
@RequiredArgsConstructor
@Log4j2
public class AuthenticationFilter  implements GatewayFilter {
    private final RouterValidator routerValidator;
    private final JwtUtil jwtUtil;
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        if (routerValidator.isSecured.test(request)) {
            if (this.isAuthMissing(request))
                return this.onError(exchange, "Authorization header is missing in request", HttpStatus.UNAUTHORIZED);
            String token = getAuthHeader(request);
            token = token.replace("Bearer ", "");
            log.info(token);
            if(jwtUtil.isInvalid(token)) {
                return this.onError(exchange, "Authorization header is invalid", HttpStatus.UNAUTHORIZED);
            }
            this.populateRequestWithHeaders(exchange, token);
        }
        log.info("toi day r");
        return chain.filter(exchange);
    }

    /**
     * Check request Headers contains JSon Web Token (JWT)
     * @param request request
     * @return false if contains (and vice versa)
     */
    private boolean isAuthMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey("Authorization");
    }

    /**
     * send response error to client
     *
     * @param exchange   ServerWebExchange
     * @param err        error message
     * @param httpStatus HTTP Status
     * @return
     */
    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        log.error(err);
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    /**
     * Get string authorization from header
     * @param request
     * @return String authorization. Ex: Bearer header.payload.signature
     */
    private String getAuthHeader(ServerHttpRequest request) {
        return request.getHeaders().getOrEmpty("Authorization").get(0);
    }

    /**
     * Set the userId and roles into request headers
     * @param exchange ServerWebExchange
     * @param token JSon Web Token (JWT)
     */
    private void populateRequestWithHeaders(ServerWebExchange exchange, String token) {
        Claims claims = jwtUtil.getAllClaimsFromToken(token);
        exchange.getRequest().mutate()
                .header("userId", String.valueOf(claims.get("id")))
                .header("roles", String.valueOf(claims.get("roles")))
                .build();
    }

}
