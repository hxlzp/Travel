package com.example.hxl.travel.presenter.contract;

import android.content.Context;

import com.example.hxl.travel.base.BasePresenter;
import com.example.hxl.travel.base.BaseView;
import com.example.hxl.travel.model.bean.FootprintScenicList;

import java.util.List;

/**
 * Created by hxl on 2017/8/15 0015 at haichou.
 */

public interface FootprintContract {
    interface View extends BaseView<Presenter>{
        boolean isActive();
        Context getFootprintContext();
        void showWebData(List<String> urlData, List<FootprintScenicList> scenicListDatas);
    }
    interface Presenter extends BasePresenter{
        void getData();
    }

}
