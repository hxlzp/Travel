package com.example.hxl.travel.presenter.contract;

import android.content.Context;

import com.example.hxl.travel.base.BasePresenter;
import com.example.hxl.travel.base.BaseView;
import com.example.hxl.travel.model.bean.VisitorGroups;

import java.util.List;

/**
 * Created by hxl on 2017/6/9 at haiChou.
 * 群组
 */
public interface GroupContract {
    interface View extends BaseView<Presenter> {
        /**
         * 1.该操作需要什么
         * 2.该操作过程中友好交互什么
         * 3.该操作过程中对应的反馈是什么
         */
        boolean isActive();
        //静态页面
        //void showData(List<Group> groups);
        //动态页面
        void showData(List<VisitorGroups> groups);
        void showProgress();
        void hideProgress();
        Context getGroupContext();
        void showGroupMessage(String message);
        String getSessionId();
        String getUserId();
    }
    interface Presenter extends BasePresenter {
        void getData();
        void dissolveGroup(String groupId);
        void quitGroup(String userId,String groupId);
    }
}
