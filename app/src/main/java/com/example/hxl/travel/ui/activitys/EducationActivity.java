package com.example.hxl.travel.ui.activitys;

import android.os.Bundle;

import com.example.hxl.travel.R;
import com.example.hxl.travel.base.BaseActivity;
import com.example.hxl.travel.presenter.EducationPresenter;
import com.example.hxl.travel.ui.view.EducationView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EducationActivity extends BaseActivity {
    @BindView(R.id.education_view)
    EducationView educationView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education);
        unbinder = ButterKnife.bind(this);
        mPresenter = new EducationPresenter(educationView);
    }
}
