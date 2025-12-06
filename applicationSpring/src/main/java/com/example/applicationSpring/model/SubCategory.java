package com.example.applicationSpring.model;

public class SubCategory {
    private int id;
    private String subCategoryName;
    private int category_id;
    public SubCategory() {}
    public SubCategory(int Id, String subCategoryName, int category_id) {
        this.id = id;
        this.subCategoryName = subCategoryName;
        this.category_id = category_id;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }
}
