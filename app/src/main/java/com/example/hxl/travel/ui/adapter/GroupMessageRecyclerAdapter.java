package com.example.hxl.travel.ui.adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.hxl.travel.R;
import com.example.hxl.travel.base.BaseRecyclerAdapter;
import com.example.hxl.travel.model.bean.GroupMessage;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;
import java.util.Vector;

/**
 * Created by hxl on 2017/6/8 at haiChou.
 */
public class GroupMessageRecyclerAdapter extends BaseRecyclerAdapter<GroupMessage> {
    Vector<Boolean> vector = new Vector<>();
    private boolean isShow;
    public GroupMessageRecyclerAdapter(Context mContext, List<GroupMessage> datas,
                                       Vector<Boolean> vector) {
        super.mContext = mContext;
        super.datas = datas;
        this.vector = vector;
    }
    public void setVector(Vector<Boolean> vector) {
        this.vector = vector;
    }

    public void setShow(boolean show) {
        isShow = show;
    }
    /*设置点击删除按钮接口*/
    public interface OnDeleteItemClickListener{
        void onDeleteItemClick(View view, List<GroupMessage> datas, int position);
    }
    OnDeleteItemClickListener onDeleteItemClickListener ;

    public void setOnDeleteItemClickListener(OnDeleteItemClickListener
                                                     onDeleteItemClickListener) {
        this.onDeleteItemClickListener = onDeleteItemClickListener;
    }

    @Override
    protected RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_fragment_group_message,
                null);
        return new Holder(view);
    }

    @Override
    protected void onBind(RecyclerView.ViewHolder holder,
                          final List<GroupMessage> datas, int position) {
        RoundedImageView roundedImageView = (RoundedImageView) holder.itemView.findViewById(R.id.round_image_fragment_group_message_item);
        TextView name = (TextView) holder.itemView
                .findViewById(R.id.tv_fragment_group_message_name_item);
        TextView message = (TextView) holder.itemView
                .findViewById(R.id.tv_fragment_group_message_mes_item);
        TextView time = (TextView) holder.itemView
                .findViewById(R.id.tv_fragment_group_message_time_item);
        Button deleteBtn = (Button) holder.itemView
                .findViewById(R.id.btn_fragment_group_item_delete);
        final int layoutPosition = holder.getLayoutPosition();
        roundedImageView.setImageResource(datas.get(layoutPosition).getImage());
        name.setText(datas.get(layoutPosition).getName());
        message.setText(datas.get(layoutPosition).getMessage());
        time.setText(datas.get(layoutPosition).getTime());

        if(vector.get(layoutPosition)){
            deleteBtn.setVisibility(View.VISIBLE);
            ObjectAnimator animator = ObjectAnimator
                    .ofFloat(deleteBtn,"alpha",0f, 0.2f, 0.5f, 0.8f, 1f);
            animator.setDuration(1000);
            animator.start();
        }else{
            deleteBtn.setVisibility(View.GONE);
            ObjectAnimator animator = ObjectAnimator
                    .ofFloat(deleteBtn,"alpha",1.0f, 0.8f, 0.5f, 0.2f, 0f);
            animator.setDuration(1000);
            animator.start();
        }
        if (isShow){
            deleteBtn.setVisibility(View.VISIBLE);
        }else {
            deleteBtn.setVisibility(View.GONE);
        }

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDeleteItemClickListener.onDeleteItemClick(view,datas,layoutPosition);
            }
        });
    }
}
