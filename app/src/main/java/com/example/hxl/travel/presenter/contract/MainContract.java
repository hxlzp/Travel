package com.example.hxl.travel.presenter.contract;

import com.example.hxl.travel.base.BasePresenter;
import com.example.hxl.travel.base.BaseView;

/**
 * Created by hxl on 2016/12/23 at haiChou.
 */
public interface MainContract {
    interface View extends BaseView<Presenter>{};
    interface Presenter extends BasePresenter{};
}
