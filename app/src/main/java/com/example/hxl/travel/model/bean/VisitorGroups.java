package com.example.hxl.travel.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hxl on 2017/6/21 at haiChou.
 */
public class VisitorGroups implements Parcelable{
    /**
     * "group_nickname": "好",
     * "group_member_type": 2,群主；1成员
     * "group_id": 149795052127948,
     * "user_id": 48,
     * "group_authority": 3,
     * "group_time": 1497950521279
     */
    private String group_nickname;
    private String group_member_type;
    private String group_id;
    private String user_id;
    private String group_authority;
    private String group_time;
    private String queryMemberURL;

    public VisitorGroups() {
    }

    public VisitorGroups(String group_nickname, String group_member_type, String group_id,
                         String user_id, String queryMemberURL) {
        this.group_nickname = group_nickname;
        this.group_member_type = group_member_type;
        this.group_id = group_id;
        this.user_id = user_id;
        this.queryMemberURL = queryMemberURL;
    }

    public VisitorGroups(String group_nickname) {
        this.group_nickname = group_nickname;
    }

    public VisitorGroups(String group_nickname, String group_member_type, String group_id,
                         String user_id, String group_authority, String group_time) {
        this.group_nickname = group_nickname;
        this.group_member_type = group_member_type;
        this.group_id = group_id;
        this.user_id = user_id;
        this.group_authority = group_authority;
        this.group_time = group_time;
    }

    protected VisitorGroups(Parcel in) {
        group_nickname = in.readString();
        group_member_type = in.readString();
        group_id = in.readString();
        user_id = in.readString();
        group_authority = in.readString();
        group_time = in.readString();
        queryMemberURL = in.readString();
    }

    public static final Creator<VisitorGroups> CREATOR = new Creator<VisitorGroups>() {
        @Override
        public VisitorGroups createFromParcel(Parcel in) {
            return new VisitorGroups(in);
        }

        @Override
        public VisitorGroups[] newArray(int size) {
            return new VisitorGroups[size];
        }
    };

    public String getQueryMemberURL() {
        return queryMemberURL;
    }

    public void setQueryMemberURL(String queryMemberURL) {
        this.queryMemberURL = queryMemberURL;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(group_nickname);
        dest.writeString(group_member_type);
        dest.writeString(group_id);
        dest.writeString(user_id);
        dest.writeString(group_authority);
        dest.writeString(group_time);
        dest.writeString(queryMemberURL);
    }
}
