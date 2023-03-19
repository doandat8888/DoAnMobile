package com.example.doanmobile.model;

public class Account {
    private int id;
    private String name;
    private String password;
    private String phoneNumber;
    private String username;
    private String img;

    public Account(int id, String name, String password, String phoneNumber, String username, String img) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.img = img;
    }

    public Account() {}

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public String getImg() {
        return img;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
