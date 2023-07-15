package com.example.shoppingapp.customerview.product;

public class ProductTrending {
    private String masp;
    private String resouceId;
    private String name;

    private Integer priceProduct;

    public ProductTrending(String masp, String resouceId, String name, Integer priceProduct){
        this.masp = masp;
        this.resouceId = resouceId;
        this.name = name;
        this.priceProduct = priceProduct;
    }


    public String getMasp() {
        return masp;
    }

    public void setMasp(String masp) {
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

    public Integer getPriceProduct() {
        return priceProduct;
    }

    public void setPriceProduct(Integer priceProduct) {
        this.priceProduct = priceProduct;
    }
}
