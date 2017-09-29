package com.example.hxl.travel.utils;

import android.content.Context;
import android.content.Intent;

import com.example.hxl.travel.ui.activitys.MainActivity;
import com.example.hxl.travel.ui.activitys.WelcomeActivity;

/**
 * Created by hxl on 2016/12/22 at haiChou.
 */
public class JumpUtil {
    public static void go2MainActivity(Context context) {
        jump(context, MainActivity.class);
        ((WelcomeActivity) context).finish();
    }

    public static void jump(Context a, Class<?> clazz) {
        Intent intent = new Intent(a, clazz);
        a.startActivity(intent);
    }
}
