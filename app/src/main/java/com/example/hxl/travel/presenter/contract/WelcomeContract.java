package com.example.hxl.travel.presenter.contract;

import com.example.hxl.travel.base.BasePresenter;
import com.example.hxl.travel.base.BaseView;

import java.util.List;

/**
 * Created by hxl on 2016/12/22 at haiChou.
 */
public interface WelcomeContract {
    /**
     * 对于View接口，观察功能上的操作，然后考虑：
     * 1.该操作需要什么？
     * 2.该操作的结果对应的反馈是什么？
     * 3.该操作过程中对应的友好交互是什么？
     */
    interface View extends BaseView<Presenter>{
        boolean isActive();
        //界面层：展示数据
        void showContent(List<String> list);
        void jumpToMain();
    }
    /**
     * 对于Presenter接口，也需要观察功能上有什么操作
     * Presenter的功能是View和Model之间交互的桥梁
     */
    interface Presenter extends BasePresenter{
        //展示层：接收model数据，处理ui逻辑，管理View状态，根据View层事件提供展示数据
        //处理动作操作
        void getWelcomeData();
    }
}
