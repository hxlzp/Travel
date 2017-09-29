package com.example.hxl.travel.presenter.contract;

import android.content.Context;

import com.example.hxl.travel.base.BasePresenter;
import com.example.hxl.travel.base.BaseView;
import com.example.hxl.travel.model.bean.ImageRecommend;
import com.example.hxl.travel.model.bean.RecommendList;

import java.util.List;

/**
 * Created by hxl on 2016/12/26 at haiChou.
 */
public interface RecommendContract {

    interface View extends BaseView<RecommendContract.Presenter>{
        //判断View是否在活跃状态，若在请求网络，以此判断是否继续进行耗时操作
        boolean isActive();
        //该操作需要什么---展示内容
        void showContent(List<RecommendList> data, List<Integer> icons);
        //该操作的结果对应的反馈是什么---刷新失败或显示内容
        void refreshError(String msg);
        //该操作对应的友好交互---请求网络时间较长，需使用进度条的形式通知用户需要等待，提升用户体验感
        void showProgress();
        void hideProgress();
        void showMarqueeData(List<String> datas);
        Context getRecommendContext();
        void showTabData(List<ImageRecommend> datas);
    }

    interface Presenter extends BasePresenter{

        void getData();
        //该操作的逻辑代码（动作）---刷新和加载
        //刷新数据
        void onRefresh();
        //加载数据
        void onLoad();
        void getMarqueeData();
        void getTabData();
    }
}
