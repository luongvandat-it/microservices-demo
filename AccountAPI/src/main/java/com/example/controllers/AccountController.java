package com.example.controllers;

import com.example.models.User;
import com.example.services.AccountServices;
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
    public User findUserById(long userId) {
        return accountServices.findUserById(userId);
    }

    @PostMapping("/login")
    public User login(String phone, String password) {
        return accountServices.login(phone, password);
    }
}