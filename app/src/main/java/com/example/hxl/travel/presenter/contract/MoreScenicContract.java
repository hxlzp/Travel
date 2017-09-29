package com.example.hxl.travel.presenter.contract;

import android.content.Context;

import com.example.hxl.travel.base.BasePresenter;
import com.example.hxl.travel.base.BaseView;
import com.example.hxl.travel.model.bean.RecommendList;

import java.util.List;

/**
 * Created by hxl on 2017/8/14 0014 at haichou.
 */

public interface MoreScenicContract {
    interface View extends BaseView<Presenter>{
        boolean isActive();
        Context getScenicContent();
        /*显示数据*/
        void showData(List<RecommendList> datas);
        /*交互*/
        void showProgress();
        void hideProgress();

    }
    interface Presenter extends BasePresenter{
        void getData();
    }
}
