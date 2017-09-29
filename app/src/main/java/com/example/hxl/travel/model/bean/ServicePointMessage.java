package com.example.hxl.travel.model.bean;

import java.util.List;

/**
 * Created by hxl on 2017/6/29 at haiChou.
 */
public class ServicePointMessage {
    private String type;
    private List<ServicePointMessageResources> spResources;

    public ServicePointMessage(String type, List<ServicePointMessageResources> spResources) {
        this.type = type;
        this.spResources = spResources;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<ServicePointMessageResources> getSpResources() {
        return spResources;
    }

    public void setSpResources(List<ServicePointMessageResources> spResources) {
        this.spResources = spResources;
    }
}
