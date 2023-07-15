package com.example.shoppingapp.StaffView.MyOrder;

import java.util.List;

public class Order {
    public String AnhDaiDien, DiaChi,HoTen,ID,IdOrder,SoDT,Status;
    public List<ItemOrder> itemOrderList;

    public void setStatus(String status) {
        Status = status;
    }

    public String getStatus() {
        return Status;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getID() {
        return ID;
    }

    public void setHoTen(String hoTen) {
        HoTen = hoTen;
    }

    public String getHoTen() {
        return HoTen;
    }

    public List<ItemOrder> getItemOrderList() {
        return itemOrderList;
    }

    public void setIdOrder(String idOrder) {
        IdOrder = idOrder;
    }

    public String getAnhDaiDien() {
        return AnhDaiDien;
    }

    public String getIdOrder() {
        return IdOrder;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public String getSoDT() {
        return SoDT;
    }

    public void setAnhDaiDien(String anhDaiDien) {
        AnhDaiDien = anhDaiDien;
    }

    public void setDiaChi(String diaChi) {
        DiaChi = diaChi;
    }

    public void setItemOrderList(List<ItemOrder> itemOrderList) {
        this.itemOrderList = itemOrderList;
    }

    public void setSoDT(String soDT) {
        SoDT = soDT;
    }
}
