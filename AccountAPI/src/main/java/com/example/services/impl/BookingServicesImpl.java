package com.example.services.impl;

import com.example.models.User;
import com.example.repositories.AccountRepository;
import com.example.services.AccountServices;
import org.springframework.stereotype.Service;

@Service
public class BookingServicesImpl implements AccountServices {
    private AccountRepository accountRepository;

    public BookingServicesImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public User saveAccount(User user) {
        return accountRepository.save(user);
    }

    @Override
    public User findUserById(long userId) {
        return accountRepository.findById(userId).orElse(null);
    }
}