package com.example.shoppingapp.customerview.shoppingcart;

public class ShoppingCart {
    private int dataImage;
    private String TenSanPham;
    private String TenDanhMuc;
    private String Gia;
    private int SoLuong;
    private boolean Check;

    public int getDataImage() {
        return dataImage;
    }

    public void setDataImage(int dataImage) {
        this.dataImage = dataImage;
    }

    public String getTenSanPham() {
        return TenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        TenSanPham = tenSanPham;
    }

    public String getTenDanhMuc() {
        return TenDanhMuc;
    }

    public void setTenDanhMuc(String tenDanhMuc) {
        TenDanhMuc = tenDanhMuc;
    }

    public String getGia() {
        return Gia;
    }

    public void setGia(String gia) {
        Gia = gia;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int soLuong) {
        SoLuong = soLuong;
    }

    public boolean isCheck() {
        return Check;
    }

    public void setCheck(boolean check) {
        Check = check;
    }
}
