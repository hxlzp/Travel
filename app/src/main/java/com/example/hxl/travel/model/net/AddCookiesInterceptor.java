package com.example.hxl.travel.model.net;

import android.content.Context;
import android.util.Log;

import com.example.hxl.travel.utils.SharedPreferencesUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by hxl on 2017/6/20 at haiChou.
 */
public class AddCookiesInterceptor implements Interceptor {
    private String sessionId;
    public AddCookiesInterceptor(String sessionId){
        this.sessionId = sessionId;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        builder.addHeader("Cookie", "JSESSIONID=" + sessionId);
        return chain.proceed(builder.build());
    }
}
