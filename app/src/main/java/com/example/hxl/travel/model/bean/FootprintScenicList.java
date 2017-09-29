package com.example.hxl.travel.model.bean;

/**
 * Created by hxl on 2017/8/18 0018 at haichou.
 */

public class FootprintScenicList {
    private String title;
    private String stayTime;
    private String playTime;
    private String playData;
    private String consumeMoney;
    private String iconUrl;
    private int icon;

    public FootprintScenicList() {

    }

    public FootprintScenicList(String title, String stayTime, String playTime,String playData,
                      String consumeMoney) {
        this.title = title;
        this.stayTime = stayTime;
        this.playTime = playTime;
        this.playData = playData;
        this.consumeMoney = consumeMoney;
    }

    public FootprintScenicList(String title, String stayTime, String playTime, String playData,
                               String consumeMoney, String iconUrl) {
        this.title = title;
        this.stayTime = stayTime;
        this.playTime = playTime;
        this.playData = playData;
        this.consumeMoney = consumeMoney;
        this.iconUrl = iconUrl;
    }

    public FootprintScenicList(String title, String stayTime, String playTime, String playData,
                               String consumeMoney, int icon) {
        this.title = title;
        this.stayTime = stayTime;
        this.playTime = playTime;
        this.playData = playData;
        this.consumeMoney = consumeMoney;
        this.icon = icon;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public void setPlayData(String playData) {
        this.playData = playData;
    }

    public String getPlayData() {
        return playData;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStayTime() {
        return stayTime;
    }

    public void setStayTime(String stayTime) {
        this.stayTime = stayTime;
    }


    public String getPlayTime() {
        return playTime;
    }

    public void setPlayTime(String playTime) {
        this.playTime = playTime;
    }


    public String getConsumeMoney() {
        return consumeMoney;
    }

    public void setConsumeMoney(String consumeMoney) {
        this.consumeMoney = consumeMoney;
    }
}
