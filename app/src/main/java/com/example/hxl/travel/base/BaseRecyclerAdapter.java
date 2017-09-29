package com.example.hxl.travel.base;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by hxl on 2017/1/18 at haiChou.
 * 适配器基类
 */
public  abstract class BaseRecyclerAdapter<T> extends
        RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected List<T> datas ;
    protected Context mContext ;
    protected RecyclerView mRecyclerView ;

    /*view*/
    private View header_view;
    private View footer_view;

    /*type*/
    private static final int TYPE_HEADER = 1001;
    private static final int TYPE_FOOTER = 1002;
    private static final int TYPE_NORMAL = 1003;

    /*设置对外访问的接口*/
    public interface OnItemClickListener<T>{
        void onItemClick(View view,T data,int position);
    }
    /*设置对外访问的方法*/
    private OnItemClickListener onItemClickListener;

    /*长按*/
    public interface OnItemLongClickListener<T>{
        void onItemLongClick(View view,T data,int position);
    }
    private OnItemLongClickListener onItemLongClickListener ;
    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener){
        this.onItemLongClickListener = onItemLongClickListener;
    }


    public void setOnItemClickListener(BaseRecyclerAdapter.OnItemClickListener
                                               onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * 创建布局
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER){
            return new Holder(header_view);
        }else if (viewType == TYPE_FOOTER){
            return new Holder(footer_view);
        }
        return onCreate(parent,viewType);
    }

    protected abstract RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType);

    /**
     * 绑定布局
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_HEADER ||
                getItemViewType(position) == TYPE_FOOTER){
            return;
        }

        //获得点击的position
        int layoutPosition = holder.getLayoutPosition();
        if (hasHeaderView()){
            layoutPosition = layoutPosition - 1;
        }
        final T data = datas.get(layoutPosition);
        onBind(holder,datas,position);
        final int finalLayoutPosition = layoutPosition;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(view,data, finalLayoutPosition);
            }
        });
        if (onItemLongClickListener!=null){
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    onItemLongClickListener.onItemLongClick(view,data,finalLayoutPosition);
                    return true;
                }
            });
        }

    }

    protected abstract void onBind(RecyclerView.ViewHolder holder, List<T> datas, int position);

    @Override
    public int getItemCount() {
        int count = datas  == null?0:datas.size();
        if (hasHeaderView()){
            count ++ ;
        }
        if (hasFooterView()){
            count ++ ;
        }
        return count;
    }

    /**
     * 返回条目的类型
     */
    @Override
    public int getItemViewType(int position) {
        if (isHeaderView(position)){
            return TYPE_HEADER;
        }else if (isFooterView(position)){
            return TYPE_FOOTER;
        }else {
            return TYPE_NORMAL;
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        if (mRecyclerView == null || mRecyclerView != recyclerView){
            mRecyclerView = recyclerView;
        }
    }
    /**
     * 设置了一个SpanSizeLookup，这个类是一个抽象类，而且仅有一个抽象方法getSpanSize，
     * 这个方法的返回值决定了我们每个position上的item占据的单元格个数，
     *
     */
    private void ifGridLayoutManage(){
        if (mRecyclerView == null){
            return;
        }
        //获得布局参数
        final RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager){
            ((GridLayoutManager) layoutManager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (hasFooterView() || hasHeaderView()){
                        return ((GridLayoutManager) layoutManager).getSpanCount();
                    }
                    return 1;
                }
            });
        }
    }
    /**
     * 添加头布局
     */
    public void addHeaderView(View headerView){
        if (hasHeaderView()){
            throw new IllegalStateException("headerView has already exists");
        }
        /*避免出现自适应*/
        //获得布局参数
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置布局参数
        headerView.setLayoutParams(params);
        header_view = headerView;
        ifGridLayoutManage();
        notifyItemInserted(0);
    }
    /**
     * 添加脚布局
     */
    public void addFooterView(View footerView){
        if (hasHeaderView()){
            throw new IllegalStateException("footerView has already exists");
        }
        /*避免出现自适应*/
        //获得布局参数
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置布局参数
        footerView.setLayoutParams(params);
        footer_view = footerView;
        ifGridLayoutManage();
        notifyItemInserted(getItemCount() - 1);
    }

    /**
     * 判断是否存在头部
     */
    private boolean hasHeaderView(){
        if (header_view != null){
            return true;
        }
        return false;
    }
    /**
     * 判断是否存在脚部
     */
    private boolean hasFooterView(){
        if (footer_view != null){
            return true;
        }
        return false;
    }
    /**
     * 判断是不是头布局
     */
    private boolean isHeaderView(int position){
        if (hasHeaderView() && position == 0){
            return true;
        }
        return  false;
    }
    /**
     * 判断是不是脚布局
     */
    private boolean isFooterView(int position){
        if (hasFooterView() && position == getItemCount()-1){
            return true ;
        }
        return false ;
    }

    public static class Holder extends RecyclerView.ViewHolder {
        public Holder(View view) {
            super(view);
        }
    }
}
