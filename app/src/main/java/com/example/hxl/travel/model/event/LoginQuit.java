package com.example.hxl.travel.model.event;

/**
 * Created by hxl on 2017/6/19 at haiChou.
 */
public class LoginQuit {
    private String userName;
    private String usePhone;

    public LoginQuit(String userName, String usePhone) {
        this.userName = userName;
        this.usePhone = usePhone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUsePhone() {
        return usePhone;
    }

    public void setUsePhone(String usePhone) {
        this.usePhone = usePhone;
    }
}
