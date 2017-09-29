package com.example.hxl.travel.ui.activitys;

import android.os.Bundle;

import com.example.hxl.travel.R;
import com.example.hxl.travel.base.BaseActivity;
import com.example.hxl.travel.presenter.SelectLocationPresenter;
import com.example.hxl.travel.ui.view.SelectLocationView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectLocationActivity extends BaseActivity{
    @BindView(R.id.select_location_view)
    SelectLocationView selectLocationView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_location);
        unbinder = ButterKnife.bind(this);
        mPresenter = new SelectLocationPresenter(selectLocationView);
    }
}
