package com.example.hxl.travel.ui.activitys;

import android.os.Bundle;

import com.example.hxl.travel.R;
import com.example.hxl.travel.base.BaseActivity;
import com.example.hxl.travel.presenter.AddressLinkerPresenter;
import com.example.hxl.travel.ui.view.AddressLinkerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddressLinkerActivity extends BaseActivity {
    @BindView(R.id.address_linker_view)
    AddressLinkerView addressLinkerView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_linker);
        unbinder = ButterKnife.bind(this);
        mPresenter = new AddressLinkerPresenter(addressLinkerView);
    }
}
