package com.example.hxl.travel.model.bean;

/**
 * Created by hxl on 2017/6/9 at haiChou.
 */
public class Register {
    private String type ;
    private String count ;
    private String sessionId;
    private String nickname;

    public Register(String type, String sessionId) {
        this.type = type;
        this.sessionId = sessionId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
