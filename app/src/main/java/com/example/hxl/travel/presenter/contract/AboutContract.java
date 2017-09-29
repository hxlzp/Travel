package com.example.hxl.travel.presenter.contract;

import com.example.hxl.travel.base.BasePresenter;
import com.example.hxl.travel.base.BaseView;

/**
 * Created by hxl on 2017/1/6 at haiChou.
 */
public interface AboutContract {
    interface View extends BaseView<Presenter> {
        boolean isActive();
    }
    interface Presenter extends BasePresenter {

    }
}
