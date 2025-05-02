package com.example.comlazadserver.mapper;

import com.example.comlazadserver.dto.ProductRequest;
import com.example.comlazadserver.dto.ProductResponse;
import com.example.comlazadserver.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(source = "describe", target = "productDescribe")
    Product toEntity(ProductRequest req);
    @Mapping(source = "productDescribe", target = "describe")
    ProductResponse toResponse(Product product);

}
