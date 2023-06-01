package com.example.shoppingapp;

import java.util.ArrayList;

public class item_object {
    private String name;
    private int quanity;
    private int image;
    private ArrayList<product_object> product_list;

    public String getName() {
        return name;
    }

    public item_object(String name, int quanity, int image, ArrayList<product_object> product_list) {
        this.name = name;
        this.quanity = quanity;
        this.image = image;
        this.product_list = product_list;
    }
    public item_object(String name, int image, ArrayList<product_object> product_list) {
        this.name = name;
        getQuanity();
        this.image = image;
        this.product_list = product_list;
    }

    public ArrayList<product_object> getProduct_list() {
        return product_list;
    }

    public void setProduct_list(ArrayList<product_object> product_list) {
        this.product_list = product_list;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuanity() {
        return product_list.size();
    }

    public void setQuanity(int quanity) {
        this.quanity = quanity;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
