package com.example.comlazadserver.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequest {
    private String category;
    private String title;
    private String price;
    private String describe;
}
