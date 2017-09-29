package com.example.hxl.travel.ui.activitys;

import android.os.Bundle;

import com.example.hxl.travel.R;
import com.example.hxl.travel.base.BaseActivity;
import com.example.hxl.travel.presenter.FootprintPresenter;
import com.example.hxl.travel.ui.view.FootprintView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FootprintActivity extends BaseActivity {
    @BindView(R.id.footprint_view)
    FootprintView footprintView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_footprint);
        unbinder = ButterKnife.bind(this);
        mPresenter = new FootprintPresenter(footprintView);
    }
}
