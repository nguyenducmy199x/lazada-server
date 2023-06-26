package com.example.comlazadserver.controller;

import com.example.comlazadserver.dto.AuthenRequest;
import com.example.comlazadserver.dto.AuthenResponse;
import com.example.comlazadserver.entity.User;
import com.example.comlazadserver.repository.UserRepository;
import com.example.comlazadserver.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/authen")
@CrossOrigin("http://localhost:4200")
public class AuthenApi {
    @Autowired
    UserRepository userRepository;
    @Autowired
    JwtService jwtService;

    @PostMapping("/user")
    public AuthenResponse authenticate(@RequestBody AuthenRequest authenRequest){
        User user = userRepository.findByUsername(authenRequest.getUsername());
        String jwtToken = "";
        AuthenResponse authenResponse = new AuthenResponse();
        if(user != null){
            jwtToken = jwtService.generateJwtToken(user.getUsername());
            authenResponse.setToken(jwtToken);
            authenResponse.setCode("200");
            authenResponse.setStatus("User Verified");
        }else{
            authenResponse.setCode("401");
            authenResponse.setStatus("Unauthorized");
        }

        return authenResponse;
    }


}
