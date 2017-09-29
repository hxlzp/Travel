package com.example.hxl.travel.model.bean;

/**
 * Created by hxl on 2017/6/8 at haiChou.
 */
public class GroupMessage {
    private int image;
    private String name;
    private String message;
    private String time;

    public GroupMessage() {
    }

    public GroupMessage(int image, String name, String message, String time) {
        this.image = image;
        this.name = name;
        this.message = message;
        this.time = time;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
