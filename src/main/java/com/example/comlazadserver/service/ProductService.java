package com.example.comlazadserver.service;



import com.example.comlazadserver.dto.PageProductRequest;
import com.example.comlazadserver.dto.ProductRequest;
import com.example.comlazadserver.dto.ProductResponse;
import com.example.comlazadserver.entity.Product;
import com.example.comlazadserver.repository.ProductPaginationRepository;
import com.example.comlazadserver.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class ProductService {

    @Autowired
    ObjectMapper om;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductPaginationRepository productPaginationRepository;

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
    public Map<String, Object> getProductsByTitleAndByPagingNumber(PageProductRequest body) throws IOException{
        int page = Integer.parseInt(body.getPageId());
        String category = body.getCategory();
        Pageable pageWithTwoElements = PageRequest.of(page, Integer.parseInt(body.getNumberOfProduct()));
        List<Product> products = productPaginationRepository.findAllByCategory(category, pageWithTwoElements);
        List<Product> totalItems = productPaginationRepository.findByCategory(category);
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
        Map<String, Object> response = new HashMap<>();
        response.put("products", res);
        response.put("totalItems", totalItems.size());

        return response;
    }
    public List<ProductResponse> getProductsByCategory(String category) throws IOException {
        List<ProductResponse> res = new ArrayList<>();

        List<Product> products = productRepository.findByCategory(category);
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
}
