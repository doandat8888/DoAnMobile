package com.example.doanmobile.model;


public class Product {
    private int id;
    private String name;
    private String description;
    private String img;
    private int price;
    private String type;
    private int quantity;

    public Product(int id, String name, String description, String img, int price, String type, int quantity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.img = img;
        this.price = price;
        this.type = type;
        this.quantity = quantity;
    }

    public Product() {}

    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getName() {
        return name;
    }

    public String getImg() {
        return img;
    }

    public int getPrice() {
        return price;
    }


}
