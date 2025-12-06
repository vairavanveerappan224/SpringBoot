package com.example.applicationSpring.repository;

import com.example.applicationSpring.entity.SubCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SubCategoryRepository
        extends JpaRepository<SubCategoryEntity, Integer> {

    List<SubCategoryEntity> findByCategoryId(int categoryId);

    void deleteByCategoryId(int categoryId);
}
