package com.example.hxl.travel.ui.activitys;

import android.os.Bundle;

import com.example.hxl.travel.R;
import com.example.hxl.travel.base.BaseActivity;
import com.example.hxl.travel.presenter.WelcomePresenter;
import com.example.hxl.travel.ui.view.WelcomeView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WelcomeActivity extends BaseActivity {
    @BindView(R.id.welcome_view)
    WelcomeView welcomeView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        unbinder = ButterKnife.bind(this);
        mPresenter = new WelcomePresenter(welcomeView);
    }
}
