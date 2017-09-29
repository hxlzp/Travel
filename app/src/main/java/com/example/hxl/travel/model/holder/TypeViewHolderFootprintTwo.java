package com.example.hxl.travel.model.holder;

import android.view.View;

import com.example.hxl.travel.R;
import com.example.hxl.travel.model.bean.FootprintScenicList;
import com.example.hxl.travel.widget.ExpandMoreView;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by hxl on 2017/8/18 0018 at haichou.
 */

public class TypeViewHolderFootprintTwo extends TypeViewHolder<List<FootprintScenicList>> {

    private final ExpandMoreView expandMoreView;

    public TypeViewHolderFootprintTwo(View itemView) {
        super(itemView);
        expandMoreView = ButterKnife.findById(itemView,R.id.expand_view);
    }

    @Override
    public void bindViewHolder(final List<FootprintScenicList> datas, final int position) {
        expandMoreView.setTitle(datas.get(position).getTitle());
        expandMoreView.setStayTitle(datas.get(position).getStayTime());
        expandMoreView.setPlayTitle(datas.get(position).getPlayTime());
        expandMoreView.setConsumeMoneyTitle(datas.get(position).getConsumeMoney());
        expandMoreView.setPlayDataTitle(datas.get(position).getPlayData());
        expandMoreView.setScenicIcon(datas.get(position).getIcon());
    }

}