package com.example.comlazadserver.service;



import com.example.comlazadserver.dto.ProductRequest;
import com.example.comlazadserver.entity.Product;
import com.example.comlazadserver.repository.ProductRepository;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.example.comlazadserver.constants.AppConstants.ROOT_PATH;

@Service
public class ProductService {

    @Autowired
    ObjectMapper om;
    @Autowired
    ProductRepository productRepository;

    public void addProductService(ProductRequest request, MultipartFile imageFile) throws IOException {
        ClassPathResource pathResource = new ClassPathResource(ROOT_PATH);
        File imageDest = new File(pathResource.getPath() + "Product_" + UUID.randomUUID());
        String productImageId = imageDest.getName();
        Product newProduct = new Product();
        newProduct.setCategory(request.getCategory());
        newProduct.setTitle(request.getTitle().trim());
        newProduct.setPrice(request.getPrice());
        newProduct.setProductDescribe(request.getDescribe());
        newProduct.setImageId(productImageId);
        productRepository.save(newProduct);

        try {
            imageFile.transferTo(imageDest.toPath());
        } catch (NoSuchFileException e) {
            System.out.println(e);
        }
    }

    public void editProductService(ProductRequest request, MultipartFile imageFile) throws IOException {
        ClassPathResource pathResource = new ClassPathResource(ROOT_PATH);
        File imageDest = new File(pathResource.getPath() + "Product_" + UUID.randomUUID());
        String productImageId = imageDest.getName();
        Product newProduct = new Product();
        newProduct.setCategory(request.getCategory());
        newProduct.setTitle(request.getTitle().trim());
        newProduct.setPrice(request.getPrice());
        newProduct.setProductDescribe(request.getDescribe());
        newProduct.setImageId(productImageId);
        productRepository.save(newProduct);

        try {
            imageFile.transferTo(imageDest.toPath());
        } catch (NoSuchFileException e) {
            System.out.println(e);
        }
    }
}
