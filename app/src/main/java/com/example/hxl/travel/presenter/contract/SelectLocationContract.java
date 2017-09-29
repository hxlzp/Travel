package com.example.hxl.travel.presenter.contract;

import android.content.Context;

import com.example.hxl.travel.base.BasePresenter;
import com.example.hxl.travel.base.BaseView;
import com.example.hxl.travel.model.bean.User;

import java.util.List;

/**
 * Created by hxl on 2017/8/10 0010 at haichou.
 */

public interface SelectLocationContract {
    interface View extends BaseView<Presenter>{
        boolean isActive();
        Context getLocationContext();
        void showProgess();
        void hideProgress();
        void showLocationData(List<User> locations);
        void showFlowData(List<String> datas);

    }
    interface Presenter extends BasePresenter{
        void getData();
        void getFlowData();
    }
}
