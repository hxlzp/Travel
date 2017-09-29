package com.example.hxl.travel.ui.activitys;

import android.os.Bundle;
import com.example.hxl.travel.R;
import com.example.hxl.travel.app.App;
import com.example.hxl.travel.base.BaseActivity;
import com.example.hxl.travel.ui.view.MainView;
import com.example.hxl.travel.utils.EventUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {
    private Long firstTime = 0L;
    @BindView(R.id.main_view)
    MainView mainView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
    }

    @Override
    public void onBackPressed() {
        long secondTime = System.currentTimeMillis();
        if (secondTime - firstTime > 1500) {
            EventUtil.showToast(this, "再按一次退出应用程序");
            firstTime = secondTime;
        } else {
            App.getInstance().exitApp();
        }
    }
}
