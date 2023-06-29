package com.example.shoppingapp.customerview.product;

public class ProductCard {
    private String MaSp;
    private String imageResouce;
    private String nameProduct;
    private Integer priceProduct;

    public ProductCard() {
    }

    public ProductCard(String imageResouce, String nameProduct, Integer priceProduct, String MaSp) {
        this.imageResouce = imageResouce;
        this.nameProduct = nameProduct;
        this.priceProduct = priceProduct;
        this.MaSp = MaSp;
    }


    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public Integer getPriceProduct() {
        return priceProduct;
    }

    public void setPriceProduct(Integer priceProduct) {
        this.priceProduct = priceProduct;
    }

    public String getImageResouce() {
        return imageResouce;
    }

    public String getMaSp() {
        return MaSp;
    }

    public void setMaSp(String maSp) {
        MaSp = maSp;
    }
}
