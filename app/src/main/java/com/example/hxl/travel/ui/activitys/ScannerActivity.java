package com.example.hxl.travel.ui.activitys;

import android.os.Bundle;

import com.example.hxl.travel.R;
import com.example.hxl.travel.base.BaseActivity;
import com.example.hxl.travel.presenter.ScannerPresenter;
import com.example.hxl.travel.ui.view.ScannerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScannerActivity extends BaseActivity {
    @BindView(R.id.scanner_view)
    ScannerView scannerView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        unbinder = ButterKnife.bind(this);
        mPresenter = new ScannerPresenter(scannerView);
    }
}
