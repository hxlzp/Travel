package com.example.hxl.travel.presenter.contract;

import android.content.Context;

import com.example.hxl.travel.base.BasePresenter;
import com.example.hxl.travel.base.BaseView;

/**
 * Created by hxl on 2017/1/21 at haiChou.
 */
public interface SearchContract {
    interface View extends BaseView<Presenter> {
        //view是否销毁
        boolean isActive();
        Context getSearchContext();
        //该操作需要什么？
        //该操作的结果对应的反馈是什么？
        void showMassage(String massage);
        //该操作过程中友好反馈是什么？---显示进度条、隐藏进度条
        void showProgress();
        void hideProgress();
    }
    interface Presenter extends BasePresenter {
        //该操作主要的功能是
        void getData();
    }
}
