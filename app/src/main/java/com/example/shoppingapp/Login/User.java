package com.example.shoppingapp.Login;

public class User {
    private String fullName;
    private String status;
    private String email;
    private String phoneNumber;
    private String dayOfBirth;

    private String MaND;
    private  String avatar, diachi, gioitinh;
    private String loaiND;
    // Các trường khác mà bạn muốn lưu trong Firestore

    // Tạo constructor và getter/setter
    public User(){

    }
    public User(String fullname, String email, String dayOfBirth, String phoneNumber,
                String MaND, String avatar, String diachi, String gioitinh, String Status, String loaiND)
    {
        this.fullName = fullname;
        this.email = email;
        this.dayOfBirth = dayOfBirth;
        this.phoneNumber = phoneNumber;
        this.MaND = MaND;
        this.avatar = avatar;
        this.diachi = diachi;
        this.gioitinh = gioitinh;
        this.status = Status;
        this.loaiND = loaiND;
    }

    public String getLoaiND() {
        return loaiND;
    }

    public void setLoaiND(String loaiND) {
        this.loaiND = loaiND;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDayOfBirth() {
        return dayOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    public String getMaND() {
        return MaND;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getDiachi() {
        return diachi;
    }

    public String getGioitinh() {
        return gioitinh;
    }


    public void setDayOfBirth(String dayOfBirth) {
        this.dayOfBirth = dayOfBirth;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setMaND(String maND) {
        MaND = maND;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public void setGioitinh(String gioitinh) {
        this.gioitinh = gioitinh;
    }

}