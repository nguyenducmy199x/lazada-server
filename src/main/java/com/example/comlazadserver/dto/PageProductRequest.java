package com.example.comlazadserver.dto;

import lombok.Data;

@Data
public class PageProductRequest {
    private String pageId;
    private String category;
    private String numberOfProduct;
}
