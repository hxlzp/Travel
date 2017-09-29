package com.example.hxl.travel.presenter.contract;

import android.content.Context;

import com.example.hxl.travel.base.BasePresenter;
import com.example.hxl.travel.base.BaseView;
import com.example.hxl.travel.model.bean.User;

import java.util.List;

/**
 * Created by hxl on 2017/8/23 0023 at haichou.
 */

public interface AddressLinkerContract {
    interface View extends BaseView<Presenter> {
        boolean isActive();
        Context getAddressLinkerContext();
        void showData(List<User> datas);
        void showPregress();
        void hideProgress();
    }
    interface Presenter extends BasePresenter{
        void getData();
    }
}
