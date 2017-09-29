package com.example.hxl.travel.utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.NotificationCompat;
import android.widget.RemoteViews;

import com.example.hxl.travel.R;

/**
 * Created by hxl on 2017/8/15 0015 at haichou.
 */

public class NotificationUtil {
    private Context context;
    private NotificationManager notificationManager;
    private NotificationCompat.Builder builder;

    public NotificationUtil(Context context) {
        this.context = context;
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }
    /**
     * 普通的Notification
     */
    public void postNotification(String title,int icon,boolean isPlay){
        RemoteViews remoteViews  = new RemoteViews(context.getPackageName(),
                R.layout.notification_layout);

        remoteViews.setTextViewText(R.id.notification_music_title, title);
        remoteViews.setImageViewResource(R.id.notification_artist_image,icon);

        /*需要跳转制定页面*/
        //Intent intent = new Intent() ;
        //intent.setClass(context, MainActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,
//                PendingIntent.FLAG_UPDATE_CURRENT);
        if(isPlay){
            remoteViews.setImageViewResource(R.id.notification_play_button, R.mipmap.ic_map_music_pause);
        }else{
            remoteViews.setImageViewResource(R.id.notification_play_button, R.mipmap.ic_map_music_play);
        }
        //点击的事件处理
         /* 播放/暂停  按钮 */
        Intent buttonIntent = new Intent("com.app.onclick");
        buttonIntent.putExtra("play", 1);
        PendingIntent intent_paly = PendingIntent.getBroadcast(context, 2, buttonIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.notification_play_button,intent_paly);
        /*退出*/
        Intent exitIntent = new Intent("com.app.onExit");
        PendingIntent intent_exit = PendingIntent.getBroadcast(context, 1, exitIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.notification_exit_button,intent_exit);
        //remoteViews.setOnClickPendingIntent(R.id.notification_exit_button,pendingIntent);
        /*Notification构造器*/
        builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(context.getApplicationInfo().icon);
        builder.setContentTitle(title);
        builder.setContentText(title);
        builder.setTicker(title);
        builder.setAutoCancel(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            builder.setCustomContentView(remoteViews);
        }else {
            builder.setContent(remoteViews);
        }

    }
    public void show(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            notificationManager.notify(0,builder.build());
        }
    }

    public void cancelById() {
        notificationManager.cancel(0);  //对应NotificationManager.notify(id,notification);第一个参数
    }

    public void cancelAllNotification() {
        notificationManager.cancelAll();
    }

}
