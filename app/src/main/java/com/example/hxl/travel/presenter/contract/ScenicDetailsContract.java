package com.example.hxl.travel.presenter.contract;

import android.content.Context;

import com.example.hxl.travel.base.BasePresenter;
import com.example.hxl.travel.base.BaseView;

/**
 * Created by hxl on 2017/8/21 0021 at haichou.
 */

public interface ScenicDetailsContract {
    interface View extends BaseView<Presenter> {
        boolean isActive();
        Context getScenicContext();
        void showData(String datas);
    }
    interface Presenter extends BasePresenter {
        void getData();
    }
}

