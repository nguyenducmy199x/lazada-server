package com.example.comlazadserver.controller;

import com.example.comlazadserver.dto.*;
import com.example.comlazadserver.entity.User;
import com.example.comlazadserver.repository.UserRepository;
import com.example.comlazadserver.service.AccountService;
import com.example.comlazadserver.service.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/authen")
@Slf4j
public class AuthenApi {
    @Autowired
    UserRepository userRepository;
    @Autowired
    JwtService jwtService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AccountService accountService;

    @PostMapping("/login")
    public BaseResponse<String> authenticate(@RequestBody AuthenRequest authenRequest){
        User user = userRepository.findByUsername(authenRequest.getUsername()).orElseThrow(()->new UsernameNotFoundException("User not found"));
        if(user == null || !passwordEncoder.matches(authenRequest.getPassword(), user.getPassword())){
            return BaseResponse.failCode(null, 401, "Unauthorized");   
        }
        return BaseResponse.success(jwtService.generateJwtToken(user.getUsername()));
    }

    @PostMapping("/new-account")
    public BaseResponse<AccountRes> addNewAcc(@RequestBody AccountReq accountReq){
        return BaseResponse.success(accountService.createAccount(accountReq));
    }
}
