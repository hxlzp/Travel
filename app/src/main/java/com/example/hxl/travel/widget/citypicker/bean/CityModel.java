package com.example.hxl.travel.widget.citypicker.bean;

import java.util.List;

/**
 * Created by hxl on 2017/1/13 at haiChou.
 * 城市
 */
public class CityModel {
    private String name;
    private List<DistrictModel> districtList;

    public CityModel(String name, List<DistrictModel> districtList) {
        this.name = name;
        this.districtList = districtList;
    }

    public CityModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DistrictModel> getDistrictList() {
        return districtList;
    }

    public void setDistrictList(List<DistrictModel> districtList) {
        this.districtList = districtList;
    }
}
