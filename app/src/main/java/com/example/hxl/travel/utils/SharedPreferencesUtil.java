package com.example.hxl.travel.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by hxl on 2017/6/16 at haiChou.
 * 共享参数
 */
public class SharedPreferencesUtil {
    public static SharedPreferences getSharedPreferences(Context context,String s){
        SharedPreferences sharedPreferences = context.getSharedPreferences(s,Context.MODE_PRIVATE);
        return sharedPreferences;
    }
    public static String  getSessionId(Context context){
        SharedPreferences sharedPreferences = getSharedPreferences(context, "user");
        String sessionId = sharedPreferences.getString("sessionId", null);
        return sessionId;
    }
    public static String  getUserId(Context context){
        SharedPreferences sharedPreferences = getSharedPreferences(context, "user");
        String userId = sharedPreferences.getString("user_id", null);
        return userId;
    }
}
