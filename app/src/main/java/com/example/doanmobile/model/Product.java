package com.example.doanmobile.model;


import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Product {
    private String id;
    private String name;
    private String description;
    private String img;
    private int price;
    private String type;
    private int quantity;

    public Product(String id, String name, String description, String img, int price, String type, int quantity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.img = img;
        this.price = price;
        this.type = type;
        this.quantity = quantity;
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();

        result.put("id", id);
        result.put("name", name);
        result.put("description", description);
        result.put("img", img);
        result.put("price", price);
        result.put("type", type);
        result.put("quantity", quantity);

        return result;
    }

    public Product() {}

    public void setId(String id) {
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

    public String getId() {
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
