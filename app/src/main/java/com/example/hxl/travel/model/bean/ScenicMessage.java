package com.example.hxl.travel.model.bean;

import java.util.List;

/**
 * Created by hxl on 2017/6/9 at haiChou.
 */
public class ScenicMessage {
    private List<ServicePoints> servicePoints;
    private String type;
    private Scenic scenic;

    public List<ServicePoints> getServicePoints() {
        return servicePoints;
    }

    public void setServicePoints(List<ServicePoints> servicePoints) {
        this.servicePoints = servicePoints;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Scenic getScenic() {
        return scenic;
    }

    public void setScenic(Scenic scenic) {
        this.scenic = scenic;
    }
}
