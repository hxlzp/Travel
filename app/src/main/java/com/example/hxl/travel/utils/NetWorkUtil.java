package com.example.hxl.travel.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by hxl on 2017/1/21 at haiChou.
 */
public class NetWorkUtil {
    public static boolean isNetWorkAvailable(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo!=null){
            return activeNetworkInfo.isConnected();
        }
        return false;
    }
}
