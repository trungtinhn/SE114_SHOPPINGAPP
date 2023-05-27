package com.example.shoppingapp;

public class product_object {
    private String name;
    private double price;
    private int avatar;
    private int warehouse;
    private int love;
    private int sold;
    private int views;
    private  int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public product_object(String name, double price, int avatar, int warehouse, int love, int sold, int view,int count) {
        this.name = name;
        this.price = price;
        this.avatar = avatar;
        this.warehouse = warehouse;
        this.love = love;
        this.sold = sold;
        this.views = views;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public int getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(int warehouse) {
        this.warehouse = warehouse;
    }

    public int getLove() {
        return love;
    }

    public void setLove(int love) {
        this.love = love;
    }

    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }
}
