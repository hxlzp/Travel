package com.example.hxl.travel.model.bean;

/**
 * Created by hxl on 2017/6/8 at haiChou.
 */
public class Group {
    private int image;
    private String name;
    private String id;

    public Group(int image, String name) {
        this.image = image;
        this.name = name;
    }

    public Group(int image, String name, String id) {
        this.image = image;
        this.name = name;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}
