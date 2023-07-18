package com.example.controllers;

import com.example.models.User;
import com.example.services.AccountServices;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    private AccountServices accountServices;

    public AccountController(AccountServices accountServices) {
        this.accountServices = accountServices;
    }

    @PostMapping("/save")
    public void saveAccount() {
        accountServices.saveAccount(null);
    }

    @PostMapping("/findUserById")
    public User findUserById() {
        return accountServices.findUserById(0);
    }
}