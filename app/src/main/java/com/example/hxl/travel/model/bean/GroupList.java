package com.example.hxl.travel.model.bean;

import java.util.List;

/**
 * Created by hxl on 2017/6/21 at haiChou.
 */
public class GroupList {
    //状态：type  1 成功，返回群组信息  visitorGroups   2  失败，没有参与任何群组
    private String type;
    private List<VisitorGroups> visitorGroups;

    public GroupList(String type, List<VisitorGroups> visitorGroups) {
        this.type = type;
        this.visitorGroups = visitorGroups;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<VisitorGroups> getVisitorGroups() {
        return visitorGroups;
    }

    public void setVisitorGroups(List<VisitorGroups> visitorGroups) {
        this.visitorGroups = visitorGroups;
    }
}
