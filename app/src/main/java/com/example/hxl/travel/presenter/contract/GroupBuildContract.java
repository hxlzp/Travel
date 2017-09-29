package com.example.hxl.travel.presenter.contract;

import android.content.Context;
import android.content.Intent;
import android.widget.Button;

import com.example.hxl.travel.base.BasePresenter;
import com.example.hxl.travel.base.BaseView;
import com.makeramen.roundedimageview.RoundedImageView;

/**
 * Created by hxl on 2017/6/8 at haiChou.
 * 创建群组
 */
public interface GroupBuildContract {
    interface View extends BaseView<Presenter> {
        /**
         * View界面层，展示数据，响应用户事件，并通知Presenter
         */
        boolean isActive();
        void showData();
        Context getGroupBuildContext();
        /*该操作需要什么*/
        String getGroupNick();
        /*该操作过程中对应的反馈是什么*/
        void backPage();
        void showMassage(String message);
        /*该操作的结果对应的友好交互是什么*/
        void showProgress();
        void hideProgress();
        RoundedImageView getRoundedImageView();


    }
    interface Presenter extends BasePresenter {
        /**
         * Presenter展示层，处理UI逻辑，管理View状态，根据View事件，传递展示数据，接收Model数据
         */
        void getData();
        void submit(Button button);
        void onActivityResult(int requestCode, int resultCode, Intent data);
    }
}
