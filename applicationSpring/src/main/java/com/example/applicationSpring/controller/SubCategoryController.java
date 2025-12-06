package com.example.applicationSpring.controller;

import com.example.applicationSpring.entity.CategoryEntity;
import com.example.applicationSpring.entity.ProductEntity;
import com.example.applicationSpring.entity.SubCategoryEntity;
import com.example.applicationSpring.repository.CategoryRepository;
import com.example.applicationSpring.repository.ProductRepository;
import com.example.applicationSpring.repository.SubCategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subCategory")
public class SubCategoryController {

    private final SubCategoryRepository subRepo;
    private final CategoryRepository categoryRepo;
    private final ProductRepository productRepo;

    public SubCategoryController(SubCategoryRepository subRepo, CategoryRepository categoryRepo, ProductRepository productRepo) {
        this.subRepo = subRepo;
        this.categoryRepo = categoryRepo;
        this.productRepo = productRepo;
    }

    //get all
    @GetMapping
    public List<SubCategoryEntity> getAll() {
        return subRepo.findAll();
    }

    //get by id
    @GetMapping("/{id}")
    public SubCategoryEntity getById(@PathVariable int id) {
        return subRepo.findById(id).orElse(null);
    }

    //get category
    @GetMapping("/category/{id}")
    public CategoryEntity getByCategory(@PathVariable int id) {

        SubCategoryEntity sub = subRepo.findById(id).orElse(null);
        if (sub == null) {
            return null;
        }
        return sub.getCategory();
    }

    //add
    @PostMapping
    public SubCategoryEntity add(@RequestBody SubCategoryEntity sub) {

        CategoryEntity cat = categoryRepo.findById(sub.getCategory().getId())
                .orElseThrow(() -> new RuntimeException("Invalid category"));

        sub.setCategory(cat);

        return subRepo.save(sub);
    }

    //update
    @PutMapping("/{id}")
    public SubCategoryEntity update(
            @PathVariable int id,
            @RequestBody SubCategoryEntity sub) {

        SubCategoryEntity existing = subRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Subcategory not found"));

        existing.setSubCategoryName(sub.getSubCategoryName());

        // handle category update
        if (sub.getCategory() != null) {
            CategoryEntity cat = categoryRepo.findById(sub.getCategory().getId())
                    .orElseThrow(() -> new RuntimeException("Invalid category Id"));

            existing.setCategory(cat);
        }

        return subRepo.save(existing);
    }

    //delete
    @DeleteMapping("/{id}")
    @Transactional
    public String deleteSubCategory(@PathVariable int id) {

        productRepo.deleteBySubCategoryId(id);

        boolean exists = subRepo.existsById(id);
        if (!exists) {
            return "SubCategory not found";
        }

        subRepo.deleteById(id);

        return "SubCategory and its products deleted successfully";
    }

    //get its product mapped to it
    @GetMapping("/product/{id}")
    public List<ProductEntity> getProductById(@PathVariable int id) {
        return productRepo.findBySubCategoryId(id);
    }
}
