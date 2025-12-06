package com.example.applicationSpring.controller;

import com.example.applicationSpring.entity.CategoryEntity;
import com.example.applicationSpring.entity.SubCategoryEntity;
import com.example.applicationSpring.repository.CategoryRepository;
import com.example.applicationSpring.repository.SubCategoryRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subCategory")
public class SubCategoryController {

    private final SubCategoryRepository subRepo;
    private final CategoryRepository categoryRepo;

    public SubCategoryController(SubCategoryRepository subRepo, CategoryRepository categoryRepo) {
        this.subRepo = subRepo;
        this.categoryRepo = categoryRepo;
    }

    @GetMapping
    public List<SubCategoryEntity> getAll() {
        return subRepo.findAll();
    }

    @GetMapping("/{id}")
    public SubCategoryEntity getById(@PathVariable int id) {
        return subRepo.findById(id).orElse(null);
    }

    @GetMapping("/byCategory/{catId}")
    public List<SubCategoryEntity> getByCategory(@PathVariable int catId) {
        return subRepo.findBycategory_id(catId);
    }

    @PostMapping
    public SubCategoryEntity add(@RequestBody SubCategoryEntity sub) {

        CategoryEntity cat = categoryRepo.findById(sub.getCategory().getId())
                .orElseThrow(() -> new RuntimeException("Invalid category"));

        sub.setCategory(cat);

        return subRepo.save(sub);
    }
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


    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id) {

        boolean exists = subRepo.existsById(id);
        if (!exists) {
            return "Subcategory not found";
        }

        subRepo.deleteById(id);
        return "Subcategory deleted successfully";
    }
}
