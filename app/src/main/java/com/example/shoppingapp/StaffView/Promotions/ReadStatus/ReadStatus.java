package com.example.shoppingapp.StaffView.Promotions.ReadStatus;

public class ReadStatus {
    public String getMaND() {
        return maND;
    }

    public void setMaND(String maND) {
        this.maND = maND;
    }

    private String maND;
    private boolean read;
    private String tb;

    public ReadStatus() {
        // Required empty constructor for Firestore
    }

    public ReadStatus(String maND, boolean read, String tb) {
        this.maND = maND;
        this.read = read;
        this.tb = tb;
    }

    // Getter and setter methods (if needed)
}
