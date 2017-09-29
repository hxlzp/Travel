package com.example.hxl.travel.ui.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.hxl.travel.R;
import com.example.hxl.travel.base.BaseRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by hxl on 2017/1/5 at haiChou.
 */
public class PersonRecyclerAdapter extends BaseRecyclerAdapter<String> {

    public PersonRecyclerAdapter(List<String> data, Context mContext) {
        super.datas = data;
        super.mContext = mContext;
    }
    @Override
    protected RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType) {
        return new Holder(getLayout(R.layout.person_recycler_item));
    }

    @Override
    protected void onBind(RecyclerView.ViewHolder holder, List<String> datas, int position) {
        if (holder instanceof Holder){
            Button sex = (Button) holder.itemView.findViewById(R.id.btn_activity_person);
            sex.setText(datas.get(position));
        }
    }

    private View getLayout(int layoutId) {
        return LayoutInflater.from(mContext).inflate(layoutId, null);
    }

}

