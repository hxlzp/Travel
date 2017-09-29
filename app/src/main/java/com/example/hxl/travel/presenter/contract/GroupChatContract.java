package com.example.hxl.travel.presenter.contract;

import com.example.hxl.travel.base.BasePresenter;
import com.example.hxl.travel.base.BaseView;

/**
 * Created by hxl on 2017/6/9 at haiChou.
 * 聊天
 */
public interface GroupChatContract {
    interface View extends BaseView<Presenter> {
        boolean isActive();
        void showData();
        void showProgress();
        void hideProgress();
    }
    interface Presenter extends BasePresenter {
        void getData();
    }
}
