package com.example.hxl.travel.model.bean;

import com.example.hxl.travel.utils.Cn2Spell;

/**
 * Created by hxl on 2017/8/10 0010 at haichou.
 */

public class User implements Comparable<User> {

    private String name; // 姓名
    private String pinyin; // 姓名对应的拼音
    private String firstLetter; // 拼音的首字母
    private int icon;
    private String iconUrl;
    private String phoneNumber;

    public User() {
    }

    public User(String name) {
        this.name = name;
        pinyin = Cn2Spell.getPinYin(name); // 根据姓名获取拼音
        firstLetter = pinyin.substring(0, 1).toUpperCase(); // 获取拼音首字母并转成大写
        if (!firstLetter.matches("[A-Z]")) { // 如果不在A-Z中则默认为“#”
            firstLetter = "#";
        }
    }

    public User(String name, int icon) {
        this.name = name;
        this.icon = icon;
        pinyin = Cn2Spell.getPinYin(name); // 根据姓名获取拼音
        firstLetter = pinyin.substring(0, 1).toUpperCase(); // 获取拼音首字母并转成大写
        if (!firstLetter.matches("[A-Z]")) { // 如果不在A-Z中则默认为“#”
            firstLetter = "#";
        }
    }

    public User(String name, String iconUrl) {
        this.name = name;
        this.iconUrl = iconUrl;
        pinyin = Cn2Spell.getPinYin(name); // 根据姓名获取拼音
        firstLetter = pinyin.substring(0, 1).toUpperCase(); // 获取拼音首字母并转成大写
        if (!firstLetter.matches("[A-Z]")) { // 如果不在A-Z中则默认为“#”
            firstLetter = "#";
        }
    }
    public User(String name, String phoneNumber,int icon){
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.icon = icon;
        pinyin = Cn2Spell.getPinYin(name); // 根据姓名获取拼音
        firstLetter = pinyin.substring(0, 1).toUpperCase(); // 获取拼音首字母并转成大写
        if (!firstLetter.matches("[A-Z]")) { // 如果不在A-Z中则默认为“#”
            firstLetter = "#";
        }
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getName() {
        return name;
    }

    public String getPinyin() {
        return pinyin;
    }

    public String getFirstLetter() {
        return firstLetter;
    }

    public int getIcon() {
        return icon;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    @Override
    public int compareTo(User another) {
        if (firstLetter.equals("#") && !another.getFirstLetter().equals("#")) {
            return 1;
        } else if (!firstLetter.equals("#") && another.getFirstLetter().equals("#")){
            return -1;
        } else {
            return pinyin.compareToIgnoreCase(another.getPinyin());
        }
    }
}
