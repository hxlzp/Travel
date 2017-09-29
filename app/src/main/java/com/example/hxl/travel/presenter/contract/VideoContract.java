package com.example.hxl.travel.presenter.contract;

import android.content.Context;

import com.example.hxl.travel.base.BasePresenter;
import com.example.hxl.travel.base.BaseView;

/**
 * Created by hxl on 2017/4/20 at haiChou.
 */
public interface VideoContract {
    interface View extends BaseView<Presenter>{
        boolean isActive();
        Context getVideoContext();
        void showVideoData(String url);
        void hideProgress();
        void showProgress();
        String getVideoUrl();
    }
    interface Presenter extends BasePresenter{
        void getVideoData();
    }
}
