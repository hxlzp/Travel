package com.example.hxl.travel.model.bean;

/**
 * Created by hxl on 2017/6/9 at haiChou.
 */
public class MemberGroup {
    private int img;
    private String name;

    public MemberGroup(int img, String name) {
        this.img = img;
        this.name = name;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
