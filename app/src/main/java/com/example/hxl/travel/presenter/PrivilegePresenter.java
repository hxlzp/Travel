package com.example.hxl.travel.presenter;

import android.content.Context;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.util.Log;

import com.amap.api.maps.MapView;
import com.example.hxl.travel.R;
import com.example.hxl.travel.base.RxPresenter;
import com.example.hxl.travel.model.bean.MapMessage;
import com.example.hxl.travel.model.bean.MarkerInfo;
import com.example.hxl.travel.model.bean.ScenicList;
import com.example.hxl.travel.model.bean.ServicePointMessage;
import com.example.hxl.travel.model.net.RetrofitHelper;
import com.example.hxl.travel.presenter.contract.PrivilegeContract;
import com.example.hxl.travel.ui.service.MusicService;
import com.example.hxl.travel.utils.SharedPreferencesUtil;
import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hxl on 2017/1/19 at haiChou.
 */
public class PrivilegePresenter extends RxPresenter implements PrivilegeContract.Presenter{
    private PrivilegeContract.View mView ;
    private final Context context;
    private String url = "http://112.12.36.86:8008/travel_app/html/scenic_map.html";
    private Subscription subscribe;

    /*存储Id*/
    private final String sessionIdS;
    private final String userIdS;

    /*高德地图覆盖物*/
    private double[] latitudes;
    private double[] longitude;
    private String[] names;
    private int icons;

    public PrivilegePresenter(PrivilegeContract.View view) {
        mView = Preconditions.checkNotNull(view);
        mView.setPresenter(this);
        context = mView.getPrivilegeContext();
        sessionIdS = SharedPreferencesUtil.getSessionId(context);
        userIdS = SharedPreferencesUtil.getUserId(context);
        mView.setSessionId(sessionIdS);
        getData();

    }

    @Override
    public void getData() {
        mView.showData(url);
        getTabData();
    }

