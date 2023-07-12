package com.example.comlazadserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/product")
public class AdminApi {
    @PostMapping("/add-new")
    public String addNewProduct(){
        return null;
    }

    @GetMapping("/lazada")
    public String lazada(){
        return "lazada";
    }
    @GetMapping("/lazada-get-products")
    public String lazadaProducts(){
        return "lazada-all-products";
    }
}
