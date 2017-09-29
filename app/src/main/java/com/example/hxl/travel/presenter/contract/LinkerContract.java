package com.example.hxl.travel.presenter.contract;

import android.content.Context;

import com.example.hxl.travel.base.BasePresenter;
import com.example.hxl.travel.base.BaseView;
import com.example.hxl.travel.model.bean.User;

import java.util.List;

/**
 * Created by hxl on 2017/8/11 0011 at haichou.
 */

public interface LinkerContract {
    interface View extends BaseView<Presenter> {
        boolean isActive();
        void showData(List<User> datas);
        Context getLinkerContext();
        void showProgress();
        void hideProgress();
    }
    interface Presenter extends BasePresenter {
        void getData();
    }
}
