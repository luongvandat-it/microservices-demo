package com.example.services.impl;

import com.example.models.User;
import com.example.repositories.AccountRepository;
import com.example.services.AccountServices;
import org.springframework.stereotype.Service;

@Service
public class AccountServicesImpl implements AccountServices {
    private final AccountRepository accountRepository;

    public AccountServicesImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void saveAccount(User user) {
        accountRepository.save(user);
    }

    @Override
    public User findUserById(long userId) {
        return accountRepository.findById(userId).orElse(null);
    }

    @Override
    public User login(String phone, String password) {
        User user = accountRepository.findUserByPhone(phone);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}