package com.example.hxl.travel.presenter.contract;

import android.content.Context;

import com.example.hxl.travel.base.BasePresenter;
import com.example.hxl.travel.base.BaseView;
import com.example.hxl.travel.model.bean.GroupMembers;

import java.util.List;

/**
 * Created by hxl on 2017/8/22 0022 at haichou.
 */

public interface GroupDetailContract {
    interface View extends BaseView<Presenter> {
        boolean isActive();
        Context getGroupDetailContext();
        void showProgress();
        void hideProgress();

        /*弹窗退出*/
        void dismissPopupWindow();
        String getGroupId();
        String getUserId();
        String getGroupMemberType();

        void showData(List<GroupMembers> groupMemberses );

        void showGroupDetailMessage(String massage);
        /*退出对话框*/
        void dismissDialog();
        /*获得用户录入的用户名*/
        String getUserName();
        void goBack();


    }

    interface Presenter extends BasePresenter{
        void getData();
        /*解散群组*/
        void deleteGroup();
        /*退出群组*/
        void quitGroup();
        /*确定邀请*/
        void confirmInvite();
    }
}
