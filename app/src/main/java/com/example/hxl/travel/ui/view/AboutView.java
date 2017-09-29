package com.example.hxl.travel.ui.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.hxl.travel.R;
import com.example.hxl.travel.base.RootView;
import com.example.hxl.travel.presenter.contract.AboutContract;
import com.example.hxl.travel.ui.activitys.AboutActivity;
import com.example.hxl.travel.utils.EventUtil;
import com.example.hxl.travel.utils.QRCode;
import com.google.common.base.Preconditions;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by hxl on 2017/1/6 at haiChou.
 */
public class AboutView extends RootView<AboutContract.Presenter>
        implements AboutContract.View ,View.OnClickListener{
    @BindView(R.id.iv_activity_about_icon)
    ImageView imageView ;
    private Bitmap bitmap;

    public AboutView(Context context) {
        super(context);
    }

    public AboutView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AboutView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void getLayout() {
        inflate(mContext, R.layout.activity_about_view,this);
    }

    @Override
    protected void initView() {
        bitmap = QRCode.Create2QR2(mContext, "https://www.baidu.com/", R.mipmap.ic_launcher);
        imageView.setImageBitmap(bitmap);
    }

    @Override
    protected void initEvent() {

    }
    @Override
    public void setPresenter(AboutContract.Presenter presenter) {
        mPresenter = Preconditions.checkNotNull(presenter);
    }

    @Override
    public void showError(String message) {
        EventUtil.showToast(mContext,message);
    }

    @Override
    public boolean isActive() {
        return mActive;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        unbinder.unbind();
        mContext = null;
        if (!bitmap.isRecycled()&&bitmap!=null){
            bitmap.recycle();
            bitmap = null;
        }
    }

    @OnClick({R.id.iv_activity_about_back})
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_activity_about_back:
                ((AboutActivity)mContext).finish();
            break;

        }

    }
}
