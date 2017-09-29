package com.example.hxl.travel.base;

/**
 * Created by hxl on 2016/12/21 at haiChou.
 * View层：界面层，展示数据，响应用户事件，并通知Presenter
 */
public interface BaseView<T> {
    void setPresenter(T presenter);
    void showError(String message);
}
