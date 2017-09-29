package com.example.hxl.travel.ui.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.hxl.travel.R;
import com.example.hxl.travel.base.BaseActivity;
import com.example.hxl.travel.presenter.GroupBuildPresenter;
import com.example.hxl.travel.presenter.PersonInfoPresenter;
import com.example.hxl.travel.presenter.SettingsPresenter;
import com.example.hxl.travel.ui.view.PersonInfoView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PersonInfoActivity extends BaseActivity {
    @BindView(R.id.person_view)
    PersonInfoView personInfoView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info);
        unbinder = ButterKnife.bind(this);
        mPresenter = new PersonInfoPresenter(personInfoView);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ((PersonInfoPresenter)mPresenter).onActivityResult(requestCode,resultCode,data);
    }
}
