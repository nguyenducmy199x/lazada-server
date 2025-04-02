package com.example.comlazadserver.controller;

import com.example.comlazadserver.dto.*;
import com.example.comlazadserver.entity.User;
import com.example.comlazadserver.repository.UserRepository;
import com.example.comlazadserver.service.AccountService;
import com.example.comlazadserver.service.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
    public AuthenResponse authenticate(@RequestBody AuthenRequest authenRequest){
        User user = userRepository.findByUsername(authenRequest.getUsername()).orElseThrow();
        String jwtToken = "";
        AuthenResponse authenResponse = new AuthenResponse();
        if(user != null && passwordEncoder.matches(authenRequest.getPassword(), user.getPassword())){
            jwtToken = jwtService.generateJwtToken(user.getUsername());
            authenResponse.setToken(jwtToken);
            authenResponse.setCode("200");
            authenResponse.setStatus("User Verified");
        }else{
            authenResponse.setCode("401");
            authenResponse.setStatus("Unauthorized");
        }
        log.info("User is verified - token {}", jwtToken);
        return authenResponse;
    }

    @PostMapping("/new-account")
    public BaseResponse<AccountRes> addNewAcc(@RequestBody AccountReq accountReq){
        return BaseResponse.success(accountService.createAccount(accountReq));
    }
}
