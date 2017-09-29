package com.example.hxl.travel.presenter;

import android.content.Context;

import com.example.hxl.travel.R;
import com.example.hxl.travel.base.RxPresenter;
import com.example.hxl.travel.model.bean.FootprintScenicList;
import com.example.hxl.travel.model.net.RetrofitHelper;
import com.example.hxl.travel.presenter.contract.FootprintContract;
import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hxl on 2017/8/15 0015 at haichou.
 */

public class FootprintPresenter extends RxPresenter implements FootprintContract.Presenter {
    private FootprintContract.View mView;
    private String url = RetrofitHelper.url + RetrofitHelper.footprintUrl;
    private final Context footprintContext;

    public FootprintPresenter(FootprintContract.View view ){
        mView = Preconditions.checkNotNull(view);
        mView.setPresenter(this);
        footprintContext = mView.getFootprintContext();
        getData();
    }
    @Override
    public void getData() {
        /*数据源1*/
        List<String> oneDatas = new ArrayList<>() ;
        oneDatas.add(url);
        /*数据源2*/
        List<FootprintScenicList> twoDatas = new ArrayList<>() ;
        String[] title = footprintContext.getResources().getStringArray(R.array.tiles);
        String[] stayTime = footprintContext.getResources().getStringArray(R.array.stayTime);
        String[] playTime = footprintContext.getResources().getStringArray(R.array.playTime);
        String[] consumeMoney = footprintContext.getResources().getStringArray(R.array.consumeMoney);
        String[] playData = footprintContext.getResources().getStringArray(R.array.playData);
        int icon = R.mipmap.ic_map_tab_scenic_checked;
        for(int i = 0 ;i <title.length;i++){
            twoDatas.add(new FootprintScenicList(title[i],stayTime[i],playTime[i],
                    playData[i],consumeMoney[i],icon));
        }
        mView.showWebData(oneDatas,twoDatas);
    }
}
