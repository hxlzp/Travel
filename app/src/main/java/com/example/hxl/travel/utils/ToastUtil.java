package com.example.hxl.travel.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by hxl on 2017/4/6 at haiChou.
 */
public class ToastUtil {
    public static void showToast(Context context,String content){
        Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
    };
}
