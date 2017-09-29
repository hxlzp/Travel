package com.example.hxl.travel.presenter;

import android.content.Context;

import com.example.hxl.travel.R;
import com.example.hxl.travel.base.RxPresenter;
import com.example.hxl.travel.presenter.contract.ScenicDetailsContract;
import com.google.common.base.Preconditions;

/**
 * Created by hxl on 2017/8/21 0021 at haichou.
 */

public class ScenicDetailsPresenter extends RxPresenter implements
        ScenicDetailsContract.Presenter {
    ScenicDetailsContract.View mView;
    private final Context scenicContext;

    public ScenicDetailsPresenter(ScenicDetailsContract.View view){
        mView = Preconditions.checkNotNull(view);
        mView.setPresenter(this);
        scenicContext = mView.getScenicContext();
        getData();
    }
    @Override
    public void getData() {
        String data = scenicContext.getResources().getString(R.string.scenicDetail);
        mView.showData(data);
    }
}
