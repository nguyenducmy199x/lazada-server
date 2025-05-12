package com.example.comlazadserver.controller;

import com.example.comlazadserver.dto.AccountReq;
import com.example.comlazadserver.dto.AccountRes;
import com.example.comlazadserver.dto.AuthenRequest;
import com.example.comlazadserver.dto.BaseResponse;
import com.example.comlazadserver.entity.User;
import com.example.comlazadserver.repository.UserRepository;
import com.example.comlazadserver.service.AccountService;
import com.example.comlazadserver.service.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/authen")
@Tag(name = "Authen", description = "Operations related to users authen")
@Slf4j
public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    JwtService jwtService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AccountService accountService;

    @PostMapping("/login")
    @Operation(summary = "login", description = "login")
    @ApiResponse(responseCode = "200", description = "Successful operation")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    public BaseResponse<String> authenticate(@RequestBody AuthenRequest authenRequest) {
        return BaseResponse.success(jwtService.generateJwtToken(authenRequest.getUsername()));
    }

    @PostMapping("/register")
    @Operation(summary = "add new acc", description = "add new acc")
    @ApiResponse(responseCode = "200", description = "Successful operation")
    @ApiResponse(responseCode = "400", description = "failed")
    public BaseResponse<AccountRes> addNewAcc(@RequestBody @Valid AccountReq accountReq) {
        return BaseResponse.success(accountService.createAccount(accountReq));
    }
}
