package com.example.shoppingapp.StaffView.MyOrder;

import java.util.Date;

public class Order {
    
    public void setTenNguoiMua(String tenNguoiMua) {
        TenNguoiMua = tenNguoiMua;
    }
    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public void setMaDC(String maDC) {
        MaDC = maDC;
    }

    public void setMaDH(String maDH) {
        MaDH = maDH;
    }

    public void setMaKM(String maKM) {
        MaKM = maKM;
    }

    public void setPhuongThucTT(String phuongThucTT) {
        PhuongThucTT = phuongThucTT;
    }

    public void setTrangThai(String trangThai) {
        TrangThai = trangThai;
    }

    public void setGiamGia(int giamGia) {
        GiamGia = giamGia;
    }

    public void setPhiVanChuyen(int phiVanChuyen) {
        PhiVanChuyen = phiVanChuyen;
    }

    public void setTamTinh(int tamTinh) {
        TamTinh = tamTinh;
    }

    public void setTongTien(int tongTien) {
        TongTien = tongTien;
    }

    public void setDuKienGiaoHang(Date duKienGiaoHang) {
        DuKienGiaoHang = duKienGiaoHang;
    }

    public void setNgayDatHang(Date ngayDatHang) {
        NgayDatHang = ngayDatHang;
    }

    private String TenNguoiMua, SDT, MaDC, MaDH, MaKM, PhuongThucTT, TrangThai, MaND;
    private int GiamGia, PhiVanChuyen, TamTinh, TongTien;
    private Date DuKienGiaoHang;
    private Date NgayDatHang;

    public void setMaND(String maND) {
        this.MaND = maND;
    }

    public String getMaND() {
        return MaND;
    }

    public int getGiamGia() {
        return GiamGia;
    }

    public int getPhiVanChuyen() {
        return PhiVanChuyen;
    }

    public int getTamTinh() {
        return TamTinh;
    }

    public int getTongTien() {
        return TongTien;
    }

    public String getMaDC() {
        return MaDC;
    }

    public String getMaDH() {
        return MaDH;
    }

    public String getMaKM() {
        return MaKM;
    }

    public String getPhuongThucTT() {
        return PhuongThucTT;
    }

    public String getSDT() {
        return SDT;
    }

    public String getTenNguoiMua() {
        return TenNguoiMua;
    }

    public String getTrangThai() {
        return TrangThai;
    }

    public Date getDuKienGiaoHang() {
        return DuKienGiaoHang;
    }

    public Date getNgayDatHang() {
        return NgayDatHang;
    }


}
