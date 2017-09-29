package com.example.hxl.travel.model.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by hxl on 2017/8/18 0018 at haichou.
 */

public abstract class TypeViewHolder<T> extends RecyclerView.ViewHolder{


    public TypeViewHolder(View itemView) {
        super(itemView);
    }
    public abstract void bindViewHolder(T datas,int position);
}
