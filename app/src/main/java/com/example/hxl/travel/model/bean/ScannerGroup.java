package com.example.hxl.travel.model.bean;

import java.util.List;

/**
 * Created by hxl on 2017/6/27 at haiChou.
 */
public class ScannerGroup {
    private String type;
    private List<ScannerGroupMembers> group_members;

    public ScannerGroup(String type, List<ScannerGroupMembers> group_members) {
        this.type = type;
        this.group_members = group_members;
    }

    public ScannerGroup() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<ScannerGroupMembers> getGroup_members() {
        return group_members;
    }

    public void setGroup_members(List<ScannerGroupMembers> group_members) {
        this.group_members = group_members;
    }
}
