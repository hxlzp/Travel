package com.example.hxl.travel.presenter;

import android.support.annotation.NonNull;

import com.example.hxl.travel.base.RxPresenter;
import com.example.hxl.travel.presenter.contract.WelcomeContract;
import com.example.hxl.travel.utils.RxUtil;
import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by hxl on 2016/12/22 at haiChou.
 */
public class WelcomePresenter extends RxPresenter implements WelcomeContract.Presenter{
    WelcomeContract.View mView;
    private static final int COUNT_DOWN_TIME = 2200;

    public WelcomePresenter(@NonNull WelcomeContract.View view){
        mView = Preconditions.checkNotNull(view);
        mView.setPresenter(this);
        getWelcomeData();
    }

    /**
     * CountDown开始倒数计秒
     */
    private void startCountDown() {
        Subscription subscription = Observable.timer(COUNT_DOWN_TIME, TimeUnit.MILLISECONDS)
                .compose(RxUtil.<Long>rxSchedulerHelper())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        mView.jumpToMain();
                    }
                });
        addSubscribe(subscription);
    }

    @Override
    public void getWelcomeData() {
        mView.showContent(getImgData());
        startCountDown();
    }

    private List<String> getImgData() {
        List<String> imgs = new ArrayList<>();
        imgs.add("file:///android_asset/d.jpg");
        return imgs;
    }
}
