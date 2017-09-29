package com.example.hxl.travel.base;

/**
 * Created by hxl on 2016/12/21 at haiChou.
 * Presenter展示层：接收Model数据，处理Ui逻辑，并管理View的状态，根据view层的事件，提供展示数据
 */
public interface BasePresenter<T> {
    //绑定view
    void attachView(T view);
    //解除绑定
    void detachView();

}
