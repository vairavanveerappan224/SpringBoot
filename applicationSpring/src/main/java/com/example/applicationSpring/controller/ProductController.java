package com.example.applicationSpring.controller;

import com.example.applicationSpring.entity.ProductEntity;
import com.example.applicationSpring.entity.SubCategoryEntity;
import com.example.applicationSpring.repository.ProductRepository;
import com.example.applicationSpring.repository.SubCategoryRepository;
import com.example.applicationSpring.response.ApiResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import java.time.LocalDateTime;
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

    //Product List
    @GetMapping
    public ApiResponse<List<ProductEntity>> getProductsAll() {

        List<ProductEntity> list=productRepo.findAll();
        if(list==null){
            return new ApiResponse<>(404,false,"List not found",null);
        }
        return new ApiResponse<>(200,true,"Product List",list);
    }

    //Get by Id
    @GetMapping("/{id}")
    public ApiResponse<List<ProductEntity>> getListById(@PathVariable int id) {

        ProductEntity product=productRepo.findById(id).orElse(null);
        if(product==null){
            return new ApiResponse<>(404,false,"Product not found",null);
        }
        return new ApiResponse<>(200,true,"Product with ID:"+product.getId(),List.of(product));
    }
    //Search
    @GetMapping("/search")
    public ApiResponse<List<ProductEntity>> getProductsByName(@RequestParam String name) {
        List<ProductEntity> list = productRepo.findByProductNameContainingIgnoreCase(name);
        if(list==null){
            return new ApiResponse<>(404,false,"Product not found",null);
        }
        return new ApiResponse<>(200, true, "Search by Name", list);
    }

    //filter by subcategoryname and price
    @GetMapping("/filter")
    public ApiResponse<List<ProductEntity>> filter(String text) {
        List<ProductEntity> list=productRepo.filter(text);
        if(list==null){
            return new ApiResponse<>(404,false,"SubCategory not found",null);
        }
        return new ApiResponse<>(200,true,"Product List",list);

    }

    //price range
    @GetMapping("/price-range")
    public ApiResponse<List<ProductEntity>> getProductsByPrice(@RequestParam double min, @RequestParam double max) {
        List<ProductEntity> list=productRepo.findByPriceBetween(min, max);
        if(list==null){
            return new ApiResponse<>(404,false,"Price range not found",null);

        }
        return new ApiResponse<>(200,true,"Product List with Price Ranges",list);
    }

    //date ranges
    @GetMapping("/date-range")
    public ApiResponse<List<ProductEntity>> getProductsByDate(@RequestParam LocalDateTime start, @RequestParam LocalDateTime end) {
        List<ProductEntity> list=productRepo.findByTimeStampBetween(start, end);
        if(list==null){
            return new ApiResponse<>(404,false,"Date range not found",null);
        }
        return new ApiResponse<>(200,true,"Product List with Date Ranges",list);
    }

    // add without image
//    @PostMapping
//    public ProductEntity create(@RequestBody ProductEntity payload) {
//
//        SubCategoryEntity subCategory = subRepo.findById(payload.getSubCategory().getId()).orElse(null);
//        payload.setSubCategory(subCategory);
//        return productRepo.save(payload);
//
//    }

    //Add
    @PostMapping(value = "", consumes = {"multipart/form-data"})
    public ApiResponse<List<ProductEntity>> createProduct(
            @RequestParam String productName,
            @RequestParam Double price,
            @RequestParam int subCategoryId,
            @RequestParam("image") MultipartFile imageFile
    ) throws IOException {

        SubCategoryEntity subCategory = subRepo.findById(subCategoryId).orElse(null);
        if(subCategory==null){
            return new ApiResponse<>(404,false,"Not Found Subcatgory",null);
        }

        String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
        String uploadPath = "D:/product_image/" + fileName;
        imageFile.transferTo(new File(uploadPath));

        ProductEntity product = new ProductEntity();
        product.setProductName(productName);
        product.setPrice(price);
        product.setSubCategory(subCategory);
        product.setImageUrl(fileName);

        ProductEntity products=productRepo.save(product);
        if(products==null){
            return new ApiResponse<>(404,false,"Product not saved",null);
        }
        return new ApiResponse<>(200,true,"Product Created",List.of(products));

    }

    //Delete
    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable int id) {
        boolean exists = productRepo.existsById(id);
        if (!exists) {
            return  new ApiResponse<>(404,false,"Product not Found",null);

        }
        productRepo.deleteById(id);
        return new ApiResponse<>(200,true,"Product Deleted",null);
    }

//    //update
//    @PutMapping("/{id}")
//    public ProductEntity update(@PathVariable int id, @RequestBody ProductEntity payload) {
//        ProductEntity product = productRepo.findById(id).orElse(null);
//        product.setProductName(payload.getProductName());
//        product.setPrice(payload.getPrice());
//        if (payload.getSubCategory() != null) {
//            SubCategoryEntity subCategory = subRepo.findById(payload.getSubCategory().getId()).orElse(null);
//            product.setSubCategory(subCategory);
//        }
//        return productRepo.save(product);
//    }

    //Upadate
    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    public ApiResponse<List<ProductEntity>> updateProduct(@PathVariable int id, @RequestParam String productName, @RequestParam Double price, @RequestParam int subCategoryId, @RequestParam("image") MultipartFile imageFile) throws IOException {
        SubCategoryEntity subCategory = subRepo.findById(subCategoryId).orElse(null);

        String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
        String uploadPath = "D:/product_image_update/" + fileName + "updated";
        imageFile.transferTo(new File(uploadPath));
        ProductEntity product = productRepo.findById(id).orElse(null);
        if(product==null){
            return new ApiResponse<>(404,false,"Product not found",null);
        }
        product.setProductName(productName);
        product.setPrice(price);
        product.setSubCategory(subCategory);
        product.setImageUrl(fileName);

        productRepo.save(product);
        return new ApiResponse<>(200,true,"Product Updated",List.of(product));

    }

    // Get subcategory of it
    @GetMapping("/sub/{id}")
    public ApiResponse<List<SubCategoryEntity> >getBySubCategory(@PathVariable int id) {
        ProductEntity product =  productRepo.findById(id).orElse(null);
        if (product == null) {
            return new ApiResponse<>(404,false,"Inavalid Product Id",null);
        }
        return new ApiResponse<>(200,true,"Subcategory of product with ID:"+product.getId(),List.of(product.getSubCategory()));

    }


}
