package vn.edu.iuh.userservice.services;


import vn.edu.iuh.userservice.models.User;
import vn.edu.iuh.userservice.payloads.ResponseLoginDto;

public interface AccountServices {
    User saveAccount(User user);

    User findUserById(long userId);

    ResponseLoginDto login(String phone, String password);
}