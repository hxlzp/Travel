package com.example.hxl.travel.model.net;

import com.example.hxl.travel.BuildConfig;
import com.example.hxl.travel.app.App;
import com.example.hxl.travel.app.Constants;
import com.example.hxl.travel.model.bean.FootprintMessageList;
import com.example.hxl.travel.model.bean.GroupAdd;
import com.example.hxl.travel.model.bean.GroupBuild;
import com.example.hxl.travel.model.bean.GroupList;
import com.example.hxl.travel.model.bean.GroupMember;
import com.example.hxl.travel.model.bean.Login;
import com.example.hxl.travel.model.bean.MapMessage;
import com.example.hxl.travel.model.bean.Register;
import com.example.hxl.travel.model.bean.ServicePointMessage;
import com.example.hxl.travel.utils.NetWorkUtil;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import rx.Observable;

/**
 * Created by hxl on 2017/1/21 at haiChou.
 */
public class RetrofitHelper {
    private static OkHttpClient okHttpClient = null;
    public static String url = "http://112.12.36.86:8008";
    private static Retrofit retrofit;
    private static RetrofitApi retrofitApi;
    private static RetrofitApi retrofitApiCookie;
    /*足迹*/
    public static String footprintUrl = "/travel_app/html/daily_map.html";

    private static void intiRetrofit(){
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitApi = retrofit.create(RetrofitApi.class);
    }
    /*Cookie*/
    private static void initCookieRetrofit(String sessionId){

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
                .addInterceptor(new AddCookiesInterceptor(sessionId))
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(url)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitApiCookie = retrofit.create(RetrofitApi.class);
    }

    /**
     * 注册
     */
    public static Observable<Register> registerObservable (@Field("userName") String userName, @Field("password") String password){
        intiRetrofit();
        Observable<Register> registerObservable = retrofitApi.register(userName, password);
        return registerObservable;
    }
    /**
     * 登陆
     */
    public static Observable<Login> loginObservable (@Field("userName") String userName, @Field("password") String password){
        intiRetrofit();
        Observable<Login> loginObservable = retrofitApi.login(userName, password);
        return loginObservable;
    }
    /**
     * 创建群组
     */
    public static Observable<GroupBuild> groupBuildObservable (@Field("user_id") String user_id,@Field("group_nickname") String group_nickname,String sessionId){
        initCookieRetrofit(sessionId);
        Observable<GroupBuild> groupBuildObservable = retrofitApiCookie.groupBuild(user_id, group_nickname);
        return groupBuildObservable;
    }
    /**
     * 加入群组
     */
    public static Observable<GroupAdd> groupAddObservable (@Field("user_id") String user_id,@Field("group_nickname") String group_nickname,String sessionId){
        initCookieRetrofit(sessionId);
        Observable<GroupAdd> groupAddObservable = retrofitApiCookie.observableGroupAdd(user_id, group_nickname);
        return groupAddObservable;
    }
    /**
     * 邀请入群
     */
    public static Observable<GroupAdd> groupInviteObservable(@Field("group_id") String group_id,@Field("user_name") String user_name,String sessionId){
        initCookieRetrofit(sessionId);
        Observable<GroupAdd> groupInviteObservable = retrofitApiCookie.observableGroupInvite(group_id, user_name);
        return groupInviteObservable;
    }
    /**
     * 获取群列表
     */
    public static Observable<GroupList> groupListObservable (@Field("user_id ") String user_id,String sessionId){
        initCookieRetrofit(sessionId);
        Observable<GroupList> groupListObservable = retrofitApiCookie.observableGroupList(user_id);
        return groupListObservable;
    }
    /**
     * 获取群成员列表
     */
    public static Observable<GroupMember> groupMemberObservable (@Field("group_id") String group_id,String sessionId){
        initCookieRetrofit(sessionId);
        Observable<GroupMember> groupMemberObservable = retrofitApiCookie.observableGroupMember(group_id);
        return groupMemberObservable;
    }
    /**
     * 退出群组
     */
    public static Observable<GroupAdd> groupQuitObservable(@Field("group_id") String group_id,@Field("user_id") String user_id,String sessionId){
        initCookieRetrofit(sessionId);
        Observable<GroupAdd> groupQuitObservable = retrofitApiCookie.observableQuitGroup(group_id, user_id);
        return groupQuitObservable;
    }
    /**
     * 解散群组
     */
    public static Observable<GroupAdd> groupDissolveObservable(@Field("group_id") String group_id,String sessionId){
        initCookieRetrofit(sessionId);
        Observable<GroupAdd> groupDissolveObservable = retrofitApiCookie.observableGroupDissolve(group_id);
        return groupDissolveObservable;
    }
    /**
     * 群主踢出成员
     */
    public static Observable<GroupAdd> groupDeleteObservable (@Field("group_id") String group_id,@Field("user_ids") String user_ids,String sessionId){
        initCookieRetrofit(sessionId);
        Observable<GroupAdd> groupDeleteObservable = retrofitApiCookie.observableGroupDelete(group_id, user_ids);
        return groupDeleteObservable;
    }
    /**
     * 获取百度地图信息
     */
    public static Observable<MapMessage> mapMessageObservable(@Field("user_id") String user_id, @Field("group_id") String group_id, @Field("user_name") String user_name,
                                                              @Field("longitude") String longitude, @Field("latitude") String latitude, @Field("user_type") String user_type,
                                                              @Field("interval_time") String interval_time, @Field("view_time") String view_time){
        intiRetrofit();
        Observable<MapMessage> mapMessageObservable = retrofitApi.observableMapMessage(user_id, group_id, user_name, longitude, latitude, user_type, interval_time, view_time);
        return mapMessageObservable;
    }

