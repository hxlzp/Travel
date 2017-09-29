package com.example.hxl.travel.model.bean;

/**
 * Created by hxl on 2017/8/14 0014 at haichou.
 */

public class ScenicList {
    private int img;
    private int imgChecked;
    private String imgUrl;
    private String title;

    public ScenicList(int img, String title) {
        this.img = img;
        this.title = title;
    }

    public ScenicList(int img, int imgChecked, String title) {
        this.img = img;
        this.imgChecked = imgChecked;
        this.title = title;
    }

    public ScenicList(String imgUrl, String title) {
        this.imgUrl = imgUrl;
        this.title = title;
    }

    public void setImgChecked(int imgChecked) {
        this.imgChecked = imgChecked;
    }

    public int getImgChecked() {
        return imgChecked;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
