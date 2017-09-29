package com.example.hxl.travel.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.hxl.travel.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;
import java.util.Map;

/**
 * Created by hxl on 2017/6/29 at haiChou.
 */
public class GroupListExpandableListAdapter extends BaseExpandableListAdapter {
    //单元类
    class ExpandableListHolder {
        TextView nickName;
        RoundedImageView ioc;
    }

    //父单元
    class ExpandableGroupHolder {
        TextView title;
    }
    private List<Map<String, Object>> groupData;//组显示
    private List<List<Map<String, Object>>> childData;//子列表

    private LayoutInflater mGroupInflater; //用于加载group的布局xml
    private LayoutInflater mChildInflater; //用于加载listitem的布局xml

    //自宝义构造
    public GroupListExpandableListAdapter(Context context, List<Map<String, Object>> groupData,
                                         List<List<Map<String, Object>>> childData) {
        this.childData=childData;
        this.groupData=groupData;
        mChildInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mGroupInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    //得到组数据的数量
    @Override
    public int getGroupCount() {
        return groupData!=null&&groupData.size()>0?groupData.size():0;
    }

    //得到子数据的数量
    @Override
    public int getChildrenCount(int i) {
        return (childData.get(i)!=null&&childData.get(i).size()>0)?childData.get(i).size():0;
    }

    //得到组数据
    @Override
    public Object getGroup(int i) {
        return groupData.get(i);
    }
    //得到子数据
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childData.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return groupPosition;
    }

    //行是否具有唯一id
    @Override
    public boolean hasStableIds() {
        return false;
    }
    //行是否可选 在继承的BaseExpandableListAdapter的ExpandableListView的Adapter中，
    // 重写以下方法实现点击事件
    //然后设置ExpandableListView的 setOnChildClickListener。即可实现子Item的点击事件
    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }


    @Override
    public View getGroupView(int groupPosition, boolean flag, View convertView,
                             ViewGroup viewGroup) {
        ExpandableGroupHolder holder = null; //清空临时变量holder
        if (convertView == null) { //判断view（即view是否已构建好）是否为空

            convertView = mGroupInflater.inflate(R.layout.item_group_extendable, null);
            holder = new ExpandableGroupHolder();
            holder.title=(TextView) convertView.findViewById(R.id.main_tree_title_id);
            convertView.setTag(holder);
        } else { //若view不为空，直接从view的tag属性中获得各子视图的引用
            holder = (ExpandableGroupHolder) convertView.getTag();
        }
        String title=(String)this.groupData.get(groupPosition).get("title");
        holder.title.setText(title);
        notifyDataSetChanged();
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean flag,
                             View convertView, ViewGroup viewGroup) {
        ExpandableListHolder holder = null;
        if (convertView == null) {
            convertView = mChildInflater.inflate(R.layout.item_child_extendable, null);
            holder = new ExpandableListHolder();
            holder.nickName = (TextView) convertView.findViewById(R.id.tv_fragment_group_item);
            holder.ioc = (RoundedImageView) convertView.
                    findViewById(R.id.round_image_fragment_group_item);
            convertView.setTag(holder);
        } else {//若行已初始化，直接从tag属性获得子视图的引用
            holder = (ExpandableListHolder) convertView.getTag();
        }
        Map<String,Object> unitData=this.childData.get(groupPosition).get(childPosition);
        holder.nickName.setText((String)unitData.get("nickName"));
        holder.ioc.setImageResource((Integer) unitData.get("ico"));
        return convertView;
    }

}
