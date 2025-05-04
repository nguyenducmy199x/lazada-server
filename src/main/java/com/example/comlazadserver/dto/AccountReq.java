package com.example.comlazadserver.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data @AllArgsConstructor @NoArgsConstructor
public class AccountReq extends AuthenRequest {
    @Email(message = "email không hợp lệ") @NonNull
    private String email;
}
