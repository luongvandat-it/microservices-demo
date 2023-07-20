package com.example.controllers;

import com.example.models.User;
import com.example.services.AccountServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    private final AccountServices accountServices;

    public AccountController(AccountServices accountServices) {
        this.accountServices = accountServices;
    }

    @PostMapping("/register")
    public void saveAccount(User user) {
        accountServices.saveAccount(user);
    }

    @PostMapping("/findUserById")
    public ResponseEntity<User> findUserById(long userId) {
        User user = accountServices.findUserById(userId);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(String phone, String password) {
        User user = accountServices.login(phone, password);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }
}