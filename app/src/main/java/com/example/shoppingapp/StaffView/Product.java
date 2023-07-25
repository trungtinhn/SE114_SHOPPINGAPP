package com.example.shoppingapp.StaffView;

import java.util.List;

public class Product  {
    private String MoTaSP;
    private String name;

    public String getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(String trangThai) {
        TrangThai = trangThai;
    }

    private String TrangThai;
    private int price;
    private String avatar;
    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    private String maSP;
    private int warehouse; //Tồn kho
    private Object imageUrl;
    private int love;  //Yêu thích
    private int sold; //Bán ra
    private int views; //Số lượng người xem

    public List<String> getMauSac() {
        return MauSac;
    }

    public void setMauSac(List<String> mauSac) {
        MauSac = mauSac;
    }

    private List<String> MauSac;
    public Product(String hinhAnhSP, String TenSP, int GiaSP, int warehouse, int sold, int Love, int View, String MaSP)
    {
        this.avatar = hinhAnhSP;
        this.name = TenSP;
        this.price = GiaSP;
        this.warehouse = warehouse;
        this.sold=  sold;
        this.love = Love;
        this.views = View;
        this.maSP = MaSP;
    }
    public Product(String hinhAnhSP, String tenSP, int giaSP) {
        avatar = hinhAnhSP;
        name = tenSP;
        price = giaSP;
    }

    public List<String> getSize() {
        return Size;
    }

    public void setSize(List<String> size) {
        Size = size;
    }

    private List<String> Size;
    public Product(){}
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(int warehouse) {
        this.warehouse = warehouse;
    }

    public Object getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(Object imageUrl) {
        this.imageUrl = imageUrl;
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

    public String getMoTaSP() {
        return MoTaSP;
    }

    public void setMoTaSP(String moTaSP) {
        MoTaSP = moTaSP;
    }

}
