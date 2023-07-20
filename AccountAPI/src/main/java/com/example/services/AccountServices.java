package com.example.services;

import com.example.models.User;

public interface AccountServices {
    void saveAccount(User user);

    User findUserById(long userId);

    User login(String phone, String password);
}