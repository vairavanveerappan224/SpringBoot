package com.example.applicationSpring.controller;

import com.example.applicationSpring.entity.CategoryEntity;
import com.example.applicationSpring.entity.SubCategoryEntity;
import com.example.applicationSpring.repository.CategoryRepository;
import com.example.applicationSpring.repository.SubCategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryRepository categoryRepo;
    private final SubCategoryRepository subRepo;

    public CategoryController(CategoryRepository categoryRepo,
                              SubCategoryRepository subRepo) {
        this.categoryRepo = categoryRepo;
        this.subRepo = subRepo;
    }

    @GetMapping
    public List<CategoryEntity> getAllCategories() {
        return categoryRepo.findAll();
    }

    @GetMapping("/{id}")
    public CategoryEntity getCategoryById(@PathVariable int id) {
        return categoryRepo.findById(id).orElse(null);
    }

    @GetMapping("/sub/{id}")
    public List<SubCategoryEntity> getSubcategories(@PathVariable int id) {
        return subRepo.findByCategoryId(id);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public String deleteCategory(@PathVariable int id) {

        subRepo.deleteByCategoryId(id);

        boolean exists = categoryRepo.existsById(id);
        if (!exists) {
            return "Category not found";
        }

        categoryRepo.deleteById(id);

        return "Category and its subcategories deleted successfully";
    }

    @PostMapping
    public CategoryEntity addCategory(@RequestBody CategoryEntity entity) {
        return categoryRepo.save(entity);
    }

    @PutMapping("/{id}")
    public String updateCategory(@PathVariable int id,
                                 @RequestBody CategoryEntity payload) {

        CategoryEntity cat = categoryRepo.findById(id).orElse(null);

        if (cat == null) {
            return "Category not found";
        }

        cat.setName(payload.getName());
        categoryRepo.save(cat);

        return "Category updated successfully";
    }
}
