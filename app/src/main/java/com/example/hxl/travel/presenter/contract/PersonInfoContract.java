package com.example.hxl.travel.presenter.contract;

import android.content.Context;
import android.content.Intent;

import com.example.hxl.travel.base.BasePresenter;
import com.example.hxl.travel.base.BaseView;
import com.makeramen.roundedimageview.RoundedImageView;

/**
 * Created by hxl on 2017/1/5 at haiChou.
 */
public interface PersonInfoContract {
    interface View extends BaseView<Presenter>{
        //判断当前view是否销毁
        boolean isActive();
        //该操作需要什么？---显示数据
        void showData();
        //该操作的结果对应的反馈是什么？
        //该操作过程中对应的友好反馈是什么？
        void showProgress();
        void hideProgress();
        RoundedImageView getRoundedImageView();
        Context getPersonInfoContext();
    }
    interface Presenter extends BasePresenter{
        void getData();
        void onActivityResult(int requestCode, int resultCode, Intent data);
    }
}
