package com.example.doanmobile.model;

public class MyNotification {

    private int imageResource;

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    private String notice;
    private String content;

    public MyNotification(int imageResource, String notice, String content) {
        this.imageResource = imageResource;
        this.notice = notice;
        this.content = content;
    }




}

