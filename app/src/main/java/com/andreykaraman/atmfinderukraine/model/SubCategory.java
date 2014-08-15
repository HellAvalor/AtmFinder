package com.andreykaraman.atmfinderukraine.model;

public class SubCategory {
    int _id;
    String SubCategoryName;

    public SubCategory() {

    }

    public SubCategory(int _id, String subCategoryName) {
        this._id = _id;
        SubCategoryName = subCategoryName;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getSubCategoryName() {
        return SubCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        SubCategoryName = subCategoryName;
    }
}
