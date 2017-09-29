package com.example.hxl.travel.presenter.contract;

import android.content.Context;

import com.example.hxl.travel.base.BasePresenter;
import com.example.hxl.travel.base.BaseView;
import com.example.hxl.travel.model.bean.ScannerGroupMembers;

import java.util.List;

/**
 * Created by hxl on 2017/6/13 at haiChou.
 */
public interface ScannerContract {
    interface View extends BaseView<Presenter>{
        /**
         * 1.该界面需要什么
         * 2.该界面的结果对应的反馈是什么
         * 3.该界面对应的友好交互
         */
        boolean isActive();
        void showData();
        void showProgress();
        void hideProgress();
        Context getScannerContext();
        void showMessage(String message);
        void showScannerMessage(List<ScannerGroupMembers> group_members);
        String getGroupName();
        /*sessionId*/
        String getSessionId();
        String getUserId();
        void setSessionId(String sessionId);
        void back();

    }
    interface Presenter extends BasePresenter{
        void getData();
        void scanner(String url);
        void confirm();
    }

}
