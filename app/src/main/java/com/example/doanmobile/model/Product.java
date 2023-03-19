package com.example.doanmobile.model;

import android.widget.ImageView;

public class Product {
    private String name;
    private int img;
    private int price;

    public Product(String name, int img, int price) {
        this.name = name;
        this.img = img;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getImg() {
        return img;
    }

    public int getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
