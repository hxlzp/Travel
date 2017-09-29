package com.example.hxl.travel.presenter.contract;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.example.hxl.travel.base.BasePresenter;
import com.example.hxl.travel.base.BaseView;

import java.util.List;

/**
 * Created by hxl on 2017/8/10 0010 at haichou.
 */

public interface FriendsCircleContract {
    interface View extends BaseView<Presenter> {
        /**
         * 1.该操作需要什么 -> showDada() 显示数据
         * 2.该操作过程中对应的反馈是什么
         * 3.该操作过程中对应的友好交互是什么
         */
        boolean isActive();
        Context getFriendContext();
        /*该操作需要什么*/
        String getUserName();
        /*sessionId*/
        String getSessionId();
        String getUserId();
        void setSessionId(String sessionId);
        //viewPage
        void showViewPager(List<Fragment> fragments, List<String> tab,
                           FragmentManager childFragmentManager);

        void showData();
        /*该操作过程中对应的友好交互*/
        void showProgress();
        void hideProgress();
        /*该操作的结果对应的反馈是什么*/
        void showFriendMessage(String message);
    }
    interface Presenter extends BasePresenter {
        void getData();
    }
}
