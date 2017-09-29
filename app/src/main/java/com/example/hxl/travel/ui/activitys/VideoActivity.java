package com.example.hxl.travel.ui.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.hxl.travel.R;
import com.example.hxl.travel.base.BaseActivity;
import com.example.hxl.travel.presenter.VideoPresenter;
import com.example.hxl.travel.ui.view.VideoView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoActivity extends BaseActivity {
    @BindView(R.id.video_view)
    VideoView videoView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        unbinder = ButterKnife.bind(this);
        mPresenter = new VideoPresenter(videoView);
    }
}
