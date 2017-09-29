package com.example.hxl.travel.presenter.contract;

import android.content.Context;

import com.example.hxl.travel.base.BasePresenter;
import com.example.hxl.travel.base.BaseView;

import java.util.List;

/**
 * Created by hxl on 2017/1/3 at haiChou.
 */
public interface MineContract {
    interface View extends BaseView<Presenter>{
        boolean isActive();

        /**
         * 1.该操作需要什么？
         * 2.该操作的结果对应的反馈是什么？
         * 3.该操作的友好反馈是什么？
         */
        Context getMineContext();
        void showContent(List<Integer> data, List<String> title);
        void showUserMessage(String phone,String nickName);
        void showProgress();
        void hideProgress();

        void setSessionId(String sessionId);
    }
    interface Presenter extends BasePresenter{
        void getData();
    }
}
