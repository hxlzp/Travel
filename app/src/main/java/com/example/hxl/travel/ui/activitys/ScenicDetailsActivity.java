package com.example.hxl.travel.ui.activitys;

import android.os.Bundle;

import com.example.hxl.travel.R;
import com.example.hxl.travel.base.BaseActivity;
import com.example.hxl.travel.presenter.ScenicDetailsPresenter;
import com.example.hxl.travel.ui.view.ScenicDetailsView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScenicDetailsActivity extends BaseActivity {
    @BindView(R.id.scenic_details_view)
    ScenicDetailsView scenicDetailsView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scenic_details);
        unbinder = ButterKnife.bind(this);
        mPresenter = new ScenicDetailsPresenter(scenicDetailsView);
    }
}
