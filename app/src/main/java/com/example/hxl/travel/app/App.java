package com.example.hxl.travel.app;

import android.app.Activity;
import android.app.Application;
import android.os.Process;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by hxl on 2016/12/21 at haiChou.
 */
public class App extends Application {
    private static App instance;
    private Set<Activity> allActivities ;

//    protected void attachBaseContext(Context base) {
//        super.attachBaseContext(base);
//        MultiDex.install(this) ;
//    }

    /**
     * 获得Application实例
     */
    public static App getInstance(){
        return instance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
    public void registerActivity(Activity activity){
        if (allActivities == null){
            allActivities = new HashSet<>();
        }
        allActivities.add(activity);
    }
    public void unregisterActivity(Activity activity){
        if (allActivities!=null){
            allActivities.remove(activity);
        }
    }
    public void exitApp(){
        if (allActivities!=null){
            synchronized (allActivities){
                for (Activity activity:allActivities){
                    if (activity!=null&&!activity.isFinishing()){
                        activity.finish();
                    }
                }
            }
        }
        android.os.Process.killProcess(Process.myPid());
        System.exit(0);
    }
}
