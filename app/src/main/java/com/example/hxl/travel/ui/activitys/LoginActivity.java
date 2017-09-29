package com.example.hxl.travel.ui.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.hxl.travel.R;
import com.example.hxl.travel.base.BaseActivity;
import com.example.hxl.travel.presenter.LoginPresenter;
import com.example.hxl.travel.ui.view.LoginView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.login_view)
    LoginView loginView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        unbinder = ButterKnife.bind(this);
        mPresenter = new LoginPresenter(loginView);
    }
}
