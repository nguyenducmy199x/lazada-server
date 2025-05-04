package com.example.comlazadserver.controller;


import com.example.comlazadserver.dto.BaseResponse;
import com.example.comlazadserver.dto.PageProductRequest;
import com.example.comlazadserver.dto.ProductRequest;
import com.example.comlazadserver.dto.ProductResponse;
import com.example.comlazadserver.repository.ProductRepository;
import com.example.comlazadserver.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Products", description = "Operations related to products")
public class ProductController {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductService productService;

    @PostMapping(value = "/add-product", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @Operation(summary = "add product", description = "add product")
    @ApiResponse(responseCode = "200", description = "Successful operation")
    @ApiResponse(responseCode = "400", description = "failed")
    public BaseResponse<String> addProduct(@RequestPart("productDto") ProductRequest productDto,
                                           @RequestPart(value = "image", required = false) MultipartFile imageFile) throws IOException  {
        productService.addProductService(productDto, imageFile);

        if (Objects.isNull(imageFile)) {
            return BaseResponse.fail("image is null");
        }
        return BaseResponse.success("Product Added Successfully");
    }

    @PatchMapping(value = "/edit-product", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @Operation(summary = "edit product", description = "edit product")
    @ApiResponse(responseCode = "200", description = "Successful operation")
    @ApiResponse(responseCode = "400", description = "failed")
    public BaseResponse<String> editProduct(@RequestPart("productDto") ProductRequest productDto,
                                           @RequestPart(value = "image", required = false) MultipartFile imageFile) throws IOException  {
        productService.editProductService(productDto, imageFile);

        if (Objects.isNull(imageFile)) {
            return BaseResponse.fail("image is null");
        }
        return BaseResponse.success("Product Added Successfully");
    }

    @PostMapping("/get-products-by-category")
    @Operation(summary = "get-products-by-category", description = "get-products-by-category")
    @ApiResponse(responseCode = "200", description = "Successful operation")
    @ApiResponse(responseCode = "400", description = "failed")
    public ResponseEntity<List<ProductResponse>>  getProductsByCategory(@RequestBody String category) throws IOException {
        List<ProductResponse> res = productService.getProductsByCategory(category);
        return ResponseEntity.ok(res);
    }
    @PostMapping ("/get-product-by-title-and-by-paging-number")
    @Operation(summary = "get-product-by-title-and-by-paging-number", description = "get-product-by-title-and-by-paging-number")
    @ApiResponse(responseCode = "200", description = "Successful operation")
    @ApiResponse(responseCode = "400", description = "failed")
    public Map<String, Object> getProductsByTitleAndByPagingNumber(@RequestBody PageProductRequest body) throws IOException {
        return productService.getProductsByTitleAndByPagingNumber(body);
    }
    @PostMapping("/search-by-product-title")
    @Operation(summary = "search-by-product-title", description = "search-by-product-title")
    @ApiResponse(responseCode = "200", description = "Successful operation")
    @ApiResponse(responseCode = "400", description = "failed")
    public ResponseEntity<List<String>> searchByProductTitle(@RequestBody String productTitleString, HttpServletRequest request){
        List<String> results = productRepository.searchByProductTitleInputString(productTitleString);
        return ResponseEntity.ok(results);
    }
}
