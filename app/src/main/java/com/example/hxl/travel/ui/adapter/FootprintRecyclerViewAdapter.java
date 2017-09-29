package com.example.hxl.travel.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hxl.travel.R;
import com.example.hxl.travel.model.bean.FootprintScenicList;
import com.example.hxl.travel.model.holder.TypeViewHolderFootprintOne;
import com.example.hxl.travel.model.holder.TypeViewHolderFootprintTwo;
import com.example.hxl.travel.widget.ExpandMoreView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hxl on 2017/8/18 0018 at haichou.
 */

public class FootprintRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final static int TYPE_ONE = 1;
    private final static int TYPE_TWO = 2;

    private Context context;
    private List<String> oneDatas;
    private List<FootprintScenicList> twoDatas;
    private final LayoutInflater layoutInflater;

    private List<Integer> types = new ArrayList<>() ;
    private Map<Integer,Integer> maps = new HashMap<>();
    private View twoView;

    public FootprintRecyclerViewAdapter(Context context, List<String> oneDatas,
                                        List<FootprintScenicList> twoDatas){
        this.context = context;
        this.oneDatas = oneDatas;
        this.twoDatas = twoDatas;
        addListByType(TYPE_ONE,oneDatas);
        addListByType(TYPE_TWO,twoDatas);
        layoutInflater = LayoutInflater.from(context);
    }

    private void addListByType(int type, List datas) {
        maps.put(type,types.size());
        for (int i = 0;i<datas.size();i++){
            types.add(type);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case TYPE_ONE:
                View oneView = layoutInflater.inflate(R.layout.item_footprint_one,parent,false);
                return new TypeViewHolderFootprintOne(oneView);
            case TYPE_TWO:
                twoView = layoutInflater.inflate(R.layout.item_footprint_two,parent,false);
                return new TypeViewHolderFootprintTwo(twoView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.e("position", "onBindViewHolder: "+position );

        int type = getItemViewType(position);
        final int realPosition = position - maps.get(type);
        Log.e("realPosition", "onBindViewHolder: "+realPosition );
        switch (type){
            case TYPE_ONE:
                ((TypeViewHolderFootprintOne)holder).bindViewHolder(oneDatas,realPosition);
                break;
            case TYPE_TWO:
                ((TypeViewHolderFootprintTwo)holder).bindViewHolder(twoDatas,realPosition);
                ((ExpandMoreView)twoView).
                        setOnItemClickListener(new ExpandMoreView.OnItemClickListener() {
                    @Override
                    public void itemClick() {
                        onAdapterClickListener.onClick(twoDatas,realPosition);
                    }
                });
                break;
        }
    }

    @Override
    public int getItemCount() {
        return types.size();
    }

    @Override
    public int getItemViewType(int position) {

        return types.get(position);
    }
    public interface OnAdapterClickListener{
        void onClick(List<FootprintScenicList> datas, int position);
    }
    public OnAdapterClickListener onAdapterClickListener ;

    public void setOnAdapterClickListener(OnAdapterClickListener onAdapterClickListener) {
        this.onAdapterClickListener = onAdapterClickListener;
    }
}
