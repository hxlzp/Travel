package com.example.hxl.travel.ui.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.hxl.travel.R;
import com.example.hxl.travel.base.BaseActivity;
import com.example.hxl.travel.presenter.SexPresenter;
import com.example.hxl.travel.ui.view.SexView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SexActivity extends BaseActivity {
    @BindView(R.id.sex_view)
    SexView sexView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sex);
        unbinder = ButterKnife.bind(this);
        mPresenter = new SexPresenter(sexView);
    }
}
