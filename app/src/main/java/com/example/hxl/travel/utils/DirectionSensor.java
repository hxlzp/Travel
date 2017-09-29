package com.example.hxl.travel.utils;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by hxl on 2017/3/31 at haiChou.
 * 方向传感器
 */
public class DirectionSensor implements SensorEventListener{
    private Context context;
    /**
     * 传感器管理器对象
     */
    private SensorManager sensorManager;
    private Sensor aSensor;
    private Sensor mSensor;
    float[] accelerometerValues = new float[3];
    float[] magneticFieldValues = new float[3];
    //设置对外访问的接口
    public interface OnDirectionChangedListener{
        public void onDirectionChanged(float degree);
    }
    private OnDirectionChangedListener onDirectionChangedListener;
    //设置对外访问的方法


    public void setOnDirectionChangedListener(OnDirectionChangedListener
                                                      onDirectionChangedListener) {
        this.onDirectionChangedListener = onDirectionChangedListener;
    }

    public DirectionSensor(Context context) {
        this.context = context;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        //判断是否是方向传感器
        if (Sensor.TYPE_ACCELEROMETER == sensorEvent.sensor.TYPE_ACCELEROMETER){
            /**
             * values[0]表示方位，也就是手机绕着Z轴旋转的角度
             * values[1]：该值表示倾斜度，或手机翘起的程度
             * values[2]：表示手机沿着Y轴的滚动角度
             */
            if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
                magneticFieldValues = sensorEvent.values;
            if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
                accelerometerValues = sensorEvent.values;
            calculateOrientation();
        }
    }

    /**
     * 开启传感器
     */
    public void start(){
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        aSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        if (aSensor!=null&&mSensor!=null){
            //使用传感器更新UI中的数据SENSOR_DELAY_UI
            sensorManager.registerListener(this,aSensor,SensorManager.SENSOR_DELAY_UI);
            sensorManager.registerListener(this,mSensor,SensorManager.SENSOR_DELAY_UI);
            //更新显示数据的方法
            calculateOrientation();
        }
    }

    private void calculateOrientation() {
        float[] values = new float[3];
        //R[] 是一个旋转矩阵，用来保存磁场和加速度的数据
        float[] R = new float[9];
        /**
         * 第一个参数需要填充的R数组，大小是9
         * 第二个是是一个转换矩阵，将磁场数据转换进实际的重力坐标中 一般默认情况下可以设置为null
         * 第三个是一个大小为3的数组，表示从加速度感应器获取来的数据  在onSensorChanged中
         * 第四个是一个大小为3的数组，表示从磁场感应器获取来的数据    在onSensorChanged中
         */
        SensorManager.getRotationMatrix(R, null, accelerometerValues, magneticFieldValues);
        SensorManager.getOrientation(R, values);
        // 要经过一次数据格式的转换，转换为度
        values[0] = (float) Math.toDegrees(values[0]);
        float currentDegree = values[0];
        if (onDirectionChangedListener != null){
            onDirectionChangedListener.onDirectionChanged(currentDegree);
        }
    }

    /**
      */
    public void stop(){
        sensorManager.unregisterListener(this);
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
