package com.example.hxl.travel.ui.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.widget.MediaController;

import com.example.hxl.travel.R;
import com.example.hxl.travel.base.RootView;
import com.example.hxl.travel.presenter.contract.VideoContract;
import com.example.hxl.travel.ui.activitys.VideoActivity;
import com.google.common.base.Preconditions;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by hxl on 2017/4/20 at haiChou.
 */
public class VideoView extends RootView<VideoContract.Presenter> implements VideoContract.View,
        View.OnClickListener{
    @BindView(R.id.vv_video)
    android.widget.VideoView videoView ;
    private MediaController mediaController;
    private String videoUrl;

    public VideoView(Context context) {
        super(context);
    }

    public VideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void getLayout() {
        inflate(mContext, R.layout.activity_video_view,this);
    }

    @Override
    protected void initView() {
        Intent intent = ((VideoActivity) mContext).getIntent();
        videoUrl = intent.getStringExtra("videoUrl");
    }

    @Override
    protected void initEvent() {

    }

    @Override
    public boolean isActive() {
        return mActive;
    }

    @Override
    public Context getVideoContext() {
        return mContext;
    }

    @Override
    public void showVideoData(String url) {
        mediaController = new MediaController(mContext);
        videoView.setMediaController(mediaController);
        mediaController.setMediaPlayer(videoView);
        videoView.setVideoURI(Uri.parse(url));
        videoView.start();
    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public String getVideoUrl() {
        return videoUrl;
    }

    @Override
    public void setPresenter(VideoContract.Presenter presenter) {
        mPresenter = Preconditions.checkNotNull(presenter);
    }

    @Override
    public void showError(String message) {

    }
    @OnClick({R.id.iv_video_back})
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_video_back:
                ((VideoActivity)mContext).finish();
                ((VideoActivity) mContext).overridePendingTransition(android.R.anim.fade_in,
                        android.R.anim.fade_out);
            break;
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (unbinder!=null){
            unbinder.unbind();
            unbinder = null;
        }
        mContext = null;
    }
}
