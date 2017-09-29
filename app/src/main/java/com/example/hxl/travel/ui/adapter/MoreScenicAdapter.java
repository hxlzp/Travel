package com.example.hxl.travel.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hxl.travel.R;
import com.example.hxl.travel.base.BaseRecyclerAdapter;
import com.example.hxl.travel.model.bean.RecommendList;

import java.util.List;

/**
 * Created by hxl on 2017/8/14 0014 at haichou.
 */

public class MoreScenicAdapter extends BaseRecyclerAdapter<RecommendList>{
    public MoreScenicAdapter(List<RecommendList> data, Context mContext) {
        super.datas = data;
        super.mContext = mContext;
    }
    @Override
    protected RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType) {
        return new Holder(getLayout(R.layout.item_more_scenic));
    }

    @Override
    protected void onBind(RecyclerView.ViewHolder holder, List<RecommendList> datas,
                          int position) {
        if (holder instanceof Holder){
            ImageView imageView = (ImageView) holder.itemView
                    .findViewById(R.id.iv_more_scenic_item);
            TextView tvTitle = (TextView) holder.itemView.findViewById(R.id.tv_scenic_title);
            TextView tvInfo = (TextView) holder.itemView.findViewById(R.id.tv_scenic_info);
            int layoutPosition = holder.getLayoutPosition();
            tvTitle.setText(datas.get(layoutPosition).getTitle().toString());
            tvInfo.setText(datas.get(layoutPosition).getContent().toString());
            imageView.setImageResource(datas.get(layoutPosition).getImgUrl());
            //Glide.with(mContext).load(datas.get(layoutPosition).getImgUrl()).into(imageView);
        }

    }
    private View getLayout(int layoutId) {
        return LayoutInflater.from(mContext).inflate(layoutId, null);
    }
}
