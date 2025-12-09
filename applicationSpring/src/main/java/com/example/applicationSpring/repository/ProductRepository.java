package com.example.applicationSpring.repository;

import com.example.applicationSpring.entity.ProductEntity;
import org.antlr.v4.runtime.atn.SemanticContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity,Integer> {
    List<ProductEntity> findBySubCategoryId(int subCategoryId);
    void deleteBySubCategoryId(int subCategoryId);
    List<ProductEntity> findByProductNameContainingIgnoreCase(String keyword);
    @Query("SELECT p FROM ProductEntity p WHERE LOWER(p.productName) LIKE LOWER(CONCAT('%', :text, '%')) OR " +
            "LOWER(p.subCategory.subCategoryName) LIKE LOWER(CONCAT('%', :text, '%')) OR " +
            "LOWER(p.subCategory.category.name) LIKE LOWER(CONCAT('%', :text, '%'))")
    List<ProductEntity> filter(String text);

    List<ProductEntity> findByPriceBetween(Double min, Double max);
    List<ProductEntity> findByTimeStampBetween(LocalDateTime start, LocalDateTime end);
}
