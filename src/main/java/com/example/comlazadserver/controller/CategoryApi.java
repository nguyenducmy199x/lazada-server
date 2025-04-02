package com.example.comlazadserver.controller;


import com.example.comlazadserver.entity.Category;
import com.example.comlazadserver.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryApi {
    @Autowired
    CategoryRepository categoryRepository;

    @PostMapping(name = "/add-category")
    public ResponseEntity<String> addCategory(@RequestBody Category category){

        return ResponseEntity.ok("Add Category Successfully!");
    }

    @GetMapping("/get-categories")
    public ResponseEntity<List<Category>> getCategories(){
        List<Category> categoryList = categoryRepository.findAll();

        return ResponseEntity.ok(categoryList);
    }
}
