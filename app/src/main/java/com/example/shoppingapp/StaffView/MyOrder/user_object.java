package com.example.shoppingapp.StaffView.MyOrder;

public class user_object {
    private String name;
    private String ID;
    private double moneytotal;
    private int ava;

    public int getAva() {
        return ava;
    }

    public void setAva(int ava) {
        this.ava = ava;
    }

    public user_object(String name, String ID, double moneytotal, int ava) {
        this.name = name;
        this.ID = ID;
        this.moneytotal = moneytotal;
        this.ava = ava;
    }

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

    public double getMoneytotal() {
        return moneytotal;
    }

    public void setMoneytotal(double moneytotal) {
        this.moneytotal = moneytotal;
    }
}
