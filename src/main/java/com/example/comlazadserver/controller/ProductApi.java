package com.example.comlazadserver.controller;


import com.example.comlazadserver.dto.BaseResponse;
import com.example.comlazadserver.dto.PageProductRequest;
import com.example.comlazadserver.dto.ProductRequest;
import com.example.comlazadserver.dto.ProductResponse;
import com.example.comlazadserver.repository.ProductRepository;
import com.example.comlazadserver.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;


@RestController
@RequestMapping("/api/v1/product")
public class ProductApi {
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    HttpServletRequest request;

    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductService productService;

//    @GetMapping("/get-products")
//    public String getProducts(){
//        String url = "http://localhost:8080/api/v1/store/get-stores";
//        String bearer = request.getHeader(HttpHeaders.AUTHORIZATION);
//        String token = bearer.substring(7);
////        String url = "http://localhost:8083/api/v1/store/get-stores";
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(HttpHeaders.AUTHORIZATION, token);
//        HttpEntity httpEntity = new HttpEntity<>(headers);
//        ResponseEntity<String> result = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
//        return "products" + " - " + result.getBody();
//    }

    @PostMapping(value = "/add-product", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public BaseResponse<String> addProduct(@RequestPart("productDto") ProductRequest productDto,
                                           @RequestPart(value = "image", required = false) MultipartFile imageFile) throws IOException  {
        productService.addProductService(productDto, imageFile);

        if (Objects.isNull(imageFile)) {
            return BaseResponse.fail("image is null");
        }
        return BaseResponse.success("Product Added Successfully");
    }

    @PatchMapping(value = "/edit-product", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public BaseResponse<String> editProduct(@RequestPart("productDto") ProductRequest productDto,
                                           @RequestPart(value = "image", required = false) MultipartFile imageFile) throws IOException  {
        productService.editProductService(productDto, imageFile);

        if (Objects.isNull(imageFile)) {
            return BaseResponse.fail("image is null");
        }
        return BaseResponse.success("Product Added Successfully");
    }

    @PostMapping("/get-products-by-category")
    public ResponseEntity<List<ProductResponse>>  getProductsByCategory(@RequestBody String category) throws IOException {
        List<ProductResponse> res = productService.getProductsByCategory(category);
        return ResponseEntity.ok(res);
    }
    @PostMapping ("/get-product-by-title-and-by-paging-number")
    public Map<String, Object> getProductsByTitleAndByPagingNumber(@RequestBody PageProductRequest body) throws IOException {
        return productService.getProductsByTitleAndByPagingNumber(body);
    }
    @PostMapping("/search-by-product-title")
    public ResponseEntity<List<String>> searchByProductTitle(@RequestBody String productTitleString, HttpServletRequest request){
        List<String> results = productRepository.searchByProductTitleInputString(productTitleString);
        return ResponseEntity.ok(results);
    }
}
