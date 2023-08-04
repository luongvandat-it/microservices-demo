package vn.edu.iuh.authservice.controllers;

import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.iuh.authservice.entities.AuthRequest;
import vn.edu.iuh.authservice.entities.AuthResponse;
import vn.edu.iuh.authservice.services.AuthService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Log4j2
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    @Retry(name = "login", fallbackMethod = "fallback")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        AuthResponse authResponse = authService.login(authRequest);
        if (null == authResponse) {
            return new ResponseEntity<>("Login failed. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(authResponse);
    }

    public ResponseEntity<?> fallback(Exception e) {
        log.error(e.getMessage());
        return ResponseEntity.notFound().build();
    }
}
