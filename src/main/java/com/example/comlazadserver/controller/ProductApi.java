package com.example.comlazadserver.controller;


import com.example.comlazadserver.dto.PageProductRequest;
import com.example.comlazadserver.dto.ProductRequest;
import com.example.comlazadserver.dto.ProductResponse;
import com.example.comlazadserver.entity.Product;
import com.example.comlazadserver.repository.ProductPaginationRepository;
import com.example.comlazadserver.repository.ProductRepository;
import com.example.comlazadserver.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;


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

    @Autowired
    ProductPaginationRepository productPaginationRepository;

    @GetMapping("/get-products")
    public String getProducts(){
        String url = "http://localhost:8080/api/v1/store/get-stores";
        String bearer = request.getHeader(HttpHeaders.AUTHORIZATION);
        String token = bearer.substring(7);
//        String url = "http://localhost:8083/api/v1/store/get-stores";
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, token);
        HttpEntity httpEntity = new HttpEntity<>(headers);
        ResponseEntity<String> result = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
        return "products" + " - " + result.getBody();
    }

    @PostMapping(value = "/add-product", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<String> addProduct(@RequestPart("productDto") ProductRequest productDto,
                                             @RequestPart(value = "image", required = false) MultipartFile imageFile) throws IOException  {
//        productService.addProductService(productDto, imageFile);

        if (Objects.isNull(imageFile)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Image is required.");
        }
        return ResponseEntity.ok("Product Added Successfully");
    }

    @PatchMapping("/edit-product")
    public ResponseEntity<String> editProduct(HttpServletRequest request) throws IOException {
        productService.editProductService(request);
        return ResponseEntity.ok("Edit Successfully");
    }

    @PostMapping("/get-products-by-category")
    public ResponseEntity<List<ProductResponse>>  getProductsByCategory(@RequestBody String category) throws IOException {
        List<Product> products = productRepository.findByCategory(category);
        List<ProductResponse> res = new ArrayList<>();
        for (Product i: products){
            String imageName = i.getImageId();
            File file = new File("src/main/resources/images/" + imageName);
            Path path = file.toPath();
            System.out.println(path.toUri());
            String encodeImage = Base64.getEncoder().withoutPadding().encodeToString(Files.readAllBytes(path));
            System.out.println(encodeImage);
            ProductResponse productResponse = new ProductResponse();
            productResponse.setCategory(i.getCategory());
            productResponse.setImage(encodeImage);
            productResponse.setTitle(i.getTitle());
            productResponse.setPrice(i.getPrice());
            productResponse.setDescribe(i.getProductDescribe());
            res.add(productResponse);
        }
        return ResponseEntity.ok(res);
    }
    @PostMapping ("/get-product-by-title-and-by-paging-number")
    public List<ProductResponse> getProductsByTitleAndByPagingNumber(@RequestBody PageProductRequest body) throws IOException {
        int page = Integer.parseInt(body.getPageId());
        String category = body.getCategory();
        Pageable pageWithTwoElements = PageRequest.of(page, 4);
        List<Product> products = productPaginationRepository.findAllByCategory(category, pageWithTwoElements);
        List<ProductResponse> res = new ArrayList<>();
        for (Product i: products){
            String imageName = i.getImageId();
            File file = new File("src/main/resources/images/" + imageName);
            Path path = file.toPath();
            System.out.println(path.toUri());
            String encodeImage = Base64.getEncoder().withoutPadding().encodeToString(Files.readAllBytes(path));
            System.out.println(encodeImage);
            ProductResponse productResponse = new ProductResponse();
            productResponse.setCategory(i.getCategory());
            productResponse.setImage(encodeImage);
            productResponse.setTitle(i.getTitle());
            productResponse.setPrice(i.getPrice());
            productResponse.setDescribe(i.getProductDescribe());
            res.add(productResponse);
        }
        return res;
    }
    @PostMapping("/search-by-product-title")
    public ResponseEntity<List<String>> searchByProductTitle(@RequestBody String productTitleString, HttpServletRequest request){
        List<String> results = productRepository.searchByProductTitleInputString(productTitleString);
        return ResponseEntity.ok(results);
    }
}
