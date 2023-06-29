package com.example.shoppingapp.customerview.product;

public class Product {
    private String masp;
    private String resouceId;
    private String name;

    public Product(String resouceId, String name, String masp) {
        this.resouceId = resouceId;
        this.name = name;
        this.masp = masp;
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

    public String getMasp() {
        return masp;
    }

    public void setMasp(String masp) {
        this.masp = masp;
    }
}
