package vn.edu.iuh.userservice.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.iuh.userservice.models.User;
import vn.edu.iuh.userservice.payloads.RequestLoginDto;
import vn.edu.iuh.userservice.payloads.ResponseLoginDto;
import vn.edu.iuh.userservice.services.AccountServices;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    private final AccountServices accountServices;

    public AccountController(AccountServices accountServices) {
        this.accountServices = accountServices;
    }

    @PostMapping("/register")
    public ResponseEntity<?> saveAccount(@RequestBody User user) {
        if (accountServices.findByPhone(user.getPhone()) != null) {
            return new ResponseEntity<>("the phone number you've entered already exists with another account", HttpStatus.BAD_REQUEST);
        }
        try {
            User _user = accountServices.saveAccount(user);
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("There was an error with your registration. Please try registering again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
    public ResponseEntity<ResponseLoginDto> login(@RequestBody RequestLoginDto loginDto) {
        try {
            ResponseLoginDto responseLoginDto = accountServices.login(loginDto.getPhone(), loginDto.getPassword());
            if (responseLoginDto != null) {
                return ResponseEntity.ok(responseLoginDto);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}