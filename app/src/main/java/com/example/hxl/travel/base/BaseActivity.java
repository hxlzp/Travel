package com.example.hxl.travel.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.example.hxl.travel.R;
import com.example.hxl.travel.app.App;

import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by hxl on 2016/12/21 at haiChou.
 */
public class BaseActivity<T extends BasePresenter> extends SupportActivity {
    protected T mPresenter;
    protected Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(this.getClass()+"", this.getClass().getName()+"---->onCreate" );
        init();
    }

    protected void init() {
        App.getInstance().registerActivity(this);
        setTranslucentStatus(true);
    }

    protected void setTranslucentStatus(boolean b) {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            //获得Window对象
            Window window = getWindow();
            //获得窗口属性
            WindowManager.LayoutParams windowAttributes = window.getAttributes();
            int flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            if (b){
                //窗口属性的标志
                windowAttributes.flags |= flagTranslucentStatus;
            } else {
                windowAttributes.flags &= ~flagTranslucentStatus;
            }
            window.setAttributes(windowAttributes);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(this.getClass()+"", this.getClass().getName()+"---->onStart" );
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(this.getClass()+"", this.getClass().getName()+"---->onResume" );
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(this.getClass()+"", this.getClass().getName()+"---->onRestart" );
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(this.getClass()+"", this.getClass().getName()+"---->onPause" );
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.e(this.getClass()+"", this.getClass().getName()+"---->onStop" );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(this.getClass()+"", this.getClass().getName()+"---->onDestroy" );
        App.getInstance().unregisterActivity(this);
        if (unbinder!=null){
            unbinder.unbind();
        }
        mPresenter = null;
    }
}
