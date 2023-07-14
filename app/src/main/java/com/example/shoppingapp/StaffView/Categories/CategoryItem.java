package com.example.shoppingapp.StaffView.Categories;

public class CategoryItem {
    private String name;
    private String image;
    private int quantity;

    public CategoryItem(String name, String image, int quantity) {
        this.name = name;
        this.image = image;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public int getQuantity() {
        return quantity;
    }
}

