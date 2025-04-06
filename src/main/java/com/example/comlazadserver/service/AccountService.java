package com.example.comlazadserver.service;

import com.example.comlazadserver.dto.AccountReq;
import com.example.comlazadserver.dto.AccountRes;
import com.example.comlazadserver.entity.User;
import com.example.comlazadserver.repository.UserRepository;
import lombok.SneakyThrows;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AccountService {
    public AccountService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @SneakyThrows
    public AccountRes createAccount(AccountReq accountReq) {
        Optional<User> exist = userRepository.findByUsername(accountReq.getUsername());
        if(exist.get() != null) {
            throw new Exception();
        }
        User user = User.builder()
                .email(accountReq.getEmail()).username(accountReq.getUsername())
                .password(passwordEncoder.encode(accountReq.getPassword())).build();
        userRepository.save(user);
        AccountRes accountRes = new AccountRes();
        accountRes.setUsername(accountReq.getUsername());
        accountRes.setEmail(accountReq.getEmail());
        return accountRes;
    }
}
