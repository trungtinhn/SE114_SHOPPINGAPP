package com.example.shoppingapp.customerview.fragment.Notification;

public class Notification {
    private String TenKM;

    public String getHinhAnhKM() {
        return HinhAnhKM;
    }

    public void setHinhAnhKM(String hinhAnhKM) {
        HinhAnhKM = hinhAnhKM;
    }

    public String getHinhAnhTB() {
        return HinhAnhTB;
    }

    public void setHinhAnhTB(String hinhAnhTB) {
        HinhAnhTB = hinhAnhTB;
    }

    private String HinhAnhKM;
    private String HinhAnhTB;
    public String getTenKM() {
        return TenKM;
    }

    public void setTenKM(String tenKM) {
        TenKM = tenKM;
    }

    public String getMaTB() {
        return MaTB;
    }

    public void setMaTB(String maTB) {
        MaTB = maTB;
    }

    public String getMaKM() {
        return MaKM;
    }

    public void setMaKM(String maKM) {
        MaKM = maKM;
    }

    public String getNoiDung() {
        return NoiDung;
    }

    public void setNoiDung(String noiDung) {
        NoiDung = noiDung;
    }

    public String getTB() {
        return TB;
    }

    public void setTB(String TB) {
        this.TB = TB;
    }

    public String getLoaiTB() {
        return LoaiTB;
    }

    public void setLoaiTB(String loaiTB) {
        LoaiTB = loaiTB;
    }

    public String getMaDH() {
        return MaDH;
    }

    public void setMaDH(String maDH) {
        MaDH = maDH;
    }

    public String getMaND() {
        return maND;
    }

    public void setMaND(String maND) {
        this.maND = maND;
    }

    private String MaTB;
    private String MaKM;
    private String NoiDung;
    private String TB;
    private String LoaiTB;
    private String MaDH, maND;
    public Notification (String HinhAnhKM, String HinhAnhTB,String MaTB, String MaKM, String NoiDung, String TB, String LoaiTB, String TenKM)
    {
        this.MaTB = MaTB;
        this.MaKM = MaKM;
        this.TB = TB;
        this.LoaiTB = LoaiTB;
        this.NoiDung = NoiDung;
        this.HinhAnhKM = HinhAnhKM;
        this.HinhAnhTB = HinhAnhTB;
        this.TenKM = TenKM;
    }
}
