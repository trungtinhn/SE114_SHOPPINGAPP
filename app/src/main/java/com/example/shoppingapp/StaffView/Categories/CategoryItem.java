package com.example.shoppingapp.StaffView.Categories;

public class CategoryItem {
    private String name;
    private String image;
    private int quantity;
    private String MaDM;
    private boolean isSelected;

    public CategoryItem(String name, boolean isSelected, String MaDM, int quantity)
    {
        this.name = name;
        this.isSelected = isSelected;
        this.MaDM = MaDM;
        this.quantity = quantity;
    }
    public CategoryItem(String name, String image, int quantity) {
        this.name = name;
        this.image = image;
        this.quantity = quantity;
    }
    public CategoryItem (String name, String image, int quantity , boolean isSelected) {
        this.name = name;
        this.image = image;
        this.quantity = quantity;
        this.isSelected = isSelected;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public int getQuantity() {
        return quantity;
    }
    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getMaDM() {
        return MaDM;
    }

    public void setMaDM(String maDM) {
        MaDM = maDM;
    }
}


