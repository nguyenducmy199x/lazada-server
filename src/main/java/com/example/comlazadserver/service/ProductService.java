package com.example.comlazadserver.service;


import com.example.comlazadserver.dto.PageProductRequest;
import com.example.comlazadserver.dto.ProductRequest;
import com.example.comlazadserver.dto.ProductResponse;
import com.example.comlazadserver.entity.Product;
import com.example.comlazadserver.mapper.ProductMapper;
import com.example.comlazadserver.repository.ProductPaginationRepository;
import com.example.comlazadserver.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static com.example.comlazadserver.constants.AppConstants.S3_BASE_URL;

@Service
@AllArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductPaginationRepository productPaginationRepository;
    private final S3Service s3Service;


    public void addProductService(ProductRequest request, MultipartFile imageFile) throws IOException {
        var productImageId = s3Service.uploadFile(imageFile);
        Product newProduct = ProductMapper.INSTANCE.toEntity(request);
        newProduct.setImageId(productImageId);
        log.info("New product: {}", newProduct);
        productRepository.save(newProduct);
    }

    public void editProductService(ProductRequest request, MultipartFile imageFile) throws IOException {
        var productImageId = s3Service.uploadFile(imageFile);
        Product newProduct = ProductMapper.INSTANCE.toEntity(request);
        newProduct.setImageId(productImageId);
        productRepository.save(newProduct);
    }

    public Map<String, Object> getProductsByTitleAndByPagingNumber(PageProductRequest body) {
        int page = Integer.parseInt(body.getPageId());
        String category = body.getCategory();
        Pageable pageWithTwoElements = PageRequest.of(page, Integer.parseInt(body.getNumberOfProduct()));
        List<Product> products = productPaginationRepository.findAllByCategory(category, pageWithTwoElements);
        List<Product> totalItems = productPaginationRepository.findByCategory(category);
        List<ProductResponse> res = new ArrayList<>();
        Map<String, Object> response = getStringObjectMap(products, res, totalItems);
        return response;
    }

    private Map<String, Object> getStringObjectMap(List<Product> products, List<ProductResponse> res, List<Product> totalItems) {
        products.stream().forEach(product -> {
            String imageName = product.getImageId().substring(S3_BASE_URL.length());
            String encodeImage = null;
            try {
                encodeImage = s3Service.downloadFile(imageName);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ProductResponse productResponse = ProductMapper.INSTANCE.toResponse(product);
            productResponse.setImage(encodeImage);

            res.add(productResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("products", res);
        response.put("totalItems", totalItems.size());
        return response;
    }

    public List<ProductResponse> getProductsByCategory(String category) throws IOException {
        List<ProductResponse> res = new ArrayList<>();
        List<Product> products = productRepository.findByCategory(category);
        products.stream().forEach(product -> {
            String imageName = product.getImageId();
            File file = new File("src/main/resources/images/" + imageName);
            Path path = file.toPath();
            String encodeImage = null;
            try {
                encodeImage = Base64.getEncoder().withoutPadding().encodeToString(Files.readAllBytes(path));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ProductResponse productResponse = ProductMapper.INSTANCE.toResponse(product);
            productResponse.setImage(encodeImage);
            res.add(productResponse);
        });
        return res;
    }
}
