package com.example.doanmobile.model;

import com.example.doanmobile.adapter.CategoryListAdapter;

public class Category {
    private String name;
    private String img;

    public Category(String name, String img) {
        this.name = name;
        this.img = img;
    }

    public Category() {}

    public void setImg(String img) {
        this.img = img;
    }

    public String getImg() {
        return img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
