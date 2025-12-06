package com.example.applicationSpring.repository;

import com.example.applicationSpring.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity,Integer> {
    List<ProductEntity> findBySubCategoryId(int subCategoryId);
    void deleteBySubCategoryId(int subCategoryId);

}
