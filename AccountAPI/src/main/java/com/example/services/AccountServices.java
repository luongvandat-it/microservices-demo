package com.example.services;

import com.example.models.User;

public interface AccountServices {
    User saveAccount(User user);

    User findUserById(long userId);
}