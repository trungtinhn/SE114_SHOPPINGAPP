package com.example.shoppingapp.customerview.notification;

public class Notification {

    private String name;
    private String content;
    private int resourceAvt;

    public Notification() {
    }

    public Notification(String name, String content, int resourceAvt) {
        this.name = name;
        this.content = content;
        this.resourceAvt = resourceAvt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getResourceAvt() {
        return resourceAvt;
    }

    public void setResourceAvt(int resourceAvt) {
        this.resourceAvt = resourceAvt;
    }
}
