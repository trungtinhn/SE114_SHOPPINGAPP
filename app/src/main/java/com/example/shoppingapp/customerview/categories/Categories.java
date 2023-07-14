package com.example.shoppingapp.customerview.categories;

import android.graphics.drawable.Drawable;

public class Categories {
    private String name;
    public Categories(String name, String image) {
        this.name = name;
        this.image = image;

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
}
