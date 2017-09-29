package com.example.hxl.travel.presenter.contract;

import android.content.Context;
import android.content.ServiceConnection;
import android.os.Bundle;

import com.amap.api.maps.MapView;
import com.example.hxl.travel.base.BasePresenter;
import com.example.hxl.travel.base.BaseView;
import com.example.hxl.travel.model.bean.MarkerInfo;
import com.example.hxl.travel.model.bean.ScenicList;
import com.example.hxl.travel.model.bean.ServicePointMessageResources;
import com.example.hxl.travel.ui.service.MusicService;

import java.util.List;

/**
 * Created by hxl on 2017/1/19 at haiChou.
 */
public interface PrivilegeContract {
    /**
     * 观察功能上的操作
     */
    interface View extends BaseView<Presenter>{
        boolean isActive();
        Context getPrivilegeContext();
        /**
         * 该操作需要什么
         */
        void showData(String url);
        /**
         * 该操作的结果对应的反馈是什么
         */
        /**
         * 该操作过程中对应的友好交互
         */
        void showProgress();
        void hideProgress();
        void showMapServicePointResources(List<ServicePointMessageResources> servicePointMessageResources );
        void setServiceConnection(ServiceConnection serviceConnection);
        void setMusicService(MusicService musicService );
        MapView getMapView();
        void showTabData(List<ScenicList> tabs);

        /*sessionId*/
        String getSessionId();
        String getUserId();
        void setSessionId(String sessionId);
    }
    interface Presenter extends BasePresenter{
        void getData();
        void getMapData(double latitude,double longitude);
        void getServicePointResources(String service_point_id);
        void setServiceConnection(ServiceConnection serviceConnection);
        void setMusicService(MusicService musicService );
        void savedInstanceState(Bundle savedInstanceState);
        void getTabData();

        List<MarkerInfo>  getGaoDeMarkerInfo(int index);
    }
}
