package com.example.hxl.travel.ui.view;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.bumptech.glide.Glide;
import com.example.hxl.travel.R;
import com.example.hxl.travel.base.RootView;
import com.example.hxl.travel.model.bean.MarkerInfo;
import com.example.hxl.travel.model.bean.ScenicList;
import com.example.hxl.travel.model.bean.ServicePointMessageResources;
import com.example.hxl.travel.model.event.LoginEvent;
import com.example.hxl.travel.model.event.LoginQuit;
import com.example.hxl.travel.presenter.contract.PrivilegeContract;
import com.example.hxl.travel.ui.activitys.FootprintActivity;
import com.example.hxl.travel.ui.activitys.LoginActivity;
import com.example.hxl.travel.ui.activitys.MainActivity;
import com.example.hxl.travel.ui.activitys.VideoActivity;
import com.example.hxl.travel.ui.activitys.WebMapActivity;
import com.example.hxl.travel.ui.adapter.MapServiceListAdapter;
import com.example.hxl.travel.ui.service.MusicService;
import com.example.hxl.travel.utils.EventUtil;
import com.example.hxl.travel.utils.JumpUtil;
import com.example.hxl.travel.utils.NetWorkUtil;
import com.example.hxl.travel.utils.ScreenUtil;
import com.example.hxl.travel.utils.ToastUtil;
import com.example.hxl.travel.widget.bezierViewPager.BezierViewPager;
import com.example.hxl.travel.widget.bezierViewPager.CardPagerAdapter;
import com.google.common.base.Preconditions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**
 * Created by hxl on 2017/4/11 at haiChou.
 */
