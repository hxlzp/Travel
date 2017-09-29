package com.example.hxl.travel.ui.fragments;


import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;

import com.example.hxl.travel.R;
import com.example.hxl.travel.base.BaseFragment;
import com.example.hxl.travel.presenter.PrivilegePresenter;
import com.example.hxl.travel.presenter.contract.PrivilegeContract;
import com.example.hxl.travel.ui.service.MusicService;
import com.example.hxl.travel.ui.view.PrivilegeView;

import butterknife.BindView;

import static android.content.Context.BIND_AUTO_CREATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class PrivilegeFragment extends BaseFragment<PrivilegeContract.Presenter> {
    @BindView(R.id.privilege_view)
    PrivilegeView privilegeView ;
    private MusicService musicService;
    private Intent serviceIntent;
    @Override
    protected void initView(LayoutInflater inflater) {
        super.initView(inflater);
        mPresenter = new PrivilegePresenter(privilegeView);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        serviceIntent = new Intent();
        serviceIntent.setClass(getActivity(),MusicService.class);
        getActivity().bindService(serviceIntent, conn,BIND_AUTO_CREATE);
        mPresenter.setServiceConnection(conn);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mPresenter.savedInstanceState(savedInstanceState);
    }

    @Override
    protected int getLayout() {

        return R.layout.fragment_privilege;
    }

    @Override
    protected void lazyFetchData() {
        super.lazyFetchData();
        mPresenter.getData();
    }
    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MusicService.MyBinder myBinder = (MusicService.MyBinder) iBinder;
            musicService = myBinder.getMusicService();
            mPresenter.setMusicService(musicService);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            musicService.onUnbind(serviceIntent);
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unbindService(conn);//conn表示ServiceConnection 对象
    }
}
