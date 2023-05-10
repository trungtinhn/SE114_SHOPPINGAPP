package com.example.shoppingapp.product;

public class ProductCard {
    private int imageResouce;
    private String nameProduct;
    private Integer priceProduct;

    public ProductCard() {
    }

    public ProductCard(int imageResouce, String nameProduct, Integer priceProduct) {
        this.imageResouce = imageResouce;
        this.nameProduct = nameProduct;
        this.priceProduct = priceProduct;
    }

    public int getImageResouce() {
        return imageResouce;
    }

    public void setImageResouce(int imageResouce) {
        this.imageResouce = imageResouce;
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
}
