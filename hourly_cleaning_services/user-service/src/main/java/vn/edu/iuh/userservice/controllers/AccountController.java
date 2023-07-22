package vn.edu.iuh.userservice.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.iuh.userservice.models.User;
import vn.edu.iuh.userservice.services.AccountServices;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    private final AccountServices accountServices;

    public AccountController(AccountServices accountServices) {
        this.accountServices = accountServices;
    }

    @PostMapping("/register")
    public User saveAccount(@RequestBody User user) {
        User _user = accountServices.saveAccount(user);
        System.out.println(_user);
        return _user;
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