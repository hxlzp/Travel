package com.example.hxl.travel.utils;

/**
 * Created by hxl on 2017/4/6 at haiChou.
 * 计算两经纬度之间的距离
 */
public class ComputeTwoPointsUtil {
    /**
     * 地球的半径
     */
    private static final float earthRadius = 6378137.0f;
    private static final double angleRad = Math.PI/180.0;
    /**
     * 计算两经纬度之间的距离 单位mi
     */
    public static long getDistanceFromTwoPoints(double latitude,double longitude,
                                                double endLatitude,double endLongitude){

        double radLatStart = latitude*angleRad;
        double radLatEnd = endLatitude*angleRad;
        double radLngStart = longitude*angleRad;
        double radLngEnd = endLongitude*angleRad;

        double a = radLatStart - radLatEnd;
        double b = radLngStart - radLngEnd;
        double CosRadLatStart = Math.cos(radLatStart);
        double CosRadLatEnd =Math.cos(radLatEnd);
        double aPow=Math.pow(Math.sin(a/2),2);
        double bPow=Math.pow(Math.sin(b/2),2);
        double result=earthRadius*2* Math.asin(Math.sqrt(aPow+CosRadLatStart*CosRadLatEnd*bPow));
        long round = Math.round(result);
        return round;
    }
}
