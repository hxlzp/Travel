package com.example.hxl.travel.ui.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.hxl.travel.R;
import com.example.hxl.travel.base.BaseActivity;
import com.example.hxl.travel.presenter.RegisterPresenter;
import com.example.hxl.travel.ui.view.RegisterView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends BaseActivity {
    @BindView(R.id.register_view)
    RegisterView registerView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        unbinder = ButterKnife.bind(this);
        mPresenter = new RegisterPresenter(registerView);
    }
}
