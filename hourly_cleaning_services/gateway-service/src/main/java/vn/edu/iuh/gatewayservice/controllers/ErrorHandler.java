package vn.edu.iuh.gatewayservice.controllers;


import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Objects;

/**
 * Handle error not found api
 */
@Component
@Order(-2)
public class ErrorHandler extends AbstractErrorWebExceptionHandler {

    public ErrorHandler(ErrorAttributes errorAttributes, WebProperties.Resources resources, ApplicationContext applicationContext, ServerCodecConfigurer configurer) {
        super(errorAttributes, resources, applicationContext);
        this.setMessageWriters(configurer.getWriters());
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::handleError);
    }


    public Mono<ServerResponse> handleError(ServerRequest request) {
        ErrorAttributeOptions errorAttributeOptions = ErrorAttributeOptions.of(ErrorAttributeOptions.Include.values());
        Map<String, Object> errorAttributes = getErrorAttributes(request, errorAttributeOptions);

        // get status
        int statusCode = Integer.parseInt(Objects.toString(errorAttributes.get("status")));
        HttpStatus httpStatus = HttpStatus.valueOf(statusCode);

        // get message
        String message;
        message = (String) errorAttributes.get("message");
        if (null == message)
            message = "Unknown error";
        Map<String, String> responseBody = Map.of("message", message);
        return ServerResponse.status(httpStatus)
                .contentType(MediaType.APPLICATION_NDJSON)
                .body(BodyInserters.fromValue(responseBody));
    }


}