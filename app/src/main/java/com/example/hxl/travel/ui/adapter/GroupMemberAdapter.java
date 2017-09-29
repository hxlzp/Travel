package com.example.hxl.travel.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hxl.travel.R;
import com.example.hxl.travel.base.BaseRecyclerAdapter;
import com.example.hxl.travel.model.bean.GroupMembers;
import com.example.hxl.travel.model.bean.MemberGroup;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

/**
 * Created by hxl on 2017/6/9 at haiChou.
 * 群成员适配器
 */
public class GroupMemberAdapter extends BaseRecyclerAdapter<GroupMembers> {

    public GroupMemberAdapter(Context mContext, List<GroupMembers> memberGroups) {
        super.mContext = mContext;
        super.datas = memberGroups;
    }

    @Override
    protected RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType) {

        return new Holder(getLayoutView());
    }

    private View getLayoutView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_activity_group_member,
                null);
        return view;
    }

    @Override
    protected void onBind(RecyclerView.ViewHolder holder, List<GroupMembers> datas, int position) {
        RoundedImageView roundedImageView = (RoundedImageView) holder.itemView
                .findViewById(R.id.iv_activity_group_member_item);
        TextView textView = (TextView) holder.itemView
                .findViewById(R.id.tv_activity_group_member_item);
        int layoutPosition = holder.getLayoutPosition();
        String nickname = datas.get(layoutPosition).getUserNickname();
        int img = datas.get(layoutPosition).getImg();
        if (img == 0){//暂时本地头像图片
            roundedImageView.setImageResource(R.mipmap.photo);
        }else {
            roundedImageView.setImageResource(img);
        }
        textView.setText(nickname);
    }
}
