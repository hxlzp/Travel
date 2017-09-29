package com.example.hxl.travel.presenter;

import android.content.Context;

import com.example.hxl.travel.R;
import com.example.hxl.travel.base.RxPresenter;
import com.example.hxl.travel.model.bean.User;
import com.example.hxl.travel.presenter.contract.SelectLocationContract;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by hxl on 2017/8/10 0010 at haichou.
 */

public class SelectLocationPresenter extends RxPresenter implements
        SelectLocationContract.Presenter {
    private SelectLocationContract.View mView;
    private final Context locationContext;
    List<User> datas = new ArrayList<>() ;
    List<String> locations = new ArrayList<>() ;

    public SelectLocationPresenter(SelectLocationContract.View view){
        mView = view;
        mView.setPresenter(this);
        locationContext = mView.getLocationContext();
        getData();
        getFlowData();
    }
    @Override
    public void getData() {
        String[] locations = locationContext.getResources().getStringArray(R.array.title);
        for(int i = 0;i<locations.length;i++){
            datas.add(new User(locations[i]));
        }
        Collections.sort(datas);
        mView.showLocationData(datas);
    }

    @Override
    public void getFlowData() {
        String[] names = {"戴村镇"};
        for(int i= 0;i<names.length;i++){
            locations.add(names[i]);
        }
        mView.showFlowData(locations);

    }
}
