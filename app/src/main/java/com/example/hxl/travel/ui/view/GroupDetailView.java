package com.example.hxl.travel.ui.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.hxl.travel.R;
import com.example.hxl.travel.base.BaseRecyclerAdapter;
import com.example.hxl.travel.base.RootView;
import com.example.hxl.travel.model.bean.GroupMembers;
import com.example.hxl.travel.model.bean.VisitorGroups;
import com.example.hxl.travel.model.event.GroupKickMember;
import com.example.hxl.travel.presenter.contract.GroupDetailContract;
import com.example.hxl.travel.ui.activitys.GroupDeleteMemberActivity;
import com.example.hxl.travel.ui.activitys.GroupDetailActivity;
import com.example.hxl.travel.ui.adapter.GroupMemberAdapter;
import com.example.hxl.travel.utils.QRCode;
import com.example.hxl.travel.utils.ToastUtil;
import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**
 * Created by hxl on 2017/8/22 0022 at haichou.
 */

public class GroupDetailView extends RootView<GroupDetailContract.Presenter> implements
        GroupDetailContract.View , View.OnClickListener,
        BaseRecyclerAdapter.OnItemClickListener<GroupMembers>{
    @BindView(R.id.toolbar_activity_group_detail)
    Toolbar toolbar;
    @BindView(R.id.rv_activity_group_detail)
    RecyclerView recyclerView ;
    @BindView(R.id.tv_activity_group_nickname)
    TextView tvTitle;
    private int toolColor = 0x88000000;
    /*弹窗*/
    private PopupWindow popupWindow;
    private Button btnExitGroup;
    private Button btnCancelGroup;
    private Button btnDeleteGroup;
    private View view;
    private int backgroundPop = 0xa0000000;

    private GridLayoutManager gridLayoutManager;
    private List<GroupMembers> groupMemberses = new ArrayList<>();

    /*邀请入群*/
    private View inviteGroupView;
    private AlertDialog.Builder inviteGroupBuilder;
    private EditText editInviteUserName;
    private Button btnInviteGroupCancel;
    private Button btnInviteGroupConfirm;
    private AlertDialog inviteDialogShow;

    /*传值*/
    private String group_member_type;
    private String group_id;
    private Intent intent;
    private String group_nickname;
    private String user_id;
    private Intent intentP;
    private String queryMemberURL;
    /*二维码*/
    private Bitmap bitmap;

    public GroupDetailView(Context context) {
        super(context);
    }

    public GroupDetailView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GroupDetailView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void getLayout() {
        inflate(mContext, R.layout.activity_group_detail_view,this);
    }

    @Override
    protected void initView() {
        initData();

        /*ToolBar*/
        initToolBar();

        /*RecyclerView*/
        initRecycler();

        //退出弹窗
        initExitPopup();

        //邀请入群
        inviteToGroupDialog();
    }
    /*初始化数据*/
    private void initData() {
        intentP = new Intent();
        Bundle bundle = ((GroupDetailActivity) mContext).getIntent().getExtras();
        if (bundle != null) {
            VisitorGroups visitorGroups = bundle.getParcelable("groups");
            group_nickname = visitorGroups.getGroup_nickname();
            group_member_type = visitorGroups.getGroup_member_type();
            group_id = visitorGroups.getGroup_id();
            user_id = visitorGroups.getUser_id();
            queryMemberURL = visitorGroups.getQueryMemberURL();
        }
    }

    /*ToolBar*/
    private void initToolBar() {
        if (!TextUtils.isEmpty(group_nickname)){
            tvTitle.setText(group_nickname);
        }
        toolbar.setTitle("");
        toolbar.setTitleTextColor(toolColor);
        toolbar.setNavigationIcon(R.mipmap.ic_backalpha);
    }

    /*RecyclerView*/
    private void initRecycler() {
        gridLayoutManager =
                new GridLayoutManager(mContext,3, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

    }
    //退出弹窗
    private void initExitPopup() {
        view = LayoutInflater.from(mContext).inflate(R.layout.popup_group_exit, null);
        btnExitGroup = (Button) view.findViewById(R.id.btn_popup_exit_group);
        btnCancelGroup = (Button) view.findViewById(R.id.btn_popup_cancel);
        btnDeleteGroup = (Button) view.findViewById(R.id.btn_popup_delete_group);
        if (group_member_type.equals("2")){//群主
            btnDeleteGroup.setVisibility(VISIBLE);
            btnExitGroup.setVisibility(GONE);
        }else if (group_member_type.equals("1")){//成员，不可解散
            btnDeleteGroup.setVisibility(GONE);
            btnExitGroup.setVisibility(VISIBLE);
        }
        popupWindow = new PopupWindow(view,
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        popupWindow.setAnimationStyle(R.style.AnimBottom);
        popupWindow.setBackgroundDrawable(new ColorDrawable(backgroundPop));
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
    }

    private void inviteToGroupDialog() {
        inviteGroupView = LayoutInflater.from(mContext)
                .inflate(R.layout.dialog_invite_add_group,null);
        inviteGroupBuilder = new AlertDialog.Builder(mContext);
        inviteGroupBuilder.setView(inviteGroupView);
        inviteGroupBuilder.setCancelable(false);
        editInviteUserName = ButterKnife.findById(inviteGroupView,
                R.id.edit_dialog_invite_group_user_edit);
        btnInviteGroupCancel = ButterKnife.findById(inviteGroupView,
                R.id.btn_dialog_invite_friend_cancel);
        btnInviteGroupConfirm = ButterKnife.findById(inviteGroupView,
                R.id.btn_dialog_invite_friend_confirm);
    }

    @Override
    protected void initEvent() {
        toolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });
        btnExitGroup.setOnClickListener(this);
        btnCancelGroup.setOnClickListener(this);
        btnDeleteGroup.setOnClickListener(this);

        btnInviteGroupCancel.setOnClickListener(this);
        btnInviteGroupConfirm.setOnClickListener(this);

    }

    @Override
    public void setPresenter(GroupDetailContract.Presenter presenter) {
        mPresenter = Preconditions.checkNotNull(presenter);
    }

    @Override
    public void showError(String message) {
        ToastUtil.showToast(mContext,message);
    }

    @Override
    public boolean isActive() {
        return mActive;
    }

    @Override
    public Context getGroupDetailContext() {
        return mContext;
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void dismissPopupWindow() {
        popupWindow.dismiss();
    }

    @Override
    public String getGroupId() {
        return group_id;
    }

    @Override
    public String getUserId() {
        return user_id;
    }

    @Override
    public String getGroupMemberType() {
        return group_member_type;
    }

    @Override
    public void showData(List<GroupMembers> groupMemberses) {
        this.groupMemberses = groupMemberses;
        GroupMemberAdapter groupMemberAdapter = new GroupMemberAdapter(mContext,groupMemberses);
        recyclerView.setAdapter(groupMemberAdapter);
        groupMemberAdapter.setOnItemClickListener(this);
    }

    @Override
    public void showGroupDetailMessage(String massage) {

    }
    /*对话框退出*/
    @Override
    public void dismissDialog() {
        inviteDialogShow.cancel();
        if (inviteGroupView.getParent()!=null){
            ((ViewGroup)inviteGroupView.getParent()).removeAllViews();
        }
    }
    /*获得对话框录入的信息*/
    @Override
    public String getUserName() {
        String userName = editInviteUserName.getText().toString().trim();
        return userName;
    }
    /*退出当前页*/
    @Override
    public void goBack() {
        ((GroupDetailActivity)mContext).finish();
    }
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        EventBus.getDefault().register(this);
    }
    @Subscribe(threadMode = ThreadMode.PostThread)
    public void handlerGroupKickMember(GroupKickMember groupKickMember ){
        mPresenter.getData();
    }
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        unbinder = null;
        mContext = null;
        EventBus.getDefault().unregister(this);
        if (bitmap!=null&&!bitmap.isRecycled()){
            bitmap.recycle();
            bitmap = null;
        }
    }
    /*click点击事件*/
    @OnClick({R.id.iv_activity_group_detail_menu,R.id.iv_activity_group_erweima})
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_activity_group_detail_menu://菜单
                showExitGroupPopup();
                break;
            case R.id.iv_activity_group_erweima://二维码
                showErWeiMaDialog();
                break;
            case R.id.btn_popup_delete_group://解散群组
                mPresenter.deleteGroup();
                //popupWindow.dismiss();
                break;
            case R.id.btn_popup_exit_group://退出群组
                mPresenter.quitGroup();
                break;
            case R.id.btn_popup_cancel://取消
                dismissPopupWindow();
                break;
            case R.id.btn_dialog_invite_friend_cancel://邀请群取消
                dismissDialog();
                break;
            case R.id.btn_dialog_invite_friend_confirm://邀请群确定
                mPresenter.confirmInvite();
                break;
        }
    }
    /*二维码*/
    private void showErWeiMaDialog() {
        View erWeiMaView = LayoutInflater.from(mContext)
                .inflate(R.layout.dialog_group_erweima, null);
        ImageView ivErWeiMa = ButterKnife.findById(erWeiMaView, R.id.iv_dialog_group_erweima);
        Button btnErWeiMa = ButterKnife.findById(erWeiMaView,R.id.btn_group_title);
        AlertDialog.Builder erWeiMaDialog = new AlertDialog.Builder(mContext);
        erWeiMaDialog.setView(erWeiMaView);
        bitmap = QRCode.Create2QR2(mContext, queryMemberURL, R.mipmap.photo);
        if (bitmap!=null){
            ivErWeiMa.setImageBitmap(bitmap);
            btnErWeiMa.setText(group_nickname);
        }
        erWeiMaDialog.show();
    }
    /*显示弹窗*/
    private void showExitGroupPopup() {
        popupWindow.showAtLocation(view, Gravity.BOTTOM,0,0);
    }
    /*RecyclerView点击事件*/
    @Override
    public void onItemClick(View view, GroupMembers data, int position) {
        if (groupMemberses == null)
            return;
        if (group_member_type.equals("2")){//群主
            if (position == groupMemberses.size()-2){
                inviteAdd();//邀请入群
            }
            if (position == groupMemberses.size()-1){
                Bundle bundle = new Bundle();
                bundle.putString("group_id",group_id);
                intentP.putExtras(bundle);
                intentP.setClass(mContext,GroupDeleteMemberActivity.class);
                mContext.startActivity(intentP);
            }
            if (position >= 0 && position < groupMemberses.size()-2){

            }
        }else if (group_member_type.equals("1")){

        }
    }
    private void inviteAdd() {
        inviteDialogShow = inviteGroupBuilder.show();
    }
}
