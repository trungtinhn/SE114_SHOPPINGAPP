package com.example.shoppingapp.StaffView.item;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class admin_object implements Serializable {

    @Exclude
            private String key;

    String name,ID, status, sex, dob, email, pass, phoneNum;
    public String getName() {
        return name;
    }

    public int getSexIndex()
    {
        if(this.sex == "Male") return 0;
        else return 1;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getSex() {
        return sex;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public admin_object(String name, String ID, String status,
                        String sex, String dob, String email, String pass, String phoneNum) {
        this.name = name;
        this.ID = ID;
        this.status = status;
        this.sex = sex;
        this.dob = dob;
        this.email = email;
        this.pass = pass;
        this.phoneNum = phoneNum;
    }

    public admin_object() {
    }

    public String getStatus() {
        return status;
    }

    public int getStatusIndex() {
        if(this.status == "Working") return 0;
        else if(this.status == "On vacation") return 1;
        else if(this.status == "Absent") return 3;
        else return 0;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
