package com.example.hxl.travel.presenter.contract;

import android.content.Context;

import com.example.hxl.travel.base.BasePresenter;
import com.example.hxl.travel.base.BaseView;

/**
 * Created by hxl on 2017/1/4 at haiChou.
 */
public interface LoginContract {
    interface View extends BaseView<Presenter> {
        //view是否销毁
        boolean isActive();
        //该操作需要什么？---获得用户账号、获得用户密码
        String getUserAccount();
        String getUserPassword();
        Context getLoginContext();
        //该操作的结果对应的反馈是什么？---登陆成功，返回页面、登陆失败提示
        void backPage();
        void showLoginMassage(String message);
        void showUserMessage(String phone);
        //该操作的结果对应的用户友好反馈是什么？---登陆请求网络时，显示进度条，隐藏进度条
        void showProgress();
        void hideProgress();
    }
    interface Presenter extends BasePresenter{
        //观察功能上有什么操作? --- 登陆
        void login();
    }
}
