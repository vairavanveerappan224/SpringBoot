package com.example.applicationSpring.response;

import com.example.applicationSpring.entity.ProductEntity;
import com.example.applicationSpring.model.PaginationInfo;
import com.example.applicationSpring.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ProductPageResponse {
    private List<ProductEntity> product;
    private PaginationInfo pagination;

}
