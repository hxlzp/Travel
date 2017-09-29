package com.example.hxl.travel.widget.citypicker.bean;

import java.util.List;

/**
 * Created by hxl on 2017/1/13 at haiChou.
 * 省
 */
public class ProvinceModel {
    private String name;
    private List<CityModel> cityList;

    public ProvinceModel(String name, List<CityModel> cityList) {
        this.name = name;
        this.cityList = cityList;
    }

    public ProvinceModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CityModel> getCityList() {
        return cityList;
    }

    public void setCityList(List<CityModel> cityList) {
        this.cityList = cityList;
    }
}
