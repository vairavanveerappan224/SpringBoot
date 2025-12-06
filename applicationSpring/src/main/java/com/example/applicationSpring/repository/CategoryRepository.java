package com.example.applicationSpring.repository;

import com.example.applicationSpring.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository
        extends JpaRepository<CategoryEntity, Integer> {

}
