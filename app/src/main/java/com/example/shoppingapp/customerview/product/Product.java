package com.example.shoppingapp.customerview.product;

public class Product {
    private String resouceId;
    private String name;

    public Product(String resouceId, String name) {
        this.resouceId = resouceId;
        this.name = name;
    }

    public String getResouceId() {
        return resouceId;
    }

    public void setResouceId(String resouceId) {
        this.resouceId = resouceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
