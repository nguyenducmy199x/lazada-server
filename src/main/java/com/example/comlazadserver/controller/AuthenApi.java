package com.example.comlazadserver.controller;

import com.example.comlazadserver.dto.AuthenRequest;
import com.example.comlazadserver.dto.AuthenResponse;
import com.example.comlazadserver.entity.User;
import com.example.comlazadserver.repository.UserRepository;
import com.example.comlazadserver.service.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/authen")
@CrossOrigin("http://localhost:4200")
@Slf4j
public class AuthenApi {
    @Autowired
    UserRepository userRepository;
    @Autowired
    JwtService jwtService;

    @PostMapping("/login")
    public AuthenResponse authenticate(@RequestBody AuthenRequest authenRequest){
        User user = userRepository.findByUsername(authenRequest.getUsername());
        String jwtToken = "";
        AuthenResponse authenResponse = new AuthenResponse();
        if(user != null && user.getPassword().equals(authenRequest.getPassword())){
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


}