public class PrivilegeView extends RootView<PrivilegeContract.Presenter>
        implements PrivilegeContract.View, AMapLocationListener,
        AMap.OnMyLocationChangeListener,
        View.OnClickListener,
        CardPagerAdapter.OnCardItemClickListener,
        MusicService.OnMusicListener, AdapterView.OnItemClickListener,
        CompoundButton.OnCheckedChangeListener {
    @BindView(R.id.web_map)
    WebView webView;
    @BindView(R.id.gaode_map)
    MapView mapView ;
    @BindView(R.id.iv_map_server)
    ImageView ivMapService;
    @BindView(R.id.iv_map_location)
    ImageView ivMapLocation;
    @BindView(R.id.iv_map_switch)
    ImageView ivMapSwitch;
    @BindView(R.id.iv_map_music_control)
    ImageView ivMapVoiceControl;
    @BindView(R.id.iv_map_my_location)
    ImageView ivMyFriendLocation;
    @BindView(R.id.rl_network)
    RelativeLayout rlNetwork;
    @BindView(R.id.rl_web)
    RelativeLayout rlWeb;

    private LayoutInflater layoutInflater;
    /*服务点弹窗信息*/
    private PopupWindow scenicPopupWindow;
    private Button btnDialogMapNavigator;
    private BezierViewPager pagerDialogMap;
    private List<String> imgList;
    private Button btnDialogMapTitle;
    private String imageUrl = "http://112.12.36.86:8008/travel_app/";

    /*用户弹窗*/
    private PopupWindow userPopupWindow;
    private int backgroundPop = 0xa0000000;
    private TextView tvUserName;
    private ImageView ivUserPhone;

    private List<String> imgResources;
    private Intent jumpIntent;
    private AMapLocationClient aMapLocationClient;
    private MusicService musicService;
    private ServiceConnection conn;
    private AMap aMap;
    private boolean isGaoDe = false;
    private boolean isVoice = true;
    private ListView listView;
    /*服务点弹窗*/
    private PopupWindow servicePopupWindow;
    private ImageView ivVideo;
    private ImageView ivVoice;
    private ImageView ivClose;
    private MapServiceListAdapter adapter;
    private int mapServiceWidth;
    /*景点弹窗的宽度*/
    private int mWidth;
    private ImageView ivDialogIcon;

    /*定位好友*/
    private boolean isLocation;
    /*求救*/
    private boolean isHelp;
    private String tag;
    private PopupWindow helpPopupWindow;
    private CheckBox cbHospitalHelp;
    private CheckBox cbPoliceHelp;
    private Button btnHelp;
    /*音频播放*/
    private RotateAnimation rotateAnimation;
    private boolean isPlay;

    /*登陆 String sessionId*/
    private String sessionId = null;
    private String userId = null;
    /*存储 sessionId*/
    private String sessionIdS = null;

    private String userName;
    private String userPhone;

    /*服务设施弹窗*/
    private ImageView ivServiceFacility;
    private TextView tvServiceFacilityTitle;
    private ImageView ivServiceFacilityPhone;
    private TextView tvServiceFacilityLocation;
    private PopupWindow serviceFacilityPopupWindow;

    /*登录对话框*/
    private AlertDialog alertDialog;
    private double latitude;
    private double longitude;


    public PrivilegeView(Context context) {
        super(context);
    }

    public PrivilegeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PrivilegeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void getLayout() {
        inflate(mContext, R.layout.fragment_privilege_view,this);
    }

    @android.webkit.JavascriptInterface
    @Override
    protected void initView() {
        if (!NetWorkUtil.isNetWorkAvailable(mContext)){
            rlNetwork.setVisibility(VISIBLE);
            rlWeb.setVisibility(GONE);
        }else {
            rlNetwork.setVisibility(GONE);
            rlWeb.setVisibility(VISIBLE);
        }
        jumpIntent = new Intent();
        //获得webView设置
        WebSettings settings = webView.getSettings();
        //开启javascript
        settings.setJavaScriptEnabled(true);
        //不显示放大控制按钮
        settings.setDisplayZoomControls(false);
        //不支持缩放
        settings.setSupportZoom(false);
        settings.setBuiltInZoomControls(false);
        //使用推荐的窗口
        settings.setUseWideViewPort(true);
        //设置网页自适应屏幕大小
        settings.setLoadWithOverviewMode(true);
        /*使把图片加载放在最后来加载渲染*/
        //settings.setBlockNetworkImage(true);
        /*提高缓存*/
        //settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        /*开启H5(APPCache)缓存功能*/
        //settings.setAppCacheEnabled(true);
        /*webView数据缓存分为两种：AppCache和DOM Storage（Web Storage）。*/
        /*开启 DOM storage 功能*/
        //settings.setDomStorageEnabled(true);
        /*应用可以有数据库*/
        //settings.setDatabaseEnabled(true);
        settings.setAllowFileAccess(true);
        //settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        /*不缓存*/
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.addJavascriptInterface(this, "android");

        //1.初始化定位
        initLocation();
        //2.设置定位选项
        initLocationOption();
        //3.初始化地图
        initMap();
        uiSettings();

        layoutInflater = LayoutInflater.from(mContext);

        /*用户弹窗*/
        initUserPopupWindow();
        /*景点*/
        initScenicPopupWindow();
        initServicePopupWindow();
        /*求救*/
        initHelpPopupWindow();
        /*服务设施*/
        initServiceFacilityPopupWindow();

    }
    /*登录对话框*/
    private void showLoginDialog() {
        View view = layoutInflater.inflate(R.layout.dialog_login_whether,null,false);
        Button btnDialogCancel = ButterKnife.findById(view,R.id.btn_dialog_cancel);
        Button btnDialogConfirm = ButterKnife.findById(view,R.id.btn_dialog_confirm);
        btnDialogCancel.setOnClickListener(this);
        btnDialogConfirm.setOnClickListener(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setView(view);
        //显示对话框
        alertDialog = builder.show();
    }

    /*获得icon的宽度*/
    /*确保调用一次*/
    private boolean isFirst = true;
    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if(isFirst){
            isFirst = false;
            mapServiceWidth = ivMapService.getWidth();
            mWidth = getWidth()- ScreenUtil.dip2px(mContext,10);
        }
    }

    private void uiSettings() {
        /*3.1与控件交互 缩放按钮、指南针、定位按钮、比例尺*/
        /*UiSettings 类用于操控这些控件，以定制自己想要的视图效果。
        UiSettings 类对象的实例化需要通过 AMap 类来实现*/
        UiSettings uiSettings = aMap.getUiSettings();
        uiSettings.setCompassEnabled(false);
        uiSettings.setZoomControlsEnabled(false);
        uiSettings.setMyLocationButtonEnabled(false);
        uiSettings.setScaleControlsEnabled(true);
        uiSettings.setLogoPosition(AMapOptions.LOGO_MARGIN_BOTTOM);

    }

    //3.初始化地图
    private void initMap() {
        /*初始化地图控制器对象*/
        if(aMap == null){
            aMap = mapView.getMap();
        }
        /*实现定位蓝点*/
        MyLocationStyle myLocationStyle = new MyLocationStyle() ;
        /*定位蓝点的展示模式*/
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);
        /*设置是否需要小蓝点*/
        myLocationStyle.showMyLocation(true);
        /*自定义蓝点图标*/
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory
                .fromResource(R.mipmap.ic_map_my_location);
        myLocationStyle.myLocationIcon(bitmapDescriptor);
        /*设置定位蓝点的样式*/
        aMap.setMyLocationStyle(myLocationStyle);
        /*启动显示定位蓝点*/
        aMap.setMyLocationEnabled(true);
        /*开启室内地图，默认关闭*/
        aMap.showIndoorMap(true);
        aMap.setOnMyLocationChangeListener(this);

    }

    private void initLocationOption() {
        AMapLocationClientOption aMapLocationClientOption = new AMapLocationClientOption();
        /*设置GPS定位*/
        aMapLocationClientOption.setGpsFirst(true);
        /*间隔10秒上传一次*/
        aMapLocationClientOption.setInterval(10000);
        /*需要定位地址*/
        aMapLocationClientOption.setNeedAddress(true);
        /*设置定位的精确度*/
        aMapLocationClientOption.setLocationMode(AMapLocationClientOption
                .AMapLocationMode.Hight_Accuracy);
        aMapLocationClient.setLocationOption(aMapLocationClientOption);

    }

    /*景点*/
    private void initScenicPopupWindow() {
        View view = layoutInflater.inflate(R.layout.popup_map_scenic,null);
        btnDialogMapTitle = ButterKnife.findById(view, R.id.btn_dialog_map_title);
        btnDialogMapNavigator = ButterKnife.findById(view, R.id.btn_dialog_map_navigator);
        pagerDialogMap = ButterKnife.findById(view, R.id.pager_dialog_map);
        ivDialogIcon = ButterKnife.findById(view, R.id.iv_dialog_map_icon);
        ivVideo = ButterKnife.findById(view, R.id.iv_map_popup_video);
        ivVoice = ButterKnife.findById(view, R.id.iv_map_popup_voice);
        ivClose = ButterKnife.findById(view, R.id.iv_close);
        scenicPopupWindow = new PopupWindow(view, ScreenUtil.getScreenWidth(mContext) -
                ScreenUtil.dip2px(mContext,80),
                ScreenUtil.getScreenHeight(mContext)-ScreenUtil.dip2px(mContext,200));
        setPopupWindow(scenicPopupWindow);
    }
    /*景点列表*/
    private void initServicePopupWindow() {
        View view = layoutInflater.inflate(R.layout.popup_map_service,null);
        listView = ButterKnife.findById(view, R.id.lv_activity_map_pop);
        servicePopupWindow = new PopupWindow(view,
                ScreenUtil.dip2px(mContext,80), LayoutParams.WRAP_CONTENT);
        setPopupWindow(servicePopupWindow);
    }

    private void setPopupWindow(PopupWindow popupWindow) {
        //设置popWindow的背景
        popupWindow.setBackgroundDrawable(new ColorDrawable(backgroundPop));
        popupWindow.setAnimationStyle(R.style.AnimScale);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
    }

    /*用户弹窗*/
    private void initUserPopupWindow() {
        View view = layoutInflater.inflate(R.layout.popup_map_user,null);
        ivUserPhone = ButterKnife.findById(view, R.id.iv_dialog_map_user_phone);
        tvUserName = ButterKnife.findById(view, R.id.tv_dialog_map_user_name);
        userPopupWindow = new PopupWindow(view, ScreenUtil.getScreenWidth(mContext) -
                ScreenUtil.dip2px(mContext,120),
                LayoutParams.WRAP_CONTENT) ;
        setPopupWindow(userPopupWindow);
    }

    /*服务设施*/
    private void initServiceFacilityPopupWindow() {
        View view = layoutInflater.inflate(R.layout.popup_map_service_facility,null);
        ivServiceFacility = ButterKnife.findById(view, R.id.iv_dialog_map_service_facility);
        tvServiceFacilityTitle = ButterKnife.findById(view,
                R.id.tv_dialog_map_service_facility_title);
        ivServiceFacilityPhone = ButterKnife.findById(view,
                R.id.iv_dialog_map_service_facility_phone);
        tvServiceFacilityLocation = ButterKnife.findById(view,
                R.id.tv_dialog_map_service_facility_location);

        serviceFacilityPopupWindow = new PopupWindow(view, ScreenUtil.getScreenWidth(mContext) -
                ScreenUtil.dip2px(mContext,120),
                LayoutParams.WRAP_CONTENT);
        setPopupWindow(serviceFacilityPopupWindow);

    }
    /*求救*/
    private void initHelpPopupWindow() {
        View view = layoutInflater.inflate(R.layout.popup_map_help,null);
        cbHospitalHelp = ButterKnife.findById(view, R.id.cb_popup_hospital);
        cbPoliceHelp = ButterKnife.findById(view, R.id.cb_popup_police);
        btnHelp = ButterKnife.findById(view, R.id.btn_popup_help);
        helpPopupWindow = new PopupWindow(view, ScreenUtil.getScreenWidth(mContext) -
                ScreenUtil.dip2px(mContext,150),
                LayoutParams.WRAP_CONTENT);
        setPopupWindow(helpPopupWindow);

    }
    @Override
    protected void initEvent() {
        btnDialogMapNavigator.setOnClickListener(this);
        ivVideo.setOnClickListener(this);
        ivVoice.setOnClickListener(this);
        ivClose.setOnClickListener(this);
        btnHelp.setOnClickListener(this);
        ivServiceFacilityPhone.setOnClickListener(this);
        cbHospitalHelp.setOnCheckedChangeListener(this);
        cbPoliceHelp.setOnCheckedChangeListener(this);
    }

    /**
     * js调用java
     */
    @android.webkit.JavascriptInterface
    public void visitor_group_detail(final String json) {
        ((MainActivity) mContext).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    Log.e("json", "run: "+json);
                    String server_time = jsonObject.getString("server_time");
                    String user_type = jsonObject.getString("user_type");
                    String group_id = jsonObject.getString("group_id");
                    String user_name = jsonObject.getString("user_name");
                    String latitude = jsonObject.getString("latitude");
                    String view_time = jsonObject.getString("view_time");
                    String interval_time = jsonObject.getString("interval_time");
                    String longitude = jsonObject.getString("longitude");
                    showUserDialog(user_name);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private void showUserDialog(String user_name) {
        tvUserName.setText(user_name);
        ivUserPhone.setOnClickListener(this);
        userPopupWindow.showAtLocation(this,Gravity.CENTER,0,0);
    }
    /*服务点*/
    @android.webkit.JavascriptInterface
    public void server_point_detail(final String json) {
        ((MainActivity) mContext).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    String service_point_type = jsonObject.getString("service_point_type");

                    if (service_point_type.equals("1")){
                        showDialog(jsonObject);
                    }else {
                        showServiceFacilityPopup(jsonObject);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    /*显示服务设施*/
    private void showServiceFacilityPopup(JSONObject jsonObject) {
        try {
            tvServiceFacilityTitle.setText(jsonObject.getString("service_point_name"));
            Glide.with(mContext).load(imageUrl + jsonObject
                    .getString("service_point_icon_url")).into(ivServiceFacility);
//            String service_point_id = jsonObject.getString("service_point_id");
//            mPresenter.getServicePointResources(service_point_id);
            serviceFacilityPopupWindow.showAtLocation(this, Gravity.CENTER,0,0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /*显示对话框*/
    private void showDialog(JSONObject jsonObject) {
        try {
            btnDialogMapTitle.setText(jsonObject.getString("service_point_name"));
            Glide.with(mContext).load(imageUrl + jsonObject
                    .getString("service_point_icon_url")).into(ivDialogIcon);
            String service_point_id = jsonObject.getString("service_point_id");
            mPresenter.getServicePointResources(service_point_id);
            scenicPopupWindow.showAtLocation(this, Gravity.CENTER,0,0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /*显示图片*/
    private void showPicture() {
        float heightRatio = 0.565f;  //高是宽的 0.565 ,根据图片比例
        int mHeight = (int) (mWidth * heightRatio);
        CardPagerAdapter cardAdapter = new CardPagerAdapter(mContext);
        cardAdapter.addImgUrlList(imgList);
        //设置阴影大小，即vPage  左右两个图片相距边框  maxFactor + 0.3*CornerRadius   *2
        //设置阴影大小，即vPage 上下图片相距边框  maxFactor*1.5f + 0.3*CornerRadius
        int maxFactor = mWidth / 25;
        cardAdapter.setMaxElevationFactor(maxFactor);

        int mWidthPadding = mWidth / 8;

        //因为我们adapter里的cardView CornerRadius已经写死为10dp，所以0.3*CornerRadius=3
        //设置Elevation之后，控件宽度要减去 (maxFactor + dp2px(3)) * heightRatio
        //heightMore 设置Elevation之后，控件高度 比  控件宽度* heightRatio  多出的部分
        float heightMore = (1.5f * maxFactor + ScreenUtil.dip2px(mContext, 3))
                - (maxFactor + ScreenUtil.dip2px(mContext, 3)) * heightRatio;
        int mHeightPadding = (int) (mWidthPadding * heightRatio - heightMore);
        pagerDialogMap.setLayoutParams(new RelativeLayout.LayoutParams(mWidth, mHeight));
        pagerDialogMap.setPadding(mWidthPadding, mHeightPadding, mWidthPadding, mHeightPadding);
        //表示不裁剪Padding，这时候我们就可以看到左右的ViewPager，相当于原本两边的Padding透明度为1，
        // 而设置false之后透明度为0。
        pagerDialogMap.setClipToPadding(false);
        pagerDialogMap.setAdapter(cardAdapter);
        pagerDialogMap.showTransformer(0.2f);
        pagerDialogMap.setCurrentItem(1);

        cardAdapter.setOnCardItemClickListener(this);
    } 


    @Override
    public boolean isActive() {
        return mActive;
    }

    @Override
    public Context getPrivilegeContext() {
        return mContext;
    }

    @Override
    public void showData(final String url) {
        // 根据网络连接情况，设置缓存模式，

        if (NetWorkUtil.isNetWorkAvailable(mContext)) {
            //webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
            // 根据cache-control决定是否从网络上取数据
        } else {
            //webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            // 先查找缓存，没有的情况下从网络获取。

        }
        //WebViewClient：辅助WebView处理各种通知与请求事件！
        //主要处理解析，渲染网页等浏览器做的事情
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(url);
                return true;
            }

        });
        webView.loadUrl(url);
        //WebChromeClient：辅助WebView处理Javascript的对话框、网站图标、网站title、加载进度等！
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if(newProgress==100){

                }
                else{

                }
            }
        });
    }

    @Override
    public void showProgress() {


    }

    @Override
    public void hideProgress() {

    }

    /*服务点*/
    @Override
    public void showMapServicePointResources(List<ServicePointMessageResources>
                                                     servicePointMessageResources ) {
        int size = servicePointMessageResources.size();
        imgList = new ArrayList<>();
        imgResources = new ArrayList<>();
        for (int i = 0;i<size;i++){
            //图片缩略图
            String s_p_resource_thumbnail_url = servicePointMessageResources.get(i)
                    .getS_p_resource_thumbnail_url();
            String s_p_resource_url = servicePointMessageResources.get(i).getS_p_resource_url();
            if (!TextUtils.isEmpty(s_p_resource_thumbnail_url)){
                imgList.add(imageUrl + s_p_resource_thumbnail_url);
            }
            if (!TextUtils.isEmpty(s_p_resource_url)){
                imgResources.add(imageUrl + s_p_resource_url);
            }
        }
        showPicture();
    }

    @Override
    public void setServiceConnection(ServiceConnection conn) {
        this.conn = conn;
    }

    @Override
    public void setMusicService(MusicService musicService) {
        this.musicService = musicService;
        musicService.setOnMusicListener(this);
    }

    @Override
    public MapView getMapView() {
        return mapView;
    }
    @Override
    public void showTabData(List<ScenicList> tabs) {
        adapter = new MapServiceListAdapter(mContext,tabs);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

    }

    @Override
    public String getSessionId() {
        return sessionId;
    }

    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public void setSessionId(String sessionId) {
        sessionIdS = sessionId;
    }



    /*1.初始化定位*/
    private void initLocation() {
        /*声明AMapLocationClient*/
        aMapLocationClient = new AMapLocationClient(mContext);
        /*设置定位回调监听器*/
        aMapLocationClient.setLocationListener(this);
    }


    @Override
    public void setPresenter(PrivilegeContract.Presenter presenter) {
        mPresenter = Preconditions.checkNotNull(presenter);
    }

    @Override
    public void showError(String message) {
        EventUtil.showToast(mContext,message);
    }
    /*添加覆盖物*/
    private void addMarkers(int position) {
        /**
         * 添加覆盖物的相关属性：
         * anchor图标摆放在地图上的基准点。默认情况下，锚点是从图片下沿的中间处。
         * position(Required) 在地图上标记位置的经纬度值。参数不能为空。
         * title 当用户点击标记，在信息窗口上显示的字符串。
         * snippet 附加文本，显示在标题下方。
         * visible 设置“ false ”，标记不可见。默认情况下为“ true ”。
         * perspective设置 true，标记有近大远小效果。默认情况下为 false。
         * 可以通过Marker.setRotateAngle() 方法设置标记的旋转角度，从正北开始，逆时针计算。
         * 如设置旋转90度，Marker.setRotateAngle(90)；
         * 通过setFlat() 方法设置标志是否贴地显示。默认情况下为“false”，不贴地显示。Marker.setFlat(true)；
         */
        List<MarkerInfo> markerInfo = mPresenter.getGaoDeMarkerInfo(position);
        if(markerInfo.size()>0&&markerInfo != null){
            CameraUpdate mCameraUpdate = CameraUpdateFactory
                    .newCameraPosition(new CameraPosition(
                            new LatLng(markerInfo.get(0).getLatitude(),
                                    markerInfo.get(0).getLongitude()),
                            14,0,0));
            aMap.animateCamera(mCameraUpdate);
        }
        for(int i = 0;i<markerInfo.size();i++){
            /*创建marker的显示图标*/
            BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory
                    .fromResource(markerInfo.get(i).getImgId());
            aMap.addMarker(new MarkerOptions()
                    .anchor(0.5f,0.5f)
                    .position(new LatLng(markerInfo
                            .get(i).getLatitude(),markerInfo.get(i).getLongitude()))
                    .icon(bitmapDescriptor)
            );
        }
        aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                String id = marker.getId();
                Log.e("id", "onMarkerClick: "+id);
                return true;
            }
        });

    }

    /*播放音乐*/
    private void startMusic() {
        play();
        if (imgResources.size() > 0 &&imgResources !=null)
            musicService.play(imgResources.get(imgResources.size()-2));
        startAnim();
    }

    private void startAnim() {
        rotateAnimation = new RotateAnimation(0f,359f, Animation.RELATIVE_TO_SELF,
                0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        rotateAnimation.setDuration(2000);
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        /*匀速*/
        LinearInterpolator linearInterpolator = new LinearInterpolator();
        rotateAnimation.setInterpolator(linearInterpolator);
        ivVoice.startAnimation(rotateAnimation);
    }

    /*音频的播放与暂停*/
    @Override
    public void play() {
        isPlay = true;
    }

    @Override
    public void stop() {
        isPlay = false;
        ivVoice.clearAnimation();
        rotateAnimation.cancel();
    }


    /*登陆事件*/
    @Subscribe(threadMode = ThreadMode.PostThread)
    public void handleLogin(LoginEvent loginEvent ){
        String sessionId = loginEvent.getSessionId();
        String user_id = loginEvent.getUser_id();
        userName = loginEvent.getUserName();
        userPhone = loginEvent.getUserPhone();
        this.sessionId = sessionId;
        userId = user_id;
    }
    /*event退出登陆事件*/
    @Subscribe(threadMode = ThreadMode.PostThread)
    public void handleLoginQuit(LoginQuit loginQuit){
        userName = loginQuit.getUserName();
        userPhone = loginQuit.getUsePhone();
        sessionIdS = null;
    }
    /*点击事件*/
    @OnClick({R.id.iv_map_switch,
            R.id.iv_map_server,R.id.iv_map_location,
            R.id.iv_map_music_control,R.id.iv_map_footprint,R.id.iv_map_help,
            R.id.iv_map_my_location,R.id.tv_network_refresh,R.id.iv_map_big,R.id.iv_map_small})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_dialog_map_user_phone://联系人
                if (!TextUtils.isEmpty(userPhone)&&!TextUtils.isEmpty(userName)||
                        !TextUtils.isEmpty(sessionIdS)){
                    Uri uri = Uri.parse("tel:15088659081");//默认的号码
                    Intent intent = new Intent(Intent.ACTION_CALL, uri);
                    if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE)
                            != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    mContext.startActivity(intent);
                }else {
                    showLoginDialog();
                }

                break;
            case R.id.iv_map_switch://地图切换
                if (!TextUtils.isEmpty(userPhone)&&!TextUtils.isEmpty(userName)||
                        !TextUtils.isEmpty(sessionIdS)){
                    if(!isGaoDe){
                        webView.setVisibility(GONE);
                        mapView.setVisibility(VISIBLE);
                        ivMapLocation.setVisibility(VISIBLE);
                        ivMapSwitch.setImageResource(R.mipmap.ic_map_freehand_sketching);
                        isGaoDe = true;
                        ToastUtil.showToast(mContext,mContext.getResources().getString(R.string.isGaode));
                    }else {
                        webView.setVisibility(VISIBLE);
                        mapView.setVisibility(GONE);
                        ivMapLocation.setVisibility(GONE);
                        ivMapSwitch.setImageResource(R.mipmap.ic_map_map_switch);
                        isGaoDe = false;
                        ToastUtil.showToast(mContext,mContext.getResources().getString(R.string.isFreehand));
                    }
                    if(isGaoDe){
                        if (aMap != null){
                            aMap.clear();
                        }
                        addMarkers(0);
                    }
                }else {
                    showLoginDialog();
                }
                break;
            case R.id.iv_map_server://景点选择
                servicePopupWindow.showAtLocation(ivMapService,
                        Gravity.getAbsoluteGravity(Gravity.RIGHT,Gravity.RELATIVE_LAYOUT_DIRECTION),
                        ScreenUtil.dip2px(mContext,20)+mapServiceWidth,0);
                break;
            case R.id.iv_map_location://定位当前位置
                initMap();
                View viewDialog = LayoutInflater.from(mContext).inflate(R.layout.map_dialog,null,false);
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext) ;
                builder.setView(viewDialog);
                TextView tvLongitude = ButterKnife.findById(viewDialog,R.id.tv_longitude);
                TextView tvLatitude = ButterKnife.findById(viewDialog,R.id.tv_latitude);
                tvLongitude.setText("经度:" + longitude);
                tvLatitude.setText("纬度:" + latitude);
                builder.show();
                break;
            case R.id.iv_map_music_control://语音开关控制

                if (!TextUtils.isEmpty(userPhone)&&!TextUtils.isEmpty(userName)||
                        !TextUtils.isEmpty(sessionIdS)){
                    //on_off_invented_guide(boolean) true表示开启，false表示关闭
                    if(!isVoice){
                        ivMapVoiceControl.setImageResource(R.mipmap.ic_map_voice_cheched);
                        webView.loadUrl("javascript:on_off_invented_guide('" + isVoice + "')");
                        isVoice = true;
                        ToastUtil.showToast(mContext,mContext.getResources().getString(R.string.isVoice));
                    }else {
                        ivMapVoiceControl.setImageResource(R.mipmap.ic_map_voice);
                        webView.loadUrl("javascript:on_off_invented_guide('" + isVoice + "')");
                        isVoice = false;
                        ToastUtil.showToast(mContext,mContext.getResources().getString(R.string.notVoice));
                    }
                }else {
                    showLoginDialog();
                }
                break;
            case R.id.iv_map_popup_video://视频
                if (!TextUtils.isEmpty(userPhone)&&!TextUtils.isEmpty(userName)||
                        !TextUtils.isEmpty(sessionIdS)){
                    if (imgResources != null &&imgResources.size()>0){
                        jumpIntent.setClass(mContext, VideoActivity.class);
                        jumpIntent.putExtra("videoUrl",imgResources.get(imgResources.size()-1));
                        mContext.startActivity(jumpIntent);
                        ((MainActivity) mContext).overridePendingTransition(android.R.anim.fade_in,
                                android.R.anim.fade_out);
                    }
                }else {
                    showLoginDialog();
                }
                break;
            case R.id.iv_map_popup_voice://音频
                if (!TextUtils.isEmpty(userPhone)&&!TextUtils.isEmpty(userName)||
                        !TextUtils.isEmpty(sessionIdS)){
                    if (!isPlay){
                        startMusic();
                    }else {
                        musicService.stop();
                    }
                }else {
                    showLoginDialog();
                }

                break;
            case R.id.btn_dialog_map_navigator://导航

                break;
            case R.id.iv_close://关闭景点弹窗
                if(scenicPopupWindow.isShowing()){
                    scenicPopupWindow.dismiss();
                }
                break;
            case R.id.iv_map_footprint:
                if (!TextUtils.isEmpty(userPhone)&&!TextUtils.isEmpty(userName)||
                        !TextUtils.isEmpty(sessionIdS)){
                    JumpUtil.jump(mContext, FootprintActivity.class);
                    ((MainActivity) mContext).overridePendingTransition(android.R.anim.fade_in,
                            android.R.anim.fade_out);
                }else {
                    showLoginDialog();
                }

                break;
            case R.id.iv_map_help://求救
                if (!TextUtils.isEmpty(userPhone)&&!TextUtils.isEmpty(userName)||
                        !TextUtils.isEmpty(sessionIdS)){
                    if(!isHelp){
                        isHelp = true;
                    }else {
                        isHelp = false;
                    }
                    if (cbHospitalHelp.isChecked()){
                        cbHospitalHelp.setChecked(false);
                    }
                    if (cbPoliceHelp.isChecked()){
                        cbPoliceHelp.setChecked(false);
                    }
                    helpPopupWindow.showAtLocation(this,Gravity.CENTER,0,0);
                }else {
                    showLoginDialog();
                }

                break;
            case R.id.iv_map_my_location://定位开启
                if (!TextUtils.isEmpty(userPhone)&&!TextUtils.isEmpty(userName)||
                        !TextUtils.isEmpty(sessionIdS)){
                    if (!isLocation){
                        isLocation = true;
                        webView.loadUrl("javascript:on_off_group_member_location()");
                        ivMyFriendLocation.setImageResource(R.mipmap.ic_map_location_web);
                        ToastUtil.showToast(mContext,mContext.getResources().getString(R.string.notLocation));
                    }else {
                        isLocation = false;
                        webView.loadUrl("javascript:on_off_group_member_location()");
                        ivMyFriendLocation.setImageResource(R.mipmap.ic_map_location_web_checked);
                        ToastUtil.showToast(mContext,mContext.getResources().getString(R.string.isLocation));
                    }
                }else {
                    showLoginDialog();
                }
                break;
            case R.id.btn_popup_help://求助
                if (tag!=null&&tag.equals("0") && cbHospitalHelp.isChecked()){//医疗求助
                    Uri uriHospital = Uri.parse("tel:120");//默认的号码
                    Intent intentHospital = new Intent(Intent.ACTION_CALL, uriHospital);
                    if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE)
                            != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    mContext.startActivity(intentHospital);
                }else if (tag!=null&&tag.equals("1") && cbPoliceHelp.isChecked()){//报警求助
                    Uri uriPolice = Uri.parse("tel:110");//默认的号码
                    Intent intentPolice = new Intent(Intent.ACTION_CALL, uriPolice);
                    if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE)
                            != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    mContext.startActivity(intentPolice);
                }
                break;
            case R.id.iv_dialog_map_service_facility_phone://服务设施电话

                break;

            case R.id.btn_dialog_cancel://对话框取消
                alertDialog.cancel();
                break;
            case R.id.btn_dialog_confirm://对话框确定
                alertDialog.cancel();
                JumpUtil.jump(mContext, LoginActivity.class);
                break;
            case R.id.tv_network_refresh://网络连接刷新
                if (!NetWorkUtil.isNetWorkAvailable(mContext)){
                    rlNetwork.setVisibility(VISIBLE);
                    rlWeb.setVisibility(GONE);
                }else {
                    rlNetwork.setVisibility(GONE);
                    rlWeb.setVisibility(VISIBLE);

                }
                break;
            case R.id.iv_map_big://放大
                webView.loadUrl("javascript:amplify_map()");
                break;
            case R.id.iv_map_small://缩小
                webView.loadUrl("javascript:reduce_map()");
                break;
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        EventBus.getDefault().register(this);
        if (!aMapLocationClient.isStarted()){
            aMapLocationClient.startLocation();
        }
        //2.1开启辅助定位
        aMapLocationClient.startAssistantLocation();
    }

    @Override
    protected void onDetachedFromWindow() {
        if (aMapLocationClient.isStarted()){
            aMapLocationClient.stopLocation();
        }
        //2.2结束定位
        aMapLocationClient.stopAssistantLocation();
        aMapLocationClient.onDestroy();
        EventBus.getDefault().unregister(this);
        webView.destroy();
        if (unbinder!=null){
            unbinder.unbind();
            unbinder = null;
        }
        mContext = null;
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        if(mapView != null){
            mapView.onDestroy();
        }
        super.onDetachedFromWindow();
    }
    /*定位监听*/
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null){
            if (aMapLocation.getErrorCode() == 0){
                latitude = aMapLocation.getLatitude();
                longitude = aMapLocation.getLongitude();
                ((WebMapActivity)mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mPresenter.getMapData(latitude, longitude);
                    }
                });
            }else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，
                // 详见错误码表。
                Log.e("AMapError","location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }
        }
    }
    /*aMap监听*/
    @Override
    public void onMyLocationChange(Location location) {

    }

    /*cardAdapter点击事件*/
    @Override
    public void onClick(int position) {

    }
    /*ListView景点类型点击事件*/
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (isGaoDe){
            if (aMap != null){
                aMap.clear();
            }
            addMarkers(position);
        }else {
            int realPosition = position + 1;
            webView.loadUrl("javascript:show_service_point_by_service_type('" + realPosition + "')");
        }
        adapter.setCurrentPosition(position);
        adapter.notifyDataSetChanged();
    }
    /*checkBox监听*/
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        String tag = (String) buttonView.getTag();
        switch (tag){
            case "0"://医疗求助
                if (isChecked){
                    this.tag = tag;
                    if (cbPoliceHelp.isChecked())
                        cbPoliceHelp.setChecked(false);
                    btnHelp.setTextColor(mContext.getResources().getColor(R.color.black));
                }else {
                    btnHelp.setTextColor(mContext.getResources().getColor(R.color.scenicGrayColor));
                }
                break;
            case "1"://报警求助
                if (isChecked){
                    this.tag = tag;
                    if (cbHospitalHelp.isChecked())
                        cbHospitalHelp.setChecked(false);
                    btnHelp.setTextColor(mContext.getResources().getColor(R.color.black));
                }else {
                    btnHelp.setTextColor(mContext.getResources().getColor(R.color.scenicGrayColor));
                }
                break;
        }
    }
}
