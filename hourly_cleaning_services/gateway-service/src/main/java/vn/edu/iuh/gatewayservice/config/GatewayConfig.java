package vn.edu.iuh.gatewayservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableHystrix
@RequiredArgsConstructor
public class GatewayConfig {
    private final AuthenticationFilter filter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth-service", r -> r.path("/auth/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://auth-service"))
                .route("booking-service", r -> r.path("/api/booking/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://booking-service"))
                .route("employee-service", r -> r.path("/api/employee/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://employee-service/"))
                .route("user-service", r -> r.path("/api/account/register")
                        .filters(f -> f.filter(filter))
                        .uri("lb://user-service"))
                .build();
    }

    @Bean
    public WebProperties.Resources resources() {
        return new WebProperties.Resources();
    }

}
