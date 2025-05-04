package com.example.comlazadserver.service;

import com.example.comlazadserver.dto.AccountReq;
import com.example.comlazadserver.dto.AccountRes;
import com.example.comlazadserver.entity.User;
import com.example.comlazadserver.repository.UserRepository;
import lombok.SneakyThrows;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
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
        List<User> exist = userRepository.findByUsernameOrEmail(accountReq.getUsername(), accountReq.getEmail());
        if(ObjectUtils.allNotNull(exist)) {
            throw new IllegalArgumentException("Tài khoản với username "  +accountReq.getUsername() + " ,email " + accountReq.getEmail() + " đã tồn tại !");
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
