package com.example.hxl.travel.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.example.hxl.travel.R;
import com.example.hxl.travel.base.RxPresenter;
import com.example.hxl.travel.presenter.contract.VideoContract;
import com.example.hxl.travel.utils.EventUtil;
import com.example.hxl.travel.utils.NetWorkUtil;
import com.google.common.base.Preconditions;

/**
 * Created by hxl on 2017/4/20 at haiChou.
 */
public class VideoPresenter extends RxPresenter implements VideoContract.Presenter{
    private VideoContract.View mView;
    private final Context videoContext;

    private String videoUrl =
            "http://vf1.mtime.cn/Video/2016/05/11/mp4/160511092153821141.mp4";
    public VideoPresenter(VideoContract.View view) {
        mView = Preconditions.checkNotNull(view);
        mView.setPresenter(this);
        videoContext = mView.getVideoContext();
        getVideoData();
    }

    @Override
    public void getVideoData() {

        if (!NetWorkUtil.isNetWorkAvailable(videoContext)){
            EventUtil.showToast(videoContext,videoContext.getResources().getString(R.string.netFail));
        }
        String videoUrl = mView.getVideoUrl();
        if (!TextUtils.isEmpty(videoUrl))
         mView.showVideoData(this.videoUrl);
    }
}
