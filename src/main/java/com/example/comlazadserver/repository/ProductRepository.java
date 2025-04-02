package com.example.comlazadserver.repository;


import com.example.comlazadserver.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findProductByTitle(String title);

    List<Product> findByCategory(String category);

    @Query("select a.title from Product a where a.title like ?1%")
    List<String> searchByProductTitleInputString(String searchInputString);
 }
