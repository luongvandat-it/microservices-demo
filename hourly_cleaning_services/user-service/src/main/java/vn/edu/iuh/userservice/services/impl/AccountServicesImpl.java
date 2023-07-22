package vn.edu.iuh.userservice.services.impl;

import org.springframework.stereotype.Service;
import vn.edu.iuh.userservice.models.User;
import vn.edu.iuh.userservice.repositories.AccountRepository;
import vn.edu.iuh.userservice.services.AccountServices;

@Service
public class AccountServicesImpl implements AccountServices {
    private final AccountRepository accountRepository;

    public AccountServicesImpl(AccountRepository accountRepository) {
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

    @Override
    public User login(String phone, String password) {
        User user = accountRepository.findUserByPhone(phone);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}