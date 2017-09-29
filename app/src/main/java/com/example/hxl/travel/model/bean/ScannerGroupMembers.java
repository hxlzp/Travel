package com.example.hxl.travel.model.bean;

/**
 * Created by hxl on 2017/6/27 at haiChou.
 */
public class ScannerGroupMembers {
    private String group_nickname;//": "haichousj-88",
    private String group_member_type;//": 1,
    private String group_id;//": 149845508319953,
    private String user_id;//": 50,
    private String userNickname;//": "jay_01",
    private String group_authority;//": 0,
    private String group_time;//": 1498461487805

    public ScannerGroupMembers(String group_nickname, String group_member_type, String group_id,
                               String user_id, String userNickname, String group_authority,
                               String group_time) {
        this.group_nickname = group_nickname;
        this.group_member_type = group_member_type;
        this.group_id = group_id;
        this.user_id = user_id;
        this.userNickname = userNickname;
        this.group_authority = group_authority;
        this.group_time = group_time;
    }

    public ScannerGroupMembers() {
    }

    public ScannerGroupMembers(String group_nickname) {
        this.group_nickname = group_nickname;
    }

    public String getGroup_nickname() {
        return group_nickname;
    }

    public void setGroup_nickname(String group_nickname) {
        this.group_nickname = group_nickname;
    }

    public String getGroup_member_type() {
        return group_member_type;
    }

    public void setGroup_member_type(String group_member_type) {
        this.group_member_type = group_member_type;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public String getGroup_authority() {
        return group_authority;
    }

    public void setGroup_authority(String group_authority) {
        this.group_authority = group_authority;
    }

    public String getGroup_time() {
        return group_time;
    }

    public void setGroup_time(String group_time) {
        this.group_time = group_time;
    }
}
