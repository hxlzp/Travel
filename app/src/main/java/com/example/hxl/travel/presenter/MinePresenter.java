package com.example.hxl.travel.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.hxl.travel.R;
import com.example.hxl.travel.base.RxPresenter;
import com.example.hxl.travel.presenter.contract.MineContract;
import com.example.hxl.travel.utils.SharedPreferencesUtil;
import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hxl on 2017/1/3 at haiChou.
 */
public class MinePresenter extends RxPresenter implements MineContract.Presenter{
    MineContract.View mView;
    private final Context mineContext;
    private final String sessionIdS;

    public MinePresenter(MineContract.View view) {
        mView = Preconditions.checkNotNull(view);
        mView.setPresenter(this);
        mineContext = mView.getMineContext();
        SharedPreferences sharedPreferences =
                SharedPreferencesUtil.getSharedPreferences(mineContext, "user");
        String userPhone = sharedPreferences.getString("userPhone", null);
        String nickname = sharedPreferences.getString("nickname", null);
        sessionIdS = SharedPreferencesUtil.getSessionId(mineContext);
        mView.showUserMessage(userPhone,nickname);
        mView.setSessionId(sessionIdS);
        getData();
    }

    @Override
    public void getData() {
        List<String> title = new ArrayList<>();
        List<Integer> data = new ArrayList<>();
        String[] titleStr = mineContext.getResources().getStringArray(R.array.mineItems);
        Integer[] iconStr = {R.mipmap.ic_my_footprint,R.mipmap.ic_my_travel_notes,
                R.mipmap.ic_my_strategy,
                R.mipmap.ic_my_attention,R.mipmap.ic_my_favorite,R.mipmap.ic_my_service,
                R.mipmap.ic_my_couple_back};
        for (int i = 0;i<titleStr.length;i++){
            data.add(iconStr[i]);
            title.add(titleStr[i]);
        }
        mView.showContent(data,title);
    }
}