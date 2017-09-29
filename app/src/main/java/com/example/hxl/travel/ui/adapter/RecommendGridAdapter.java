package com.example.hxl.travel.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hxl.travel.R;
import com.example.hxl.travel.model.bean.ImageRecommend;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by hxl on 2017/8/10 0010 at haichou.
 */

public class RecommendGridAdapter extends BaseAdapter {
    private Context mContext;
    private List<ImageRecommend> datas;
    public RecommendGridAdapter(Context mContext, List<ImageRecommend> datas) {
        this.mContext = mContext;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas == null?0:datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_recommend_tab,null);
            viewHolder = new ViewHolder();
            viewHolder.textView = ButterKnife.findById(convertView,R.id.tv_tab);
            viewHolder.imageView = ButterKnife.findById(convertView,R.id.iv_tab);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textView.setText(datas.get(position).getNames());
        viewHolder.imageView.setImageResource(datas.get(position).getIcons());
        return convertView;
    }
    static class ViewHolder{
        ImageView imageView ;
        TextView textView ;
    }
}
