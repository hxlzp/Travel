package com.example.hxl.travel.ui.view;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;

import com.example.hxl.travel.R;
import com.example.hxl.travel.base.RootView;
import com.example.hxl.travel.model.bean.VisitorGroups;
import com.example.hxl.travel.model.event.GroupDissolve;
import com.example.hxl.travel.model.event.GroupEdit;
import com.example.hxl.travel.model.event.LoginEvent;
import com.example.hxl.travel.presenter.contract.GroupContract;
import com.example.hxl.travel.ui.activitys.GroupBuildActivity;
import com.example.hxl.travel.ui.activitys.GroupDetailActivity;
import com.example.hxl.travel.ui.activitys.MainActivity;
import com.example.hxl.travel.ui.adapter.GroupListExpandableListAdapter;
import com.example.hxl.travel.utils.EventUtil;
import com.example.hxl.travel.utils.JumpUtil;
import com.example.hxl.travel.utils.ToastUtil;
import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**
 * Created by hxl on 2017/6/9 at haiChou.
 */
public class GroupView extends RootView<GroupContract.Presenter> implements
        GroupContract.View, ExpandableListView.OnChildClickListener ,
        View.OnClickListener{
    @BindView(R.id.rl_group_build)
    RelativeLayout rlGroupBuild;

    private Intent intent;
    private String sessionId;
    private String userId;

    /*Expandable*/
    private List<List<Map<String, Object>>> childs;
    private View view;
    private ExpandableListView expandableListView;

    public GroupView(Context context) {
        super(context);
    }

    public GroupView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GroupView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    protected void getLayout() {
        view = inflate(mContext, R.layout.fragment_group_view, this);
        expandableListView = ButterKnife.findById(view, R.id.expandable_id);
    }

    @Override
    protected void initView() {
        intent = new Intent();
    }

    @Override
    protected void initEvent() {
        rlGroupBuild.setOnClickListener(this);
    }
    @Override
    public boolean isActive() {
        return mActive;
    }

    @Override
    public void showData(List<VisitorGroups> datas) {
        int size = datas.size();
        //为ExpandableListView准备数据
        List<Map<String, Object>> groups = new ArrayList<>();
        //群组标题
        String[] titles = new String[2];
        //加入的群
        List<VisitorGroups> groupNicknameAddList = new ArrayList<>();
        //创建的群
        List<VisitorGroups> groupNicknameBuildList = new ArrayList<>();
        List<VisitorGroups>[] lists = new List[titles.length];
        lists[0] = groupNicknameAddList;
        lists[1] = groupNicknameBuildList;
        for (int i = 0;i<size;i++){
            String group_member_type = datas.get(i).getGroup_member_type();
            String group_nickname = datas.get(i).getGroup_nickname();
            String queryMemberURL = datas.get(i).getQueryMemberURL();
            String user_id = datas.get(i).getUser_id();
            String group_id = datas.get(i).getGroup_id();
            if (group_member_type.equals("1")){//成员
                titles[0]=mContext.getResources().getString(R.string.groupNicknameAdd);
                groupNicknameAddList.add(new VisitorGroups(group_nickname,
                        group_member_type,group_id,user_id,queryMemberURL));
            }else if (group_member_type.equals("2")){//群组
                titles[1] = mContext.getResources().getString(R.string.groupNicknameBuild);
                groupNicknameBuildList.add(new VisitorGroups(group_nickname,
                        group_member_type,group_id,user_id,queryMemberURL));
            }
        }
        //父群组
        for (int i = 0;i<titles.length;i++){
            Map<String, Object> group = new HashMap<String, Object>();
            group.put("title", titles[i]);
            groups.add(group);
        }
        //子群组
        List<List<Map<String, Object>>> childs = new ArrayList<>();
        this.childs = childs;
        for (int i = 0 ; i<lists.length;i++){
            List<Map<String, Object>> child = new ArrayList<>();
            for (int j = 0;j<lists[i].size();j++){
                Map<String, Object> childData = new HashMap<>();
                childData.put("nickName",lists[i].get(j).getGroup_nickname());
                childData.put("group_member_type",lists[i].get(j).getGroup_member_type());
                childData.put("queryMemberURL",lists[i].get(j).getQueryMemberURL());
                childData.put("user_id",lists[i].get(j).getUser_id());
                childData.put("group_id",lists[i].get(j).getGroup_id());
                childData.put("ico",R.mipmap.photo);
                child.add(childData);
            }
            childs.add(child);
        }
        // 实例化ExpandableListView的适配器
        GroupListExpandableListAdapter adapter = new
                GroupListExpandableListAdapter(mContext, groups, childs);
        // 设置适配器
        if (adapter!=null&&expandableListView!=null){
            expandableListView.setAdapter(adapter);
            // 设置监听器
            expandableListView.setOnChildClickListener(this);
        }
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public Context getGroupContext() {
        return mContext;
    }

    @Override
    public void showGroupMessage(String message) {
        ToastUtil.showToast(mContext,message);
    }

    @Override
    public String getSessionId() {
        return sessionId;
    }

    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public void setPresenter(GroupContract.Presenter presenter) {
        mPresenter = Preconditions.checkNotNull(presenter);
    }

    @Override
    public void showError(String message) {
        EventUtil.showToast(mContext,message);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        //1.注册
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        //2.取消注册
        EventBus.getDefault().unregister(this);
        unbinder.unbind();
        unbinder = null;
    }
    //2.处理添加群聊
    @Subscribe(threadMode = ThreadMode.PostThread)
    public void handlerMessage(VisitorGroups group){
        mPresenter.getData();
    }
    //处理点击编辑群聊
    @Subscribe(threadMode = ThreadMode.PostThread)
    public void handlerMessage(GroupEdit group){

    }
    /*1.登录*/
    @Subscribe(threadMode = ThreadMode.PostThread)
    public void handlerLoginMessage(LoginEvent loginEvent ){
        String user_id = loginEvent.getUser_id();
        String sessionId = loginEvent.getSessionId();
        this.sessionId = sessionId;
        userId = user_id;
        mPresenter.getData();
    }
    /*解散群组*/
    @Subscribe(threadMode = ThreadMode.PostThread)
    public void handlerDissolveMessage(GroupDissolve groupDissolve){
        mPresenter.getData();
    }

    /*点击事件*/
    @Override
    public boolean onChildClick(ExpandableListView expandableListView, View view,
                                int groupPosition, int childPosition, long id) {
        String  group_nickname = (String) childs.get(groupPosition)
                .get(childPosition).get("nickName");
        String  group_member_type = (String) childs.get(groupPosition)
                .get(childPosition).get("group_member_type");
        String  group_id = (String) childs.get(groupPosition)
                .get(childPosition).get("group_id");
        String  user_id = (String) childs.get(groupPosition)
                .get(childPosition).get("user_id");
        String  queryMemberURL = (String) childs.get(groupPosition)
                .get(childPosition).get("queryMemberURL");
        intent.setClass(mContext,GroupDetailActivity.class);
        VisitorGroups visitorGroups = new VisitorGroups(group_nickname,group_member_type,
                group_id,user_id,queryMemberURL) ;
        intent.putExtra("groups", visitorGroups);
        mContext.startActivity(intent);

        return true;
    }
    /*点击事件*/
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_group_build://创建群组
                JumpUtil.jump(mContext, GroupBuildActivity.class);
                ((MainActivity) mContext).overridePendingTransition(R.anim.enter_in_active,
                        R.anim.exit_out_inactive);
                break;
        }
    }
}
