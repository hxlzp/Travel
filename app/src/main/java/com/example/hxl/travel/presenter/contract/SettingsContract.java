package com.example.hxl.travel.presenter.contract;

import android.content.Context;

import com.example.hxl.travel.base.BasePresenter;
import com.example.hxl.travel.base.BaseView;

/**
 * Created by hxl on 2017/1/4 at haiChou.
 */
public interface SettingsContract {
    interface View extends BaseView<Presenter>{
        boolean isActive();
        void setSessionId(String sessionId);
        Context getSettingsContext();
    }
    interface Presenter extends BasePresenter{

    }
}
