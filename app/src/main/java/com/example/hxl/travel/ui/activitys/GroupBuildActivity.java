package com.example.hxl.travel.ui.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.hxl.travel.R;
import com.example.hxl.travel.base.BaseActivity;
import com.example.hxl.travel.presenter.GroupBuildPresenter;
import com.example.hxl.travel.ui.view.GroupBuildView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GroupBuildActivity extends BaseActivity {
    @BindView(R.id.activity_group_build_view)
    GroupBuildView groupBuildView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_build);
        unbinder = ButterKnife.bind(this);
        mPresenter = new GroupBuildPresenter(groupBuildView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ((GroupBuildPresenter)mPresenter).onActivityResult(requestCode,resultCode,data);
    }
}
