package com.example.shoppingapp.StaffView.MyOrder;

public class ItemOrder {

    private String TenSP, HinhAnhSP;
    private int SoLuong, Gia;

    public void setHinhAnhSP(String hinhAnhSP) {
        HinhAnhSP = hinhAnhSP;
    }

    public void setGia(int gia) {
        Gia = gia;
    }

    public void setTenSP(String tenSP) {
        TenSP = tenSP;
    }

    public void setSoLuong(int soLuong) {
        SoLuong = soLuong;
    }

    public String getHinhAnhSP() {
        return HinhAnhSP;
    }

    public String getTenSP() {
        return TenSP;
    }

    public int getGia() {
        return Gia;
    }

    public int getSoLuong() {
        return SoLuong;
    }
}
