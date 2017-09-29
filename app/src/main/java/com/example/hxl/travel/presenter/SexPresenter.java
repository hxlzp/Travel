package com.example.hxl.travel.presenter;

import com.example.hxl.travel.R;
import com.example.hxl.travel.base.RxPresenter;
import com.example.hxl.travel.presenter.contract.SexContract;
import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hxl on 2017/1/5 at haiChou.
 */
public class SexPresenter extends RxPresenter implements SexContract.Presenter{
    SexContract.View mView;

    public SexPresenter(SexContract.View view) {
        mView = Preconditions.checkNotNull(view);
        mView.setPresenter(this);
        getData();
    }

    @Override
    public void getData() {
        String[] sex = mView.getSexContext().getResources().getStringArray(R.array.sex);
        List<String> data = new ArrayList<>();
        if (data!=null&&data.size()>0)
            data.removeAll(data);
        for (int i = 0;i <sex.length;i++){
            data.add(sex[i]);
        }
        mView.showData(data);
    }
}
