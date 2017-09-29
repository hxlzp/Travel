package com.example.hxl.travel.ui.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.hxl.travel.R;
import com.example.hxl.travel.base.BaseActivity;
import com.example.hxl.travel.presenter.AgePresenter;
import com.example.hxl.travel.ui.view.AgeView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AgeActivity extends BaseActivity {
    @BindView(R.id.age_view)
    AgeView ageView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_age);
        unbinder = ButterKnife.bind(this);
        mPresenter = new AgePresenter(ageView);
    }
}
