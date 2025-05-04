package com.example.comlazadserver.service;

import com.example.comlazadserver.dto.AccountReq;
import com.example.comlazadserver.dto.AccountRes;
import com.example.comlazadserver.entity.User;
import com.example.comlazadserver.repository.UserRepository;
import com.example.comlazadserver.utils.PasswordValidator;
import lombok.SneakyThrows;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.List;

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
        validateRequest(accountReq);
        User user = User.builder()
                .email(accountReq.getEmail()).username(accountReq.getUsername())
                .password(passwordEncoder.encode(accountReq.getPassword())).build();
        userRepository.save(user);
        AccountRes accountRes = new AccountRes();
        accountRes.setUsername(accountReq.getUsername());
        accountRes.setEmail(accountReq.getEmail());
        return accountRes;
    }

    public void validateRequest(AccountReq accountReq) {
        User existUserName = userRepository.findByUsername(accountReq.getUsername());
        if(!ObjectUtils.isEmpty(existUserName)) {
            throw new IllegalArgumentException("Tài khoản với username "  +accountReq.getUsername() + " đã tồn tại !");
        }
        User existEmail = userRepository.findByEmail(accountReq.getEmail());
        if(!ObjectUtils.isEmpty(existEmail)) {
            throw new IllegalArgumentException("Tài khoản với email "  +accountReq.getEmail() + " đã tồn tại !");
        }
//        PasswordValidator.validate(accountReq.getPassword(), "password không hợp lệ");
    }
}
