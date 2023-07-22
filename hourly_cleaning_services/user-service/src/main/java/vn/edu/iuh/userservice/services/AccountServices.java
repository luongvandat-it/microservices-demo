package vn.edu.iuh.userservice.services;


import vn.edu.iuh.userservice.models.User;

public interface AccountServices {
    User saveAccount(User user);

    User findUserById(long userId);

    User login(String phone, String password);
}