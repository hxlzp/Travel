package com.example.hxl.travel.model.event;

/**
 * Created by hxl on 2017/6/19 at haiChou.
 */
public class LoginEvent {
    private String userPhone;
    private String userName;
    private String sessionId;
    private String user_id;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public LoginEvent(String userPhone, String userName, String sessionId, String user_id) {
        this.userPhone = userPhone;
        this.userName = userName;
        this.sessionId = sessionId;
        this.user_id = user_id;
    }

    public LoginEvent(String userPhone, String userName) {
        this.userPhone = userPhone;
        this.userName = userName;
    }

    public LoginEvent(String userPhone, String userName, String sessionId) {
        this.userPhone = userPhone;
        this.userName = userName;
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
