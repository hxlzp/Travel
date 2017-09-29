package com.example.hxl.travel.model.bean;

/**
 * Created by hxl on 2017/6/20 at haiChou.
 */
public class GroupBuild {
    private String type;
    private String group_nickname;
    private String group_id;

    public GroupBuild(String type, String group_nickname, String group_id) {
        this.type = type;
        this.group_nickname = group_nickname;
        this.group_id = group_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGroup_nickname() {
        return group_nickname;
    }

    public void setGroup_nickname(String group_nickname) {
        this.group_nickname = group_nickname;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }
}
