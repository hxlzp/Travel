package com.example.hxl.travel.presenter.contract;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.example.hxl.travel.base.BasePresenter;
import com.example.hxl.travel.base.BaseView;

import java.util.List;

/**
 * Created by hxl on 2017/6/8 at haiChou.
 * 旅行圈
 */
public interface FriendContract {
    interface View extends BaseView<Presenter> {
        /**
         * 1.该操作需要什么 -> showDada() 显示数据
         * 2.该操作过程中对应的反馈是什么
         * 3.该操作过程中对应的友好交互是什么
         */
        boolean isActive();
        Context getFriendContext();
        /*该操作需要什么*/
        //获得群昵称
        String getGroupNickName();
        //获得群昵称和用户名
        String getGroupInviteNickName();
        String getUserName();
        /*sessionId*/
        String getSessionId();
        String getUserId();
        void setSessionId(String sessionId);
        //viewPage
        void showViewPager(List<Fragment> fragments,List<String> tab,
                           FragmentManager childFragmentManager);
        void showData();
        /*该操作过程中对应的友好交互*/
        void showProgress();
        void hideProgress();
        /*该操作的结果对应的反馈是什么*/
        void showFriendMessage(String message);
        //添加群组对话框退出
        void dismissAddDialog();
    }
    interface Presenter extends BasePresenter {
        void getData();
        /*添加群*/
        void addGroup();
    }
}
