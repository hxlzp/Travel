package com.example.hxl.travel.ui.activitys;

import android.os.Bundle;

import com.example.hxl.travel.R;
import com.example.hxl.travel.base.BaseActivity;
import com.example.hxl.travel.presenter.MoreScenicPresenter;
import com.example.hxl.travel.ui.view.MoreScenicView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoreScenicActivity extends BaseActivity {
    @BindView(R.id.more_scenic_view)
    MoreScenicView moreScenicView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_scenic);
        unbinder = ButterKnife.bind(this);
        mPresenter = new MoreScenicPresenter(moreScenicView);
    }
}
