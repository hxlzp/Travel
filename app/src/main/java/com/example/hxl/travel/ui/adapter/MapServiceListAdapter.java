package com.example.hxl.travel.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hxl.travel.R;
import com.example.hxl.travel.model.bean.ScenicList;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by hxl on 2017/8/8 0008 at haichou.
 */

public class MapServiceListAdapter extends BaseAdapter{
    private Context context;
    private List<ScenicList> datas;
    int mCurrentPos;
    public MapServiceListAdapter(Context context, List<ScenicList> datas){
        this.context = context;
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
    public void setCurrentPosition(int position){
        this.mCurrentPos = position;
    }

    Holder holder = null;
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_map_service_list,null);
            holder = new Holder() ;
            holder.textView = ButterKnife.findById(convertView,R.id.tv_map_service_item);
            holder.imageView = ButterKnife.findById(convertView,R.id.iv_map_service_item);
            convertView.setTag(holder);
        }else {
            holder = (Holder) convertView.getTag();
        }
        holder.textView.setText(datas.get(position).getTitle());
        holder.textView.setTextColor(context.getResources().getColor(R.color.scenicGrayColor));
        holder.imageView.setImageResource(datas.get(position).getImg());
        if (mCurrentPos == position){
            holder.imageView.setImageResource(datas.get(position).getImgChecked());
            holder.textView.setTextColor(context.getResources().getColor(R.color.colorPrimaryText));
        }
        return convertView;
    }


    /*视图保持者*/
    static class Holder{
         TextView textView ;
         ImageView imageView ;
    }
}
