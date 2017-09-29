package com.example.hxl.travel.ui.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import java.io.IOException;

public class MusicService extends Service {

    private MediaPlayer mediaPlayer;
//    private NotificationUtil notificationUtil;
//    private ExitReceiver exitReceiver;
//    private PlayReceiver playReceiver;
//    private String path;
//    private boolean isFirst = true;

    public MusicService() {

    }
    /*自定义Binder类，将暴露该类的方法写在该类中*/
    public class MyBinder extends Binder{
        public MusicService getMusicService(){
            return MusicService.this;
        }
    }
    /*实例化该类*/
    MyBinder myBinder = new MyBinder() ;
    @Override
    public IBinder onBind(Intent intent) {
//        notificationUtil = new NotificationUtil(MusicService.this);
//        IntentFilter exitIntent = new IntentFilter() ;
//        exitReceiver = new ExitReceiver();
//        exitIntent.addAction("com.app.onExit");
//        registerReceiver(exitReceiver,exitIntent);
//
//        IntentFilter playIntent = new IntentFilter() ;
//        playReceiver = new PlayReceiver();
//        playIntent.addAction("com.app.onclick");
//        registerReceiver(playReceiver,playIntent);
       return myBinder;
    }
    public void play(String path){
        //this.path = path;
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(path);
            if (mediaPlayer!=null&&mediaPlayer.isPlaying()){
                mediaPlayer.stop();
                mediaPlayer.reset();
            }
            /*在start之前，需要先prepare*/
            mediaPlayer.setOnPreparedListener(onPreparedListener);
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    MediaPlayer.OnPreparedListener onPreparedListener = new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mediaPlayer) {
            onMusicListener.play();
            mediaPlayer.start();
            //notificationUtil.postNotification("title", R.mipmap.photo,true);
            //notificationUtil.show();
        }
    };
    public void stop(){
        if (mediaPlayer!=null&&mediaPlayer.isPlaying()){
            onMusicListener.stop();
            mediaPlayer.stop();
            mediaPlayer.reset();
            //notificationUtil.postNotification("title",R.mipmap.photo,false);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mediaPlayer!=null){
            mediaPlayer.release();
        }
        //notificationUtil.cancelById();
        //unregisterReceiver(exitReceiver);
        //unregisterReceiver(playReceiver);

    }
//    public class ExitReceiver extends BroadcastReceiver {
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            notificationUtil.cancelById();
//            stop();
//        }
//    }

//    public class PlayReceiver extends BroadcastReceiver {
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            if (mediaPlayer.isPlaying()&&isFirst){
//                stop();
//                notificationUtil.show();
//                isFirst = false;
//            }else if (path!=null&&!path.equals("")&&!isFirst){
//                play(path);
//                isFirst = true;
//            }
//        }
//    }
    public interface OnMusicListener{
        void play();
        void stop();
    }
    private OnMusicListener onMusicListener ;

    public void setOnMusicListener(OnMusicListener onMusicListener) {
        this.onMusicListener = onMusicListener;
    }
}
