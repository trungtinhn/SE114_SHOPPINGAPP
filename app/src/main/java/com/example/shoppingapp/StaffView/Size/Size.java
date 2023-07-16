package com.example.shoppingapp.StaffView.Size;

public class Size {
    private String sizeName;

    private boolean checked;
    private String MaSize;


    public Size(String sizeName, String MaSize, boolean checked) {
        this.sizeName = sizeName;
        this.MaSize = MaSize;
        this.checked= checked;
    }

    public String getSizeName() {
        return sizeName;
    }

    public String getMaSize() {
        return MaSize;
    }

    public void setMaSize(String maSize) {
        MaSize = maSize;
    }

    public void setSizeName(String sizeName) {
        this.sizeName = sizeName;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
