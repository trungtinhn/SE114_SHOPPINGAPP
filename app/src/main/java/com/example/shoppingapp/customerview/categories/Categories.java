package com.example.shoppingapp.customerview.categories;

import android.graphics.drawable.Drawable;

public class Categories {
    private String MaDM;
    private String name;
    public Categories(String name, String image, String MaDM) {
        this.name = name;
        this.image = image;
        this.MaDM = MaDM;

    }
    private String image;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMaDM() {
        return MaDM;
    }

    public void setMaDM(String maDM) {
        MaDM = maDM;
    }
}
