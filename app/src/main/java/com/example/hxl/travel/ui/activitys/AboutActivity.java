package com.example.hxl.travel.ui.activitys;

import android.os.Bundle;

import com.example.hxl.travel.R;
import com.example.hxl.travel.base.BaseActivity;
import com.example.hxl.travel.presenter.AboutPresenter;
import com.example.hxl.travel.ui.view.AboutView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutActivity extends BaseActivity {
    @BindView(R.id.about_view)
    AboutView aboutView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        unbinder = ButterKnife.bind(this);
        mPresenter = new AboutPresenter(aboutView);
    }
}
