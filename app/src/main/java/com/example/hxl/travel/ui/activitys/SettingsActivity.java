package com.example.hxl.travel.ui.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.hxl.travel.R;
import com.example.hxl.travel.base.BaseActivity;
import com.example.hxl.travel.presenter.SettingsPresenter;
import com.example.hxl.travel.ui.view.SettingsView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsActivity extends BaseActivity {
    @BindView(R.id.settings_view)
    SettingsView settingsView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        unbinder = ButterKnife.bind(this);
        mPresenter = new SettingsPresenter(settingsView);
    }
}
