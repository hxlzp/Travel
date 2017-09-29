package com.example.hxl.travel.ui.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hxl.travel.R;
import com.example.hxl.travel.base.RootView;
import com.example.hxl.travel.presenter.contract.GroupChatContract;
import com.example.hxl.travel.ui.activitys.GroupChatActivity;
import com.example.hxl.travel.utils.EventUtil;
import com.google.common.base.Preconditions;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by hxl on 2017/6/9 at haiChou.
 * 群组
 */
public class GroupChatView extends RootView<GroupChatContract.Presenter> implements
        GroupChatContract.View, View.OnClickListener {
    @BindView(R.id.iv_activity_group_chat_back)
    ImageView ivBack;
    @BindView(R.id.btn_activity_group_chat_detail)
    Button btnMenu;
    @BindView(R.id.tv_activity_group_chat_title)
    TextView tvTitle;
    /*传值*/
    private String group_member_type;
    private String group_id;
    private Intent intent;
    private String group_nickname;
    private String user_id;
    private String queryMemberURL;

    public GroupChatView(Context context) {
        super(context);
    }

    public GroupChatView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GroupChatView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    protected void getLayout() {
        inflate(mContext, R.layout.activity_group_chat_view,this);
    }

    @Override
    protected void initView() {
        intent = new Intent();
        Intent intentP = ((GroupChatActivity) mContext).getIntent();
        Bundle bundle = intentP.getExtras();
        group_nickname = bundle.getString("group_nickname");
        group_member_type = bundle.getString("group_member_type");
        group_id = bundle.getString("group_id");
        user_id = bundle.getString("user_id");
        queryMemberURL = bundle.getString("queryMemberURL");
        if (!TextUtils.isEmpty(group_nickname))
            tvTitle.setText(group_nickname);
    }

    @Override
    protected void initEvent() {

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
    public void setPresenter(GroupChatContract.Presenter presenter) {
        mPresenter = Preconditions.checkNotNull(presenter);
    }



    @Override
    public void showError(String message) {
        EventUtil.showToast(mContext,message);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        unbinder = null;
        mContext = null;
    }
    @OnClick({R.id.iv_activity_group_chat_back,R.id.btn_activity_group_chat_detail})
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_activity_group_chat_back://返回
                ((GroupChatActivity)mContext).finish();
                break;
            case R.id.btn_activity_group_chat_detail://群详情
                //intent.setClass(mContext,GroupDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("group_nickname",group_nickname);
                bundle.putString("group_member_type",group_member_type);
                bundle.putString("group_id",group_id);
                bundle.putString("user_id",user_id);
                bundle.putString("queryMemberURL",queryMemberURL);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
                //JumpUtil.jump(mContext, GroupDetailActivity.class);
                break;
        }
    }
}

