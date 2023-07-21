package vn.edu.iuh.gatewayservice.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
@Configuration
@EnableHystrix
public class GatewayConfig {

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes().route("employee-service", r -> r.path("/api/employee/**")
                .uri("lb://employee-service")).build();
    }
}
