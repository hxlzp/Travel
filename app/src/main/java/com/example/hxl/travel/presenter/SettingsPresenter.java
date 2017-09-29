package com.example.hxl.travel.presenter;

import android.content.Context;

import com.example.hxl.travel.base.RxPresenter;
import com.example.hxl.travel.presenter.contract.SettingsContract;
import com.example.hxl.travel.utils.SharedPreferencesUtil;
import com.google.common.base.Preconditions;

/**
 * Created by hxl on 2017/1/4 at haiChou.
 */
public class SettingsPresenter extends RxPresenter implements SettingsContract.Presenter{
    SettingsContract.View mView ;

    public SettingsPresenter(SettingsContract.View view) {
        mView = Preconditions.checkNotNull(view);
        mView.setPresenter(this);
        Context settingsContext = mView.getSettingsContext();
        String sessionId = SharedPreferencesUtil.getSessionId(settingsContext);
        mView.setSessionId(sessionId);
    }
}
