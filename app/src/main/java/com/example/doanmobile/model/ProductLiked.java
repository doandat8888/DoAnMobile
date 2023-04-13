package com.example.doanmobile.model;

public class ProductLiked {
    String id;
    String name;
    String price;
    String image;
    boolean status;

    public ProductLiked() {
    }

    public ProductLiked(String id, String name, String price, String image, boolean status) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
