package com.example.hxl.travel.model.bean;

/**
 * Created by hxl on 2017/8/1 at haiChou.
 */
public class MarkerInfo {
    private double latitude;//纬度
    private double longitude;//经度
    private String name;//名字
    private int imgId;//图片
    private String description;//描述

    public MarkerInfo(double latitude, double longitude, String name, int imgId) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.imgId = imgId;
    }

    public MarkerInfo() {
    }

    public MarkerInfo(double latitude, double longitude, String name, int imgId, String description) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.imgId = imgId;
        this.description = description;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
