package com.example.hxl.travel.model.bean;

/**
 * Created by hxl on 2017/8/10 0010 at haichou.
 */

public class ImageRecommend {
    private int icons;
    private String names;

    public ImageRecommend(int icons,String names) {
        this.icons = icons;
        this.names = names;
    }

    public void setIcons(int icons) {
        this.icons = icons;
    }

    public int getIcons() {
        return icons;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }
}
