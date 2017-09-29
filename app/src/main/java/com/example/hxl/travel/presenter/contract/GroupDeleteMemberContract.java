package com.example.hxl.travel.presenter.contract;

import android.content.Context;

import com.example.hxl.travel.base.BasePresenter;
import com.example.hxl.travel.base.BaseView;
import com.example.hxl.travel.model.bean.GroupMembers;

import java.util.List;

/**
 * Created by hxl on 2017/6/9 at haiChou.
 * 删除群成员
 */
public interface GroupDeleteMemberContract {
    interface View extends BaseView<Presenter> {
        boolean isActive();
        void showData(List<GroupMembers> datas);
        void showProgress();
        void hideProgress();
        Context getGroupDeleteMemberContext();
        String getGroupId();
        void showGroupDeleteMessage(String message);
    }
    interface Presenter extends BasePresenter {
        void getData();
        void deleteGroupMembers(String userIds);
    }
}
