package com.example.shoppingapp.StaffView.MyOrder;

public class ItemOrder {

    private String TenSP;
    private String MauSac;
    private String Size;

    public String getMauSac() {
        return MauSac;
    }

    public void setMauSac(String mauSac) {
        MauSac = mauSac;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }


    public String getHinhAnhSP() {
        return HinhAnhSP;
    }

    public void setHinhAnhSP(String hinhAnhSP) {
        HinhAnhSP = hinhAnhSP;
    }

    private String HinhAnhSP;
    public int getGiaSP() {
        return GiaSP;
    }

    public void setGiaSP(int giaSP) {
        GiaSP = giaSP;
    }

    private int GiaSP;
    private String MaDH;
    private String MaMauSac;
    private String MaSP;
    private String MaSize;
    private int SoLuong;

    public ItemOrder (String HinhAnhSP,String TenSP, String MaSP,int GiaSP, int SoLuong){
        this.GiaSP = GiaSP;
        this.HinhAnhSP = HinhAnhSP;
        this.TenSP = TenSP;
        this.MaSP = MaSP;
        this.SoLuong = SoLuong;
    }
    public ItemOrder (String HinhAnhSP,String TenSP, String MaSP,int GiaSP, int SoLuong, String color, String size){
        this.GiaSP = GiaSP;
        this.HinhAnhSP = HinhAnhSP;
        this.TenSP = TenSP;
        this.MaSP = MaSP;
        this.SoLuong = SoLuong;
        this.MauSac = color;
        this.Size = size;
    }

    public String getTenSP() {
        return TenSP;
    }

    public void setTenSP(String tenSP) {
        TenSP = tenSP;
    }


    public String getMaSize() {
        return MaSize;
    }

    public void setMaSize(String maSize) {
        MaSize = maSize;
    }


    public String getMaDH() {
        return MaDH;
    }

    public void setMaDH(String maDH) {
        MaDH = maDH;
    }

    public String getMaMauSac() {
        return MaMauSac;
    }

    public void setMaMauSac(String maMauSac) {
        MaMauSac = maMauSac;
    }

    public String getMaSP() {
        return MaSP;
    }

    public void setMaSP(String maSP) {
        MaSP = maSP;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int soLuong) {
        SoLuong = soLuong;
    }


}
