package com.example.hxl.travel.ui.activitys;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Bundle;

import com.example.hxl.travel.R;
import com.example.hxl.travel.base.BaseActivity;
import com.example.hxl.travel.presenter.WebMapPresenter;
import com.example.hxl.travel.ui.service.MusicService;
import com.example.hxl.travel.ui.view.WebMapView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebMapActivity extends BaseActivity {
    @BindView(R.id.web_map_view)
    WebMapView webMapView ;
    private MusicService musicService;
    private Intent serviceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_map);
        unbinder = ButterKnife.bind(this);
        mPresenter = new WebMapPresenter(webMapView);
        serviceIntent = new Intent();
        serviceIntent.setClass(this,MusicService.class);
        bindService(serviceIntent, conn,BIND_AUTO_CREATE);
        ((WebMapPresenter)mPresenter).setServiceConnection(conn);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        ((WebMapPresenter) mPresenter).savedInstanceState(savedInstanceState);
    }
    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MusicService.MyBinder myBinder = (MusicService.MyBinder) iBinder;
            musicService = myBinder.getMusicService();
            ((WebMapPresenter)mPresenter).setMusicService(musicService);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            musicService.onUnbind(serviceIntent);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);//conn表示ServiceConnection 对象
    }
}
