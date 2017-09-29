package com.example.hxl.travel.model.bean;

import java.util.List;

/**
 * Created by hxl on 2017/6/21 at haiChou.
 */
public class GroupMember {
    private String type;
    private List<GroupMembers> group_members;

    public GroupMember(String type, List<GroupMembers> group_members) {
        this.type = type;
        this.group_members = group_members;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<GroupMembers> getGroup_members() {
        return group_members;
    }

    public void setGroup_members(List<GroupMembers> group_members) {
        this.group_members = group_members;
    }
}
