package com.example.hxl.travel.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.hxl.travel.R;
import com.example.hxl.travel.base.BaseRecyclerAdapter;
import com.example.hxl.travel.model.bean.GroupMembers;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hxl on 2017/6/9 at haiChou.
 * 删除群成员
 */
public class GroupMemberDeleteAdapter extends BaseRecyclerAdapter<GroupMembers> {
    private Map<Integer,Integer> map = new LinkedHashMap<>();
    private Map<Integer,String> userIds = new LinkedHashMap<>();
    public GroupMemberDeleteAdapter(Context mContext, List<GroupMembers> memberGroups) {
        super.mContext = mContext;
        super.datas = memberGroups;
    }

    public Map<Integer, Integer> getMap() {
        return map;
    }

    public Map<Integer, String> getUserIds() {
        return userIds;
    }

    @Override
    protected RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType) {

        return new Holder(getLayoutView());
    }

    private View getLayoutView() {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_activity_group_member_delete, null);
        return view;
    }

    @Override
    protected void onBind(RecyclerView.ViewHolder holder,
                          List<GroupMembers> datas, final int position) {
        RoundedImageView roundedImageView = (RoundedImageView) holder.itemView
                .findViewById(R.id.iv_activity_group_member_item);
        TextView textView = (TextView) holder.itemView
                .findViewById(R.id.tv_activity_group_member_item);
        CheckBox checkBox = (CheckBox) holder.itemView
                .findViewById(R.id.cb_activity_group_member_item);
        final int layoutPosition = holder.getLayoutPosition();
        int img = R.mipmap.photo;
        String userNickname = datas.get(layoutPosition).getUserNickname();
        String group_member_type = datas.get(layoutPosition).getGroup_member_type();
        final String user_id = datas.get(layoutPosition).getUser_id();
        roundedImageView.setImageResource(img);
        textView.setText(userNickname);
        if (group_member_type.equals("1")){//成员
            checkBox.setVisibility(View.VISIBLE);
        }else if (group_member_type.equals("2")){//群主
            checkBox.setVisibility(View.GONE);
            return;
        }
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    map.put(layoutPosition,layoutPosition);
                    userIds.put(layoutPosition,user_id);
                }else{
                    map.remove(layoutPosition);
                    userIds.remove(layoutPosition);
                }
            }
        });
    }
}

