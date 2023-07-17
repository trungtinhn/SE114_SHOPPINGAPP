package com.example.shoppingapp.StaffView.MyOrder;

public class Xacnhandonhang {
    private String MaND, MaDH;
    public Xacnhandonhang(String MaND, String MaDH)
    {
        this.MaDH = MaDH;
        this.MaND = MaND;
    }
    public String getMaND() {
        return MaND;
    }

    public void setMaND(String maND) {
        MaND = maND;
    }

    public String getMaDH() {
        return MaDH;
    }

    public void setMaDH(String maDH) {
        MaDH = maDH;
    }
}
