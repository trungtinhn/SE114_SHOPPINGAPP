package com.example.shoppingapp.StaffView.Promotions;

import com.google.firebase.Timestamp;

public class PromotionStaff {
    public PromotionStaff(){}
    public String getTenKM() {
        return TenKM;
    }

    public void setTenKM(String tenKM) {
        TenKM = tenKM;
    }

    public String getChiTietKM() {
        return ChiTietKM;
    }

    public void setChiTietKM(String chiTietKM) {
        ChiTietKM = chiTietKM;
    }

    public String getLoaiKhuyenMai() {
        return LoaiKhuyenMai;
    }

    public void setLoaiKhuyenMai(String loaiKhuyenMai) {
        LoaiKhuyenMai = loaiKhuyenMai;
    }

    public String getHinhAnhKM() {
        return HinhAnhKM;
    }

    public void setHinhAnhKM(String hinhAnhKM) {
        HinhAnhKM = hinhAnhKM;
    }

    public String getMaKM() {
        return MaKM;
    }

    public void setMaKM(String maKM) {
        MaKM = maKM;
    }

    public long getDonToiThieu() {
        return DonToiThieu;
    }

    public void setDonToiThieu(int donToiThieu) {
        DonToiThieu = donToiThieu;
    }

    public double getTiLe() {
        return TiLe;
    }

    public void setTiLe(int tiLe) {
        TiLe = tiLe;
    }

    public Timestamp getNgayBatDau() {
        return NgayBatDau;
    }

    public void setNgayBatDau(Timestamp ngayBatDau) {
        NgayBatDau = ngayBatDau;
    }

    public Timestamp getNgayKetThuc() {
        return NgayKetThuc;
    }

    public void setNgayKetThuc(Timestamp ngayKetThuc) {
        NgayKetThuc = ngayKetThuc;
    }

    private String TenKM, ChiTietKM, LoaiKhuyenMai, HinhAnhKM, MaKM;
    private long DonToiThieu;

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }

    private Boolean isSelected;
    private  double TiLe;
    private Timestamp NgayBatDau, NgayKetThuc;
    public PromotionStaff(String tenKM, String chiTietKM, String loaiKhuyenMai, String hinhAnhKM, String maKM,
                          long donToiThieu, double tiLe, Timestamp ngayBatDau, Timestamp ngayKetThuc){
        this.ChiTietKM=  chiTietKM;
        this.HinhAnhKM= hinhAnhKM;
        this.TenKM = tenKM;
        this.LoaiKhuyenMai = loaiKhuyenMai;
        this.MaKM = maKM;
        this.DonToiThieu = donToiThieu;
        this.TiLe = tiLe;
        this.NgayBatDau = ngayBatDau;
        this.NgayKetThuc = ngayKetThuc;
    }
    public PromotionStaff(String tenKM, String chiTietKM, String loaiKhuyenMai, String hinhAnhKM, String maKM,
                          long donToiThieu, double tiLe, Timestamp ngayBatDau, Timestamp ngayKetThuc, boolean isSelected){
        this.ChiTietKM=  chiTietKM;
        this.HinhAnhKM= hinhAnhKM;
        this.TenKM = tenKM;
        this.LoaiKhuyenMai = loaiKhuyenMai;
        this.MaKM = maKM;
        this.DonToiThieu = donToiThieu;
        this.TiLe = tiLe;
        this.NgayBatDau = ngayBatDau;
        this.NgayKetThuc = ngayKetThuc;
        this.isSelected = isSelected;
    }

}