    @Override
    public void getMapData(double latitude, double longitude) {
        int latitudeI = (new Double(latitude*1000000)).intValue();
        int longitudeI = (new Double(longitude*1000000)).intValue();
        String latitudeS = String.valueOf(latitudeI);
        String longitudeS = String.valueOf(longitudeI);
        long l = System.currentTimeMillis();
        String s = String.valueOf(l);
        subscribe = RetrofitHelper.mapMessageObservable("5", "222", "guide1",
                longitudeS, latitudeS, "2", "3",s)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MapMessage>() {
                    @Override
                    public void onCompleted() {
                        Log.e("webMapMessage", "onCompleted: " + "complete");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("webMapMessage", "onError: " + e);
                    }

                    @Override
                    public void onNext(MapMessage mapMessage) {
                        Log.e("webMapMessage", "onNext: " + mapMessage);
                    }
                });
        addSubscribe(subscribe);
    }

    @Override
    public void getServicePointResources(String service_point_id) {
        Subscription subscription = RetrofitHelper.servicePointMessageObservable(service_point_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ServicePointMessage>() {
                    @Override
                    public void onCompleted() {
                        Log.e("servicePointMessage", "onCompleted: "+"complete");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("servicePointMessage", "onError: "+e);
                    }

                    @Override
                    public void onNext(ServicePointMessage servicePointMessage) {
                        Log.e("servicePointMessage", "onNext: "+servicePointMessage );
                        String type = servicePointMessage.getType();
                        if (type.equals("1"))
                            mView.showMapServicePointResources(servicePointMessage.getSpResources());
                    }
                });
        addSubscribe(subscription);
    }

    @Override
    public void setServiceConnection(ServiceConnection serviceConnection) {
        mView.setServiceConnection(serviceConnection);
    }

    @Override
    public void setMusicService(MusicService musicService) {
        mView.setMusicService(musicService);
    }

    @Override
    public void savedInstanceState(Bundle savedInstanceState) {
        //执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        MapView mapView = mView.getMapView();
        mapView.onCreate(savedInstanceState);
    }

    @Override
    public void getTabData() {
        String[] tabs = context.getResources().getStringArray(R.array.mapTab);
        int imgs[] = {R.mipmap.ic_map_tab_scenic,R.mipmap.ic_map_tab_food,R.mipmap.ic_map_tab_hotel,
                R.mipmap.ic_map_tab_shopping,R.mipmap.ic_map_tab_hospital,R.mipmap.ic_map_tab_bank,
                R.mipmap.ic_map_tab_police,R.mipmap.ic_map_tab_car,R.mipmap.ic_map_tab_wc};
        int imgChecked[] = {R.mipmap.ic_map_tab_scenic_checked,R.mipmap.ic_map_tab_food_checked,
                R.mipmap.ic_map_tab_hotel_checked, R.mipmap.ic_map_tab_shopping_checked,
                R.mipmap.ic_map_tab_hospital_checked,R.mipmap.ic_map_tab_bank_checked,
                R.mipmap.ic_map_tab_police_checked,R.mipmap.ic_map_tab_car_checked,
                R.mipmap.ic_map_tab_wc_checked};

        List<ScenicList> datas = new ArrayList<>();
        for(int i=0;i<tabs.length;i++){
            datas.add(new ScenicList(imgs[i],imgChecked[i],tabs[i]));
        }
        mView.showTabData(datas);
    }
    private List<MarkerInfo> markerInfos = new ArrayList<>();
    @Override
    public List<MarkerInfo> getGaoDeMarkerInfo(int index) {
        switch (index){
            case 0://景区
                if (markerInfos.size() > 0 && markerInfos != null){
                    markerInfos.clear();
                }
                longitude = new double[]{120.184145,120.156945,120.158356,120.123766,120.115952,
                        120.122390};
                latitudes = new double[]{30.014183,30.007888,30.007359,29.963086,29.972302,
                        29.964436};
                names = new String[]{"仙女湖","三清寨","三清茶","火山云石景","响天岭水库",
                        "亿年火山遗址入口"};
                icons = R.mipmap.ic_map_tab_scenic_checked;
                for(int i = 0; i< latitudes.length; i++){
                    markerInfos.add(new MarkerInfo(latitudes[i], longitude[i],
                            names[i], icons));
                }
                break;
            case 1://美食
                if (markerInfos.size() > 0 && markerInfos != null){
                    markerInfos.clear();
                }
                longitude = new double[]{120.1981786,120.122068,120.128596};
                latitudes= new double[]{30.012570,29.964479,29.967472};
                names = new String[]{"方林面馆","华克山庄","云石星空"};
                icons = R.mipmap.ic_map_tab_food_checked;
                for(int i = 0; i< latitudes.length; i++){
                    markerInfos.add(new MarkerInfo(latitudes[i], longitude[i],
                            names[i], icons));
                }
                break;
            case 2://住宿
                if (markerInfos.size() > 0 && markerInfos != null){
                    markerInfos.clear();
                }
                longitude = new double[]{120.128596,120.116545};
                latitudes= new double[]{29.967472,29.976568};
                names = new String[]{"云石星空","湘耕民宿"};
                icons = R.mipmap.ic_map_tab_hotel_checked;
                for(int i = 0; i< latitudes.length; i++){
                    markerInfos.add(new MarkerInfo(latitudes[i], longitude[i],
                            names[i], icons));
                }
                break;
            case 3://购物
                if (markerInfos.size() > 0 && markerInfos != null){
                    markerInfos.clear();
                }
                longitude = new double[]{120.122068,120.128596};
                latitudes = new double[]{29.964479,29.967472};
                names = new String[]{"华克山庄","云石星空"};
                icons = R.mipmap.ic_map_tab_shopping_checked;
                for(int i = 0; i< latitudes.length; i++){
                    markerInfos.add(new MarkerInfo(latitudes[i], longitude[i],
                            names[i], icons));
                }
                break;
            case 4://医院
                if (markerInfos.size() > 0 && markerInfos != null){
                    markerInfos.clear();
                }
                longitude = new double[]{120.198226,120.192601,120.128810};
                latitudes = new double[]{30.013675,30.010844,29.967690};
                names = new String[]{"第六医院","茶亭伤科医院","尖山下社区卫生服务站"};
                icons = R.mipmap.ic_map_tab_hospital_checked;
                for(int i = 0; i< latitudes.length; i++){
                    markerInfos.add(new MarkerInfo(latitudes[i], longitude[i],
                            names[i], icons));
                }
                break;
            case 5://银行
                if (markerInfos.size() > 0 && markerInfos != null){
                    markerInfos.clear();
                }
                longitude = new double[]{120.200658,120.198163};
                latitudes = new double[]{30.014190,30.010916};
                names = new String[]{"农商银行","农业银行"};
                icons = R.mipmap.ic_map_tab_bank_checked;
                for(int i = 0; i< latitudes.length; i++){
                    markerInfos.add(new MarkerInfo(latitudes[i], longitude[i],
                            names[i], icons));
                }
                break;
            case 6://派出所
                if (markerInfos.size() > 0 && markerInfos != null){
                    markerInfos.clear();
                }
                longitude = new double[]{120.201104};
                latitudes = new double[]{30.014124};
                names = new String[]{"戴村镇派出所"};
                icons = R.mipmap.ic_map_tab_police_checked;
                for(int i = 0; i< latitudes.length; i++){
                    markerInfos.add(new MarkerInfo(latitudes[i], longitude[i],
                            names[i], icons));
                }
                break;
            case 7://停车场
                if (markerInfos.size() > 0 && markerInfos != null){
                    markerInfos.clear();
                }
                longitude = new double[]{120.128810};
                latitudes = new double[]{29.967690};
                names = new String[]{"停车场"};
                icons = R.mipmap.ic_map_tab_car_checked;
                for(int i = 0; i< latitudes.length; i++){
                    markerInfos.add(new MarkerInfo(latitudes[i], longitude[i],
                            names[i], icons));
                }
                break;
            case 8://卫生间
                if (markerInfos.size() > 0 && markerInfos != null){
                    markerInfos.clear();
                }
                break;
        }
        return markerInfos;
    }
}
