package com.example.shoppingapp.customerview.color;

public class Colors {
    private String MaMau;
    private String MaMauSac;
    private String TenMau;

    public Colors(String maMau, String maMauSac, String tenMau) {
        MaMau = maMau;
        MaMauSac = maMauSac;
        TenMau = tenMau;
    }

    public String getMaMau() {
        return MaMau;
    }

    public void setMaMau(String maMau) {
        MaMau = maMau;
    }

    public String getMaMauSac() {
        return MaMauSac;
    }

    public void setMaMauSac(String maMauSac) {
        MaMauSac = maMauSac;
    }

    public String getTenMau() {
        return TenMau;
    }

    public void setTenMau(String tenMau) {
        TenMau = tenMau;
    }
}