    /**
     * 足迹列表
     */
    public static Observable<FootprintMessageList> footprintMessageListObservable(@Field("scenicName") String scenicName){
        intiRetrofit();
        Observable<FootprintMessageList> footprintMessageListObservable = retrofitApi.observableFootprintMessageList(scenicName);
        return footprintMessageListObservable;
    }

    /*服务点景点信息*/
    public static Observable<ServicePointMessage> servicePointMessageObservable(@Field("servicePointId") String servicePointId){
        intiRetrofit();
        Observable<ServicePointMessage> servicePointMessageObservable = retrofitApi.observableServicePointMessage(servicePointId);
        return servicePointMessageObservable;
    }


    private static void initOkHttp() {
        if (okHttpClient == null){
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            if (BuildConfig.DEBUG){
                HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
                httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
                builder.addInterceptor(httpLoggingInterceptor);
            }
            //设置缓存
            File file = new File(Constants.PATH_CACHE);
            //缓存大小为10M
            Cache cache = new Cache(file,1024 * 1024 * 10);
            final boolean netWorkAvailable = NetWorkUtil.isNetWorkAvailable(App.getInstance().getApplicationContext());
            /**
             * 拦截 Request 做一些你想做的事情再送出去
             * 拦截器是 OkHttp 提供的对 HTTP 请求和响应进行统一处理的强大机制。
             * 拦截器在实现和使用上类似于 Servlet 规范中的过滤器。多个拦截器可以链接起来，形成一个链条。
             * 拦截器会按照在链条上的顺序依次执行。 拦截器在执行时，可以先对请求的 Request 对象进行修改；
             * 再得到响应的 Response 对象之后，可以进行修改之后再返回。
             * Interceptor 接口只包含一个方法 intercept，其参数是 Chain 对象。Chain 对象表示的是当前的拦截器链条。
             * 通过 Chain 的 request 方法可以获取到当前的 Request 对象。
             * 在使用完 Request 对象之后，通过 Chain 对象的 proceed 方法来继续拦截器链条的执行。
             * 当执行完成之后，可以对得到的 Response 对象进行额外的处理
             */
            Interceptor interceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    if (!netWorkAvailable){
                        request = request.newBuilder()
                                .cacheControl(CacheControl.FORCE_CACHE)
                                .build();
                    }
                    int tryCount = 0;
                    Response response = chain.proceed(request);
                    while (!response.isSuccessful() && tryCount > 3){
                        tryCount++;
                        // retry the request
                        response = chain.proceed(request);
                    }
                    if (netWorkAvailable){
                        int maxAge = 0;
                        // 有网络时, 不缓存, 最大保存时长为0
                        response.newBuilder()
                                .header("Cache-Control", "public, max-age=" + maxAge)
                                .removeHeader("Pragma")
                                .build();
                    } else {
                        // 无网络时，设置超时为4周
                        int maxStale = 60 * 60 * 24 * 28;
                        response.newBuilder()
                                .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                                .removeHeader("Pragma")
                                .build();
                    }
                    return response;
                }
            };
            //设置缓存
            builder.addNetworkInterceptor(interceptor)//添加网络拦截器
                    .addInterceptor(interceptor)//添加应用拦截器
                    .cache(cache);
            //设置超时
            builder.connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(20,TimeUnit.SECONDS)
                    .writeTimeout(20,TimeUnit.SECONDS);
            //错误重连
            builder.retryOnConnectionFailure(true);
            okHttpClient = builder.build();
        }
    }
}
