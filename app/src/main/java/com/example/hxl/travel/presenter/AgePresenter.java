package com.example.hxl.travel.presenter;

import com.example.hxl.travel.R;
import com.example.hxl.travel.base.RxPresenter;
import com.example.hxl.travel.presenter.contract.AgeContract;
import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hxl on 2017/1/6 at haiChou.
 */
public class AgePresenter extends RxPresenter implements AgeContract.Presenter{
    AgeContract.View mView;

    public AgePresenter(AgeContract.View view) {
        mView = Preconditions.checkNotNull(view);
        mView.setPresenter(this);
        getData();
    }

    @Override
    public void getData() {
        String[] age = mView.getAgeContext().getResources().getStringArray(R.array.age);
        List<String> data = new ArrayList<>();
        if (data!=null&data.size()>0)
            data.removeAll(data);
        for (int i = 0;i <age.length;i++){
            data.add(age[i]);
        }
        mView.showData(data);
    }
}
