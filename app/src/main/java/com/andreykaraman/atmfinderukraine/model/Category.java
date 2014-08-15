package com.andreykaraman.atmfinderukraine.model;

public class Category {
    int _id;
    String categoryName;

    public Category() {
    }

    public Category(int _id, String categoryName) {
        this._id = _id;
        this.categoryName = categoryName;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
