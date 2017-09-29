package com.example.hxl.travel.presenter;

import com.example.hxl.travel.base.RxPresenter;
import com.example.hxl.travel.presenter.contract.AboutContract;
import com.google.common.base.Preconditions;

/**
 * Created by hxl on 2017/1/6 at haiChou.
 */
public class AboutPresenter extends RxPresenter implements AboutContract.Presenter{
    AboutContract.View mView ;

    public AboutPresenter(AboutContract.View view) {
        mView = Preconditions.checkNotNull(view);
        mView.setPresenter(this);
    }
}
