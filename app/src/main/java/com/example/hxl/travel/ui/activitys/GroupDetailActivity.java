package com.example.hxl.travel.ui.activitys;

import android.os.Bundle;

import com.example.hxl.travel.R;
import com.example.hxl.travel.base.BaseActivity;
import com.example.hxl.travel.presenter.GroupDetailPresenter;
import com.example.hxl.travel.ui.view.GroupDetailView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GroupDetailActivity extends BaseActivity {
    @BindView(R.id.view_group_detail)
    GroupDetailView groupDetailView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_detail);
        unbinder = ButterKnife.bind(this);
        mPresenter = new GroupDetailPresenter(groupDetailView);
    }
}
