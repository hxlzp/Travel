package com.example.hxl.travel.model.bean;

/**
 * Created by hxl on 2017/6/8 at haiChou.
 */
public class RecommendList {
    private int imgUrl;
    private String title;
    private String content;

    public RecommendList(int imgUrl, String title,String content) {
        this.imgUrl = imgUrl;
        this.title = title;
        this.content= content;
    }

    public int getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(int imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
