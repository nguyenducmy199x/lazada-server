package com.example.comlazadserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class ProductResponse {
    private String category;
    private String title;
    private String price;
    private String describe;

    private String image;


}
