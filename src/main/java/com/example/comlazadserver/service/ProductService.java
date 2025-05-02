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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.*;

import static com.example.comlazadserver.constants.AppConstants.ROOT_PATH;

@Service
@AllArgsConstructor
@Slf4j
public class ProductService {
    private final ObjectMapper om;
    private final ProductRepository productRepository;
    private final ProductPaginationRepository productPaginationRepository;
    private final ProductMapper productMapper;

    public void addProductService(ProductRequest request, MultipartFile imageFile) throws IOException {
        ClassPathResource pathResource = new ClassPathResource(ROOT_PATH);
        File imageDest = new File(pathResource.getPath() + "Product_" + UUID.randomUUID());
        String productImageId = imageDest.getName();
        Product newProduct = productMapper.toEntity(request);
        newProduct.setImageId(productImageId);
        productRepository.save(newProduct);
        try {
            imageFile.transferTo(imageDest.toPath());
        } catch (NoSuchFileException e) {
            log.info(e.getMessage());
        }
    }

    public void editProductService(ProductRequest request, MultipartFile imageFile) throws IOException {
        ClassPathResource pathResource = new ClassPathResource(ROOT_PATH);
        File imageDest = new File(pathResource.getPath() + "Product_" + UUID.randomUUID());
        String productImageId = imageDest.getName();
        Product newProduct = productMapper.toEntity(request);
        newProduct.setImageId(productImageId);
        productRepository.save(newProduct);
        try {
            imageFile.transferTo(imageDest.toPath());
        } catch (NoSuchFileException e) {
            log.info(e.getMessage());
        }
    }

    public Map<String, Object> getProductsByTitleAndByPagingNumber(PageProductRequest body) throws IOException {
        int page = Integer.parseInt(body.getPageId());
        String category = body.getCategory();
        Pageable pageWithTwoElements = PageRequest.of(page, Integer.parseInt(body.getNumberOfProduct()));
        List<Product> products = productPaginationRepository.findAllByCategory(category, pageWithTwoElements);
        List<Product> totalItems = productPaginationRepository.findByCategory(category);
        List<ProductResponse> res = new ArrayList<>();
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
            ProductResponse productResponse = productMapper.toResponse(product);
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
            ProductResponse productResponse = productMapper.toResponse(product);
            productResponse.setImage(encodeImage);
            res.add(productResponse);
        });
        return res;
    }
}
