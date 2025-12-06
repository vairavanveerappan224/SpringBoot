package com.example.applicationSpring.controller;

import com.example.applicationSpring.entity.ProductEntity;
import com.example.applicationSpring.entity.SubCategoryEntity;
import com.example.applicationSpring.repository.ProductRepository;
import com.example.applicationSpring.repository.SubCategoryRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final SubCategoryRepository subRepo;
    private final ProductRepository productRepo;

    public ProductController(SubCategoryRepository subRepo, ProductRepository productRepo) {
        this.subRepo = subRepo;
        this.productRepo = productRepo;
    }

    //get all
    @GetMapping
    public List<ProductEntity> getProductsAll() {
        return productRepo.findAll();
    }

    //get by id
    @GetMapping("/{id}")
    public ProductEntity getById(@PathVariable int id) {
        return productRepo.findById(id).orElse(null);
    }

    //add without image
    @PostMapping
    public ProductEntity create(@RequestBody ProductEntity payload) {

        SubCategoryEntity subCategory = subRepo.findById(payload.getSubCategory().getId()).orElse(null);
        payload.setSubCategory(subCategory);
        return productRepo.save(payload);

    }

    //add
    @PostMapping(value = "", consumes = {"multipart/form-data"})
    public ProductEntity createProduct(
            @RequestParam String productName,
            @RequestParam Double price,
            @RequestParam int subCategoryId,
            @RequestParam("image") MultipartFile imageFile
    ) throws IOException {

        SubCategoryEntity subCategory = subRepo.findById(subCategoryId).orElse(null);

        String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
        String uploadPath = "D:/product_image/" + fileName;
        imageFile.transferTo(new File(uploadPath));

        ProductEntity product = new ProductEntity();
        product.setProductName(productName);
        product.setPrice(price);
        product.setSubCategory(subCategory);
        product.setImageUrl(fileName);

        return productRepo.save(product);
    }

    //delete
    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id) {
        boolean exists = productRepo.existsById(id);
        if (!exists) {
            throw new RuntimeException("Product not found");
        }
        productRepo.deleteById(id);
        return "Product deleted";
    }

    //update
    @PutMapping("/{id}")
    public ProductEntity update(@PathVariable int id, @RequestBody ProductEntity payload) {
        ProductEntity product = productRepo.findById(id).orElse(null);
        product.setProductName(payload.getProductName());
        product.setPrice(payload.getPrice());
        if (payload.getSubCategory() != null) {
            SubCategoryEntity subCategory = subRepo.findById(payload.getSubCategory().getId()).orElse(null);
            product.setSubCategory(subCategory);
        }
        return productRepo.save(product);
    }

    // get subcategory of it
    @GetMapping("/sub/{id}")
    public SubCategoryEntity getBySubCategory(@PathVariable int id) {
        ProductEntity product = productRepo.findById(id).orElse(null);
        if (product == null) {
            throw new RuntimeException("Product not found");
        }
        return product.getSubCategory();

    }


}
