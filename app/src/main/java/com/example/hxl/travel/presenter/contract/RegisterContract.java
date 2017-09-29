package com.example.hxl.travel.presenter.contract;

import android.content.Context;

import com.example.hxl.travel.base.BasePresenter;
import com.example.hxl.travel.base.BaseView;

/**
 * Created by hxl on 2017/1/4 at haiChou.
 */
public interface RegisterContract {
    interface View extends BaseView<Presenter>{
        //view是否销毁
        boolean isActive();
        Context getLoginContext();
        //该操作需要什么？--- 获得昵称、手机号、验证码、密码
        String getUserName();
        String getUserPhone();
        String getUserCode();
        String getUserPassword();
        //该操作的结果对应的反馈是什么？--- 注册成功返回页面，注册失败提示用户
        void backPage();
        void showRegisterMassage(String massage);
        //该操作过程中友好反馈是什么？---显示进度条、隐藏进度条
        void showProgress();
        void hideProgress();
    }
    interface Presenter extends BasePresenter{
        //该操作主要的功能是:注册
        void register();
    }
}
