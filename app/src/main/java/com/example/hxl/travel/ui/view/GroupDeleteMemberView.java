package com.example.hxl.travel.ui.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.example.hxl.travel.R;
import com.example.hxl.travel.base.BaseRecyclerAdapter;
import com.example.hxl.travel.base.RootView;
import com.example.hxl.travel.model.bean.GroupMembers;
import com.example.hxl.travel.presenter.contract.GroupDeleteMemberContract;
import com.example.hxl.travel.ui.activitys.GroupDeleteMemberActivity;
import com.example.hxl.travel.ui.adapter.GroupMemberDeleteAdapter;
import com.example.hxl.travel.utils.EventUtil;
import com.example.hxl.travel.utils.ToastUtil;
import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by hxl on 2017/6/9 at haiChou.
 */
public class GroupDeleteMemberView extends RootView<GroupDeleteMemberContract.Presenter>
        implements GroupDeleteMemberContract.View, View.OnClickListener,
        BaseRecyclerAdapter.OnItemClickListener {
    @BindView(R.id.rv_activity_group_delete_member)
    RecyclerView recyclerView ;
    private List<GroupMembers> datas = new ArrayList<>();
    private GroupMemberDeleteAdapter adapter;
    private String group_id;

    public GroupDeleteMemberView(Context context) {
        super(context);
    }

    public GroupDeleteMemberView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GroupDeleteMemberView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void getLayout() {
        inflate(mContext, R.layout.activity_group_delete_member_view,this);
    }

    @Override
    protected void initView() {
        initData();
        initRecycler();
    }

    private void initData() {
        Intent intent = ((GroupDeleteMemberActivity) mContext).getIntent();
        Bundle bundle = intent.getExtras();
        group_id = bundle.getString("group_id");
    }

    private void initRecycler() {
        LinearLayoutManager manager = new LinearLayoutManager(mContext,
                LinearLayoutManager.VERTICAL,false);
        recyclerView.addItemDecoration(new DividerItemDecoration(mContext,
                DividerItemDecoration.VERTICAL));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(manager);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    public boolean isActive() {
        return mActive;
    }

    @Override
    public void showData(List<GroupMembers> datas) {
        this.datas = datas;
        showListData(datas);
    }

    private void showListData(List<GroupMembers> datas) {
        adapter = new GroupMemberDeleteAdapter(mContext,datas);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public Context getGroupDeleteMemberContext() {
        return mContext;
    }

    @Override
    public String getGroupId() {
        return group_id;
    }

    @Override
    public void showGroupDeleteMessage(String message) {
        ToastUtil.showToast(mContext,message);
    }

    @Override
    public void setPresenter(GroupDeleteMemberContract.Presenter presenter) {
        mPresenter = Preconditions.checkNotNull(presenter);
    }

    @Override
    public void showError(String message) {
        EventUtil.showToast(mContext,message);
    }
    @OnClick({R.id.iv_activity_group_delete_member_back,
            R.id.btn_activity_group_delete_member})
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_activity_group_delete_member_back://返回
                ((GroupDeleteMemberActivity)mContext).finish();
                break;
            case R.id.btn_activity_group_delete_member://删除成员
                //deleteMember();
                String userIds = deleteMemberByUserId();
                mPresenter.deleteGroupMembers(userIds);
                break;
        }
    }

    private String deleteMemberByUserId() {
        if (adapter != null)
            return null;
        Map<Integer, String> mapUserIds = adapter.getUserIds();
        Iterator<String> iterator = mapUserIds.values().iterator();
        List<String> userIds = new ArrayList<>();
        while (iterator.hasNext()){
            String userId = iterator.next();
            userIds.add(userId);
        }
        Collections.sort(userIds);
        StringBuffer stringBuffer = new StringBuffer() ;
        for (int i = 0; i<userIds.size();i++){
            stringBuffer.append(userIds.get(i)).append(",");
        }
        return stringBuffer.toString();
    }

    /*删除群组成员*/
    private void deleteMember() {
        int count = 0;
        boolean isFirst = true;
        Map<Integer, Integer> map = adapter.getMap();
        Iterator<Integer> iterator = map.values().iterator();
        List<Integer> nexts = new ArrayList<>();
        while (iterator.hasNext()){
            Integer next = iterator.next();
            nexts.add(next);

        }
        // 顺序排列,防止没有按顺序选择时，出现数组越界异常
        Collections.sort(nexts);
        for (int i = 0;i< nexts.size();i++){
            int next = nexts.get(i);
            if (isFirst){
                isFirst = false;
            }else {
                count++;
                next = next-count;
            }
            datas.remove(datas.get(next));
            adapter.notifyItemRemoved(next);
            adapter.notifyDataSetChanged();
        }

        showListData(datas);

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mContext = null;
        unbinder = null;
    }
    /*Recycler点击事件*/
    @Override
    public void onItemClick(View view, Object data, int position) {

    }
}

