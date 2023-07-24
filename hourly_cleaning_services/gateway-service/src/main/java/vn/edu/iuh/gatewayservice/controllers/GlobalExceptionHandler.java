package vn.edu.iuh.gatewayservice.controllers;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//@RestControllerAdvice
@Log4j2
public class GlobalExceptionHandler {

//    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleUnwantedException(Exception e) {
        log.error(e.getMessage());
        System.out.println("loi r");
        return ResponseEntity.status(500).body("Unknown error");
    }
}
