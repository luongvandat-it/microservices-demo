package vn.edu.iuh.gatewayservice.config;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
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
            final  String token = getAuthHeader(request);
            if(jwtUtil.isInvalid(token)) {
                return this.onError(exchange, "Authorization header is invalid", HttpStatus.UNAUTHORIZED);
            }
            this.populateRequestWithHeaders(exchange, token);
        }
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
//        byte[] bytes = err.getBytes(StandardCharsets.UTF_8);
//        DataBuffer buffer = response.bufferFactory().wrap(bytes);
//        response.getHeaders().setContentType(MediaType.TEXT_PLAIN);
//        Publisher<DataBuffer> just = Mono.just(buffer);
//        response.writeWith(Mono.just("loi"));
        // TODO add response body
        Map<String, String> responseBody = Map.of("message", "Unknown error");
        System.err.println(err);
        response.setStatusCode(httpStatus);
//        response.writeWith(BodyInserters.fromValue(responseBody));
        return response.setComplete();
//        return ServerResponse.status(httpStatus)
//                .contentType(MediaType.APPLICATION_NDJSON)
//                .body(BodyInserters.fromValue(responseBody));
//        return response.
    }

    private String getAuthHeader(ServerHttpRequest request) {
        return request.getHeaders().getOrEmpty("Authorization").get(0);
    }

    private void populateRequestWithHeaders(ServerWebExchange exchange, String token) {
        Claims claims = jwtUtil.getAllClaimsFromToken(token);
        exchange.getRequest().mutate()
                .header("id", String.valueOf(claims.get("id")))
                .header("role", String.valueOf(claims.get("role")))
                .build();
    }

}
