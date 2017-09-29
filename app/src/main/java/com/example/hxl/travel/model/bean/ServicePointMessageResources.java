package com.example.hxl.travel.model.bean;

/**
 * Created by hxl on 2017/6/29 at haiChou.
 */
public class ServicePointMessageResources {
    private String s_p_resource_thumbnail_url;//": "",
    private String s_p_resource_type;//": 1,
    private String s_p_resource_introduction;//": "这是红绿灯点的文本简介信息",
    private String service_point_id;//": 32,
    private String s_p_resource_id;//": 55,
    private String s_p_resource_url;//": ""

    public ServicePointMessageResources(String s_p_resource_thumbnail_url,
                                        String s_p_resource_type, String s_p_resource_introduction,
                                        String service_point_id, String s_p_resource_id,
                                        String s_p_resource_url) {
        this.s_p_resource_thumbnail_url = s_p_resource_thumbnail_url;
        this.s_p_resource_type = s_p_resource_type;
        this.s_p_resource_introduction = s_p_resource_introduction;
        this.service_point_id = service_point_id;
        this.s_p_resource_id = s_p_resource_id;
        this.s_p_resource_url = s_p_resource_url;
    }

    public String getS_p_resource_thumbnail_url() {
        return s_p_resource_thumbnail_url;
    }

    public void setS_p_resource_thumbnail_url(String s_p_resource_thumbnail_url) {
        this.s_p_resource_thumbnail_url = s_p_resource_thumbnail_url;
    }

    public String getS_p_resource_type() {
        return s_p_resource_type;
    }

    public void setS_p_resource_type(String s_p_resource_type) {
        this.s_p_resource_type = s_p_resource_type;
    }

    public String getS_p_resource_introduction() {
        return s_p_resource_introduction;
    }

    public void setS_p_resource_introduction(String s_p_resource_introduction) {
        this.s_p_resource_introduction = s_p_resource_introduction;
    }

    public String getService_point_id() {
        return service_point_id;
    }

    public void setService_point_id(String service_point_id) {
        this.service_point_id = service_point_id;
    }

    public String getS_p_resource_id() {
        return s_p_resource_id;
    }

    public void setS_p_resource_id(String s_p_resource_id) {
        this.s_p_resource_id = s_p_resource_id;
    }

    public String getS_p_resource_url() {
        return s_p_resource_url;
    }

    public void setS_p_resource_url(String s_p_resource_url) {
        this.s_p_resource_url = s_p_resource_url;
    }
}
