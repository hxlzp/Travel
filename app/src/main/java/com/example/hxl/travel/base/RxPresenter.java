package com.example.hxl.travel.base;

import android.util.Log;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by hxl on 2016/12/22 at haiChou.
 * Observable持有Context导致的内存泄露
 * 创建subscription的时候，以某种方式持有了context的引用(和view交互的时候，容易持有context的引用)
 * 如果Observable没有及时结束，内存占用就会越来越大
 * 解决方法：在生命周期的某个时刻取消订阅
 * 基于Rx的Presenter封装,
 */
public class RxPresenter<T> implements BasePresenter<T> {
    protected T mView;
    protected CompositeSubscription mCompositeSubscription;
    protected void unSubscribe(){
        if (mCompositeSubscription != null){
            mCompositeSubscription.unsubscribe();
        }
    }
    protected void addSubscribe(Subscription subscription){
        if (mCompositeSubscription == null){
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }
    @Override
    public void attachView(T view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
        unSubscribe();
    }
}
