package com.example.comlazadserver.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data @AllArgsConstructor @NoArgsConstructor
@Builder
public class AuthenRequest {
    @NonNull
    private String username;
    @NonNull
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$")
    private String password;
}
