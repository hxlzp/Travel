package com.example.hxl.travel.ui.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;

import com.example.hxl.travel.R;
import com.example.hxl.travel.base.RootView;
import com.example.hxl.travel.model.event.Age;
import com.example.hxl.travel.model.event.Education;
import com.example.hxl.travel.model.event.Sex;
import com.example.hxl.travel.presenter.contract.PersonInfoContract;
import com.example.hxl.travel.ui.activitys.AgeActivity;
import com.example.hxl.travel.ui.activitys.EducationActivity;
import com.example.hxl.travel.ui.activitys.GroupBuildActivity;
import com.example.hxl.travel.ui.activitys.PersonInfoActivity;
import com.example.hxl.travel.ui.activitys.SexActivity;
import com.example.hxl.travel.utils.EventUtil;
import com.example.hxl.travel.utils.JumpUtil;
import com.example.hxl.travel.widget.citypicker.widget.CityPicker;
import com.google.common.base.Preconditions;
import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**
 * Created by hxl on 2017/1/5 at haiChou.
 */
public class PersonInfoView extends RootView<PersonInfoContract.Presenter>
        implements PersonInfoContract.View,View.OnClickListener,
        CityPicker.OnCityItemClickListener {
    @BindView(R.id.btn_activity_person_sex_edit)
    Button sexBtn;
    @BindView(R.id.btn_activity_person_age_edit)
    Button ageBtn;
    @BindView(R.id.btn_activity_person_education_edit)
    Button educationBtn;
    @BindView(R.id.btn_activity_person_nickname_edit)
    Button nicknameBtn;
    @BindView(R.id.btn_activity_person_address_edit)
    Button addressBtn;
    @BindView(R.id.iv_fragment_mine_photo)
    RoundedImageView roundedImageView ;

    /*对话框*/
    private Button dialogCancel;
    private Button dialogConfirm;
    private AlertDialog show;
    private EditText editData;

    /*popupWindow*/
    private View selectView;
    private PopupWindow selectPopupWindow;
    private int backgroundPop = 0xa0000000;
    private Button btnCamera;
    private Button btnPhoto;
    private Button btnCancel;

    public PersonInfoView(Context context) {
        super(context);
    }

    public PersonInfoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PersonInfoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 加载布局
     */
    @Override
    protected void getLayout() {
        inflate(mContext, R.layout.activity_person_info_view,this);
    }

    @Override
    protected void initView() {
        //选取图片
        selectView = LayoutInflater.from(mContext).inflate(R.layout.popup_select_pic, null);
        btnCamera = ButterKnife.findById(selectView, R.id.btn_popup_camera);
        btnPhoto = ButterKnife.findById(selectView, R.id.btn_popup_photo);
        btnCancel = ButterKnife.findById(selectView, R.id.btn_popup_cancel);
        selectPopupWindow = new PopupWindow(selectView, LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        //设置popup的背景
        selectPopupWindow.setBackgroundDrawable(new ColorDrawable(backgroundPop));
        selectPopupWindow.setTouchable(true);
        selectPopupWindow.setOutsideTouchable(true);
        selectPopupWindow.setFocusable(true);
        selectPopupWindow.setAnimationStyle(R.style.AnimBottom);
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
    public Context getPersonInfoContext() {
        return mContext;
    }

    @Override
    public void setPresenter(PersonInfoContract.Presenter presenter) {
        mPresenter = Preconditions.checkNotNull(presenter);
    }

    @Override
    public void showError(String message) {
        EventUtil.showToast(mContext,message);
    }


    /**
     * Button点击事件
     */
    @OnClick({R.id.iv_activity_person_info_back,R.id.btn_activity_person_sex_edit,
            R.id.btn_activity_person_age_edit,
                R.id.btn_activity_person_education_edit,
            R.id.btn_activity_person_nickname_edit,
                R.id.btn_activity_person_address_edit,R.id.btn_activity_person_photo_edit})
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_activity_person_info_back://退出
                back();
                break;
            case R.id.btn_activity_person_photo_edit://头像
                selectPopupWindow.showAtLocation(selectView, Gravity.BOTTOM,0,0);
            break;
            case R.id.btn_activity_person_sex_edit://性别
                JumpUtil.jump(mContext,SexActivity.class);
            break;
            case R.id.btn_activity_person_age_edit://年龄
                JumpUtil.jump(mContext,AgeActivity.class);
                break;
            case R.id.btn_activity_person_nickname_edit://昵称
                nicknameDialog();
                break;
            case R.id.btn_activity_person_education_edit://学历
                JumpUtil.jump(mContext,EducationActivity.class);
                break;
            case R.id.btn_activity_person_address_edit://地区
                selectAddress();
                break;
            case R.id.btn_dialog_person_nickname_confirm://确定
                confirmData();
                break;
            case R.id.btn_dialog_person_nickname_cancel://取消
                show.dismiss();
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
    /*取消弹窗*/
    private void cancelPopup() {
        selectPopupWindow.dismiss();
    }
    private void back() {
        ((PersonInfoActivity)mContext).finish();
    }
    /*从相册中选取*/
    private void getPicFromPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        ((PersonInfoActivity)mContext).startActivityForResult(intent,2);
        cancelPopup();
    }

    /*拍照*/
    private void getPicFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        ((PersonInfoActivity)mContext).startActivityForResult(intent,1);
        cancelPopup();
    }

    private void selectAddress() {
        CityPicker cityPicker = new CityPicker.Builder(mContext)
                .textSize(20)
                .titleTextColor("#000000")
                .backgroundPop(0xa0000000)
                .titleBackgroundColor("#0026A5FF")
                .confirTextColor("#000000")
                .cancelTextColor("#000000")
                .province("浙江省")
                .city("杭州市")
                .district("下城区")
                .textColor(Color.parseColor("#000000"))
                .provinceCyclic(true)
                .cityCyclic(false)
                .districtCyclic(false)
                .visibleItemsCount(7)
                .itemPadding(10)
                .build();
        cityPicker.show();
        cityPicker.setOnCityItemClickListener(this);
    }

    private void confirmData() {
        //获得用户录入的信息
        String data = editData.getText().toString().trim();
        if (data!=null){
            nicknameBtn.setText(data);
        }
        show.dismiss();
    }

    /**
     * 昵称
     */
    private void nicknameDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_nickname,null);
        dialogCancel = ButterKnife.findById(view,R.id.btn_dialog_person_nickname_cancel);
        dialogConfirm = ButterKnife.findById(view,R.id.btn_dialog_person_nickname_confirm);
        editData = ButterKnife.findById(view, R.id.edit_dialog_person_nickname_edit);
        dialogCancel.setOnClickListener(this);
        dialogConfirm.setOnClickListener(this);
        builder.setView(view);
        show = builder.show();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        //1.注册eventBus
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        //2.取消注册eventBus
        EventBus.getDefault().unregister(this);
        unbinder.unbind();
        mContext = null;
    }
    //3.处理消息---性别
    @Subscribe(threadMode = ThreadMode.PostThread)
    public void setSex(Sex sex){
        if (sex!=null){
            sexBtn.setText(sex.getSex());
        }
    }
    @Subscribe(threadMode = ThreadMode.PostThread)
    public void setAge(Age age){
        if (age!=null){
            ageBtn.setText(age.getAge());
        }
    }
    @Subscribe(threadMode = ThreadMode.PostThread)
    public void setEducation(Education education){
        if (education!=null){
            educationBtn.setText(education.getEducation());
        }
    }
    /*地区选择监听*/
    @Override
    public void onSelected(String... citySelected) {
        addressBtn.setText(citySelected[0]  + citySelected[1] + citySelected[2]);
    }
}
