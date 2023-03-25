package com.example.doanmobile.model;

public class Account {
    private String name;
    private String password;
    private String phoneNumber;
    private String username;
    private String img;

    public Account() {}

    public Account(String name, String password, String phoneNumber, String username, String img) {
        this.name = name;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.img = img;
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
