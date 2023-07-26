package vn.edu.iuh.userservice.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import vn.edu.iuh.userservice.models.Role;
import vn.edu.iuh.userservice.models.User;
import vn.edu.iuh.userservice.payloads.ResponseLoginDto;
import vn.edu.iuh.userservice.repositories.AccountRepository;
import vn.edu.iuh.userservice.services.AccountServices;

import java.util.ArrayList;
import java.util.List;

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
        System.out.println(user.getRoles().toString());
        if (null != user && BCrypt.checkpw(password, user.getPassword())) {
            List<String> roles = new ArrayList<>();
            user.getRoles().forEach(role -> roles.add(role.getName()));

            ResponseLoginDto responseLoginDto = new ResponseLoginDto();
            responseLoginDto.setId(user.getId());
            responseLoginDto.setFirstName(user.getFirstName());
            responseLoginDto.setLastName(user.getLastName());
            responseLoginDto.setPhone(user.getPhone());
            responseLoginDto.setGender(user.isGender());
            responseLoginDto.setRoles(roles.toString());
            return responseLoginDto;
        }
        return null;
    }
}