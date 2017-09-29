package com.example.hxl.travel.presenter.contract;

import android.content.Context;

import com.example.hxl.travel.base.BasePresenter;
import com.example.hxl.travel.base.BaseView;

import java.util.List;

/**
 * Created by hxl on 2017/1/6 at haiChou.
 */
public interface EducationContract {
    interface View extends BaseView<Presenter> {
        //判断当前view是否销毁
        boolean isActive();
        //该操作需要什么？
        //该操作的结果对应的反馈是什么？
        void showData(List<String> data);
        Context getEducationContext();
        //该操作过程中对应的友好交互是什么？
    }
    interface Presenter extends BasePresenter {
        void getData();
    }
}
