package com.example.shoppingapp.StaffView.Color;

public class Color {
    private String colorName;
    private String MaMau;
    private String colorCode;
    private Boolean checked;

    public Color(String colorName, String colorCode, String MaMau, Boolean checked) {
        this.colorName = colorName;
        this.colorCode = colorCode;
        this.MaMau = MaMau;
        this.checked = checked;
    }
    public Color(String colorName, String colorCode, String MaMau) {
        this.colorName = colorName;
        this.colorCode = colorCode;
        this.MaMau = MaMau;
    }
    public Color(String colorName)
    {
        this.colorName = colorName;
    }

    public String getColorName() {
        return colorName;
    }

    public String getColorCode() {
        return colorCode;
    }

    public String getMaMau() {
        return MaMau;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public void setMaMau(String maMau) {
        MaMau = maMau;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Boolean getChecked() {
        return checked;
    }
}
