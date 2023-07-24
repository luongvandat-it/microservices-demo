package vn.edu.iuh.userservice.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import vn.edu.iuh.userservice.models.User;
import vn.edu.iuh.userservice.payloads.ResponseLoginDto;
import vn.edu.iuh.userservice.repositories.AccountRepository;
import vn.edu.iuh.userservice.services.AccountServices;

@Service
@RequiredArgsConstructor
public class AccountServicesImpl implements AccountServices {
    private final AccountRepository accountRepository;


    @Override
    public User saveAccount(User user) {
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        return accountRepository.save(user);
    }

    @Override
    public User findUserById(long userId) {
        return accountRepository.findById(userId).orElse(null);
    }

    @Override
    public ResponseLoginDto login(String phone, String password) {
        User user = accountRepository.findUserByPhone(phone);
        if (null != user && BCrypt.checkpw(password, user.getPassword())) {
            ResponseLoginDto responseLoginDto = new ResponseLoginDto();
            responseLoginDto.setId(user.getId());
            responseLoginDto.setFirstName(user.getFirstName());
            responseLoginDto.setLastName(user.getLastName());
            responseLoginDto.setPhone(user.getPhone());
            responseLoginDto.setGender(user.isGender());
            return responseLoginDto;
        }
        return null;
    }
}