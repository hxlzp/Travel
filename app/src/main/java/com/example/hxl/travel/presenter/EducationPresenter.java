package com.example.hxl.travel.presenter;

import com.example.hxl.travel.R;
import com.example.hxl.travel.base.RxPresenter;
import com.example.hxl.travel.presenter.contract.EducationContract;
import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hxl on 2017/1/6 at haiChou.
 */
public class EducationPresenter extends RxPresenter implements EducationContract.Presenter {
    EducationContract.View mView ;

    public EducationPresenter(EducationContract.View view) {
        mView = Preconditions.checkNotNull(view);
        mView.setPresenter(this);
        getData();
    }

    @Override
    public void getData() {
        String[] education = mView.getEducationContext().getResources()
                .getStringArray(R.array.education);
        List<String> data = new ArrayList<>();
        for (int i = 0;i <education.length;i++){
            data.add(education[i]);
        }
        mView.showData(data);
    }
}
