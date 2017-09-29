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
 * Created by hxl on 2017/6/8 at haiChou.
 * web手绘地图
 */
public interface WebMapContract {
    interface View extends BaseView<Presenter> {
        /**
         * 判断是否是在活跃状态
         */
        boolean isActive();
        /**
         * 该操作需要什么 -> 显示数据(WebView)
         * 该操作的结果对应的反馈是什么
         * 该操作过程中友好交互是什么
         */
        void showData(String url);
        void showProgress();
        void hideProgress();
        Context getWebMapContext();
        void setWebMapMessage(String message);
        void showMapServicePointResources(List<ServicePointMessageResources> servicePointMessageResources );
        void setServiceConnection(ServiceConnection serviceConnection);
        void setMusicService(MusicService musicService );
        MapView getMapView();
        void showTabData(List<ScenicList> datas);

        /*sessionId*/
        String getSessionId();
        String getUserId();
        void setSessionId(String sessionId);
    }
    interface Presenter extends BasePresenter {
        //获得model数据，根据view层事件，提供显示数据
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
