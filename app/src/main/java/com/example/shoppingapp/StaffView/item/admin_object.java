package com.example.shoppingapp.StaffView.item;

public class admin_object {

    String name,ID, status, ava;
    public String getName() {
        return name;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAva() {
        return ava;
    }

    public void setAva(String ava) {
        this.ava = ava;
    }

    public admin_object(String name, String ID, String status, String ava) {
        this.name = name;
        this.ID = ID;
        this.status = status;
        this.ava = ava;
    }
}
