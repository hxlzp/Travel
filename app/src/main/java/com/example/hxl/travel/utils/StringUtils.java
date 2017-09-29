package com.example.hxl.travel.utils;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by hxl on 2016/12/22 at haiChou.
 */
public class StringUtils {
    /**
     * 去掉特殊字符
     */
    public static String removeOtherCode(String s) {
        if (TextUtils.isEmpty(s))
            return "";
        s = s.replaceAll("\\<.*?>|\\n", "");
        return s;
    }

    /**
     * 判断非空
     */
    public static String isEmpty(String str) {
        String result = TextUtils.isEmpty(str) ? "" : str;
        return result;
    }

    /**
     * 得到一个字符串的长度,显示的长度,一个汉字或日韩文长度为2,英文字符长度为1
     */
    public static int length(String s) {
        if (s == null)
            return 0;
        char[] c = s.toCharArray();
        int len = 0;
        for (int i = 0; i < c.length; i++) {
            len++;
            if (!isLetter(c[i])) {
                len++;
            }
        }
        return len;
    }
    public static boolean isLetter(char c) {
        int k = 0x80;
        return c / k == 0 ? true : false;
    }
    /**
     * 根据Url获取catalogId
     */
    public static String getCatalogId(String url) {
        String catalogId = "";
        if (!TextUtils.isEmpty(url) && url.contains("="))
            catalogId = url.substring(url.lastIndexOf("=") + 1);
        return catalogId;
    }

    public static int getRandomNumber(int number) {
        //随机产生一个0-number的整数
        return new Random().nextInt(number);
    }

    public static String getErrorMsg(String msg) {
        if (msg.contains("*")) {
            msg = msg.substring(msg.indexOf("*") + 1);
            return msg;
        } else
            return "";
    }

}
