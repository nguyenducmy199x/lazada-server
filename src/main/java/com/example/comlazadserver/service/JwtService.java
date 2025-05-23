package com.example.comlazadserver.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.comlazadserver.dto.AuthenRequest;
import com.example.comlazadserver.dto.BaseResponse;
import com.example.comlazadserver.entity.User;
import com.example.comlazadserver.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class JwtService {
    public String SECRET_KEY = "7c2853fe-140a-11ee-be56-0242ac120002";
    @Autowired
    UserRepository  userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public String generateJwtToken(AuthenRequest authenRequest){
        User user = userRepository.findByUsername(authenRequest.getUsername());
        if (user == null || !passwordEncoder.matches(authenRequest.getPassword(), user.getPassword()))
            throw new IllegalArgumentException("Tài khoản với username "  + authenRequest.getUsername() + " không tồn tại !");

        return JWT.create()
                .withIssuer(authenRequest.getUsername())
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + 60*60*1000))
                .sign(Algorithm.HMAC256(SECRET_KEY));
    }
    public boolean verifyToken(String token){
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SECRET_KEY)).build();
        boolean result = false;
        try {
            DecodedJWT decodedJWT = jwtVerifier.verify(token);

        }catch (JWTVerificationException e){
            log.error("Token not verified" + e);
            return result;
        }
        result = true;
        return result;
    }
    public String getUsername(String token){
        return JWT.decode(token).getIssuer();
    }

}
