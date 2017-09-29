package com.example.hxl.travel.ui.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hxl.travel.R;
import com.example.hxl.travel.utils.ScreenUtil;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by hxl on 2017/1/3 at haiChou.
 */
public class MineRecyclerAdapter extends RecyclerView.Adapter<MineRecyclerAdapter.MyHolder> {

    private RecyclerView mRecyclerView;

    private List<Integer> data = new ArrayList<>();
    private List<String> title = new ArrayList<>();
    private Context mContext;

    private View VIEW_FOOTER;
    private View VIEW_HEADER;

    //Type
    private int TYPE_NORMAL = 1000;
    private int TYPE_HEADER = 1001;
    private int TYPE_FOOTER = 1002;
    private RelativeLayout.LayoutParams tvLayoutParams;
    private RelativeLayout.LayoutParams iconLayoutParams;
    private RelativeLayout.LayoutParams ivIntoLayoutParams;

    public MineRecyclerAdapter(List<Integer> data, Context mContext) {
        this.data = data;
        this.mContext = mContext;

    }
    public MineRecyclerAdapter(List<Integer> data, List<String> title,Context mContext) {
        this.title = title;
        this.data = data;
        this.mContext = mContext;
        tvLayoutParams = new RelativeLayout
                .LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                ScreenUtil.dip2px(mContext,65));
        iconLayoutParams = new RelativeLayout
                .LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                ScreenUtil.dip2px(mContext,65));
        ivIntoLayoutParams = new RelativeLayout
                .LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                ScreenUtil.dip2px(mContext,65));
    }
    @Override
    public MineRecyclerAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER) {
            return new MyHolder(VIEW_FOOTER);
        } else if (viewType == TYPE_HEADER) {
            return new MyHolder(VIEW_HEADER);
        } else {
            return new MyHolder(getLayout(R.layout.mine_recycler_item));
        }
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        if (!isHeaderView(position) && !isFooterView(position)) {
            if (haveHeaderView()) position--;
            ImageView icon = (ImageView) holder.itemView
                    .findViewById(R.id.iv_mine_recycler_item);
            icon.setImageResource(data.get(position));
            icon.setId(R.id.iv_mine_recycler_item);
            TextView textView = (TextView) holder.itemView
                    .findViewById(R.id.tv_mine_recycler_item);
            ImageView ivInto = (ImageView) holder.itemView
                    .findViewById(R.id.ic_iv_mine_recycler_item_into);
            textView.setText(title.get(position));
            View viewSpace = holder.itemView.findViewById(R.id.view_space);
            if (position == 0){
                tvLayoutParams.leftMargin = ScreenUtil.dip2px(mContext,10);
                tvLayoutParams.addRule(RelativeLayout.RIGHT_OF, icon.getId());
                textView.setLayoutParams(tvLayoutParams);
                textView.setGravity(Gravity.CENTER_VERTICAL);

                iconLayoutParams.leftMargin = ScreenUtil.dip2px(mContext,15);
                icon.setLayoutParams(iconLayoutParams);

                ivIntoLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                ivInto.setPadding(0,0,ScreenUtil.dip2px(mContext,15),0);
                ivInto.setLayoutParams(ivIntoLayoutParams);
            }
            if(position == 0 || position == 4){
                viewSpace.setVisibility(View.VISIBLE);
            }else {
                viewSpace.setVisibility(View.GONE);
            }
            final int finalPosition = position;
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(finalPosition);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        int count = (data == null ? 0 : data.size());
        if (VIEW_FOOTER != null) {
            count++;
        }

        if (VIEW_HEADER != null) {
            count++;
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderView(position)) {
            return TYPE_HEADER;
        } else if (isFooterView(position)) {
            return TYPE_FOOTER;
        } else {
            return TYPE_NORMAL;
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        try {
            if (mRecyclerView == null && mRecyclerView != recyclerView) {
                mRecyclerView = recyclerView;
            }
            ifGridLayoutManager();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private View getLayout(int layoutId) {
        return LayoutInflater.from(mContext).inflate(layoutId, null);
    }

    public void addHeaderView(View headerView) {
        if (haveHeaderView()) {
            throw new IllegalStateException("hearview has already exists!");
        } else {
            //避免出现宽度自适应
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            headerView.setLayoutParams(params);
            VIEW_HEADER = headerView;
            ifGridLayoutManager();
            notifyItemInserted(0);
        }

    }

    public void addFooterView(View footerView) {
        if (haveFooterView()) {
            throw new IllegalStateException("footerView has already exists!");
        } else {
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            footerView.setLayoutParams(params);
            VIEW_FOOTER = footerView;
            ifGridLayoutManager();
            notifyItemInserted(getItemCount() - 1);
        }
    }

    private void ifGridLayoutManager() {
        if (mRecyclerView == null) return;
        final RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager.SpanSizeLookup originalSpanSizeLookup =
                    ((GridLayoutManager) layoutManager).getSpanSizeLookup();
            ((GridLayoutManager) layoutManager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return (isHeaderView(position) || isFooterView(position)) ?
                            ((GridLayoutManager) layoutManager).getSpanCount() :
                            1;
                }
            });
        }
    }

    private boolean haveHeaderView() {
        return VIEW_HEADER != null;
    }

    public boolean haveFooterView() {
        return VIEW_FOOTER != null;
    }

    private boolean isHeaderView(int position) {
        return haveHeaderView() && position == 0;
    }

    private boolean isFooterView(int position) {
        return haveFooterView() && position == getItemCount() - 1;
    }


    public static class MyHolder extends RecyclerView.ViewHolder {

        public MyHolder(View itemView) {
            super(itemView);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public OnItemClickListener onItemClickListener ;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
