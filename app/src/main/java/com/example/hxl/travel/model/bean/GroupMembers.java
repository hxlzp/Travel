package com.example.hxl.travel.model.bean;

/**
 * Created by hxl on 2017/6/21 at haiChou.
 */
public class GroupMembers {
    private int img;//临时本地图片

    private String group_nickname;//群昵称
    private String user_nickname;
    private String group_member_type;//群创建的类型，创建或加入群
    private String user_id;
    private String group_authority;
    private String group_time;

    public GroupMembers(int img, String userNickname) {
        this.img = img;
        this.user_nickname = userNickname;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getGroup_nickname() {
        return group_nickname;
    }

    public void setGroup_nickname(String group_nickname) {
        this.group_nickname = group_nickname;
    }

    public String getUserNickname() {
        return user_nickname;
    }

    public void setUserNickname(String userNickname) {
        this.user_nickname = userNickname;
    }

    public String getGroup_member_type() {
        return group_member_type;
    }

    public void setGroup_member_type(String group_member_type) {
        this.group_member_type = group_member_type;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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
