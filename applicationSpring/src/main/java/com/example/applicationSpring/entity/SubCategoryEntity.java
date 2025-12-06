package com.example.applicationSpring.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "application_sub_category")
public class SubCategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String subCategoryName;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    public int getSubCategoryId() {
        return id;
    }

    public void setSubCategoryId(int id) {
        this.id = id;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }
}
