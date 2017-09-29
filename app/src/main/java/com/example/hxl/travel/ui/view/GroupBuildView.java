package com.example.hxl.travel.ui.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.hxl.travel.R;
import com.example.hxl.travel.base.RootView;
import com.example.hxl.travel.model.bean.Group;
import com.example.hxl.travel.presenter.contract.GroupBuildContract;
import com.example.hxl.travel.ui.activitys.GroupBuildActivity;
import com.example.hxl.travel.utils.EventUtil;
import com.google.common.base.Preconditions;
import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by hxl on 2017/6/8 at haiChou.
 */
public class GroupBuildView extends RootView<GroupBuildContract.Presenter> implements
        GroupBuildContract.View, View.OnClickListener {
    @BindView(R.id.edit_activity_group_build_nickname_edit)
    EditText editName;
    @BindView(R.id.btn_activity_group_build_password_commit)
    Button btnCommit;
    @BindView(R.id.iv_activity_group_build_select)
    RoundedImageView roundedImageView ;
//    @BindView(R.id.edit_activity_group_build__password_edit)
//    EditText editPwd;

    /*popupWindow*/
    private View selectView;
    private PopupWindow selectPopupWindow;
    private int backgroundPop = 0xa0000000;
    private Button btnCamera;
    private Button btnPhoto;
    private Button btnCancel;
    public GroupBuildView(Context context) {
        super(context);
    }

    public GroupBuildView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public GroupBuildView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    protected void getLayout() {
        inflate(mContext, R.layout.activity_group_build_view,this);
    }

    @Override
    protected void initView() {
        //选取图片
        selectView = LayoutInflater.from(mContext).inflate(R.layout.popup_select_pic, null);
        btnCamera = ButterKnife.findById(selectView, R.id.btn_popup_camera);
        btnPhoto = ButterKnife.findById(selectView, R.id.btn_popup_photo);
        btnCancel = ButterKnife.findById(selectView, R.id.btn_popup_cancel);
        selectPopupWindow = new
                PopupWindow(selectView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        //设置popup的背景
        selectPopupWindow.setBackgroundDrawable(new ColorDrawable(backgroundPop));
        selectPopupWindow.setTouchable(true);
        selectPopupWindow.setOutsideTouchable(true);
        selectPopupWindow.setFocusable(true);
        selectPopupWindow.setAnimationStyle(R.style.AnimBottom);

        //设置可点击
        btnCommit.setFocusable(true);
        btnCommit.setClickable(true);
        btnCommit.setEnabled(true);
    }

    @Override
    protected void initEvent() {
        btnCamera.setOnClickListener(this);
        btnPhoto.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }
    @Override
    public boolean isActive() {
        return mActive;
    }

    @Override
    public void showData() {

    }

    @Override
    public Context getGroupBuildContext() {
        return mContext;
    }

    @Override
    public String getGroupNick() {
        //获得用户录入的信息
        String name = editName.getText().toString().trim();
        return name;
    }

    @Override
    public void backPage() {
        back();
    }

    @Override
    public void showMassage(String message) {
        EventUtil.showToast(mContext,message);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public RoundedImageView getRoundedImageView() {
        return roundedImageView;
    }

    @Override
    public void setPresenter(GroupBuildContract.Presenter presenter) {
        mPresenter = Preconditions.checkNotNull(presenter);
    }

    @Override
    public void showError(String message) {
        EventUtil.showToast(mContext,message);
    }
    @OnClick({R.id.iv_activity_group_build_back,R.id.btn_activity_group_build_password_commit,
            R.id.iv_activity_group_build_select})
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_activity_group_build_back://返回
                back();
                break;
            case R.id.btn_activity_group_build_password_commit://提交
                mPresenter.submit(btnCommit);
                break;
            case R.id.iv_activity_group_build_select://弹窗选择图片
                selectPic();
                break;
            case R.id.btn_popup_camera://拍照
                getPicFromCamera();
                break;
            case R.id.btn_popup_photo://从相册中选取
                getPicFromPhoto();
                break;
            case R.id.btn_popup_cancel://取消
                cancelPopup();
                break;
        }
    }
    private void back() {
        ((GroupBuildActivity)mContext).finish();
        ((GroupBuildActivity)mContext).overridePendingTransition(R.anim.enter_in_inactive,
                R.anim.exit_out_active);
    }
    /*从相册中选取*/
    private void getPicFromPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        ((GroupBuildActivity)mContext).startActivityForResult(intent,2);
        cancelPopup();
    }

    /*拍照*/
    private void getPicFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        ((GroupBuildActivity)mContext).startActivityForResult(intent,1);
        cancelPopup();
    }

    /*取消弹窗*/
    private void cancelPopup() {
        selectPopupWindow.dismiss();
    }

    /*选择图片*/
    private void selectPic() {
        selectPopupWindow.showAtLocation(selectView, Gravity.BOTTOM,0,0);
    }
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        unbinder.unbind();
        unbinder = null;
        mContext = null;
    }
}
