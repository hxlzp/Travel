package com.example.hxl.travel.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jude.rollviewpager.adapter.StaticPagerAdapter;

import java.util.List;

/**
 * Created by hxl on 2016/12/29 at haiChou.
 */
public class RollPagerAdapter extends StaticPagerAdapter {

    private Context ctx;
    private List<Integer> list;

    public RollPagerAdapter(Context ctx, List<Integer> list) {
        this.ctx = ctx;
        this.list = list;
        removeEmpty(this.list);
    }

    private void removeEmpty(List<Integer> list) {
        for (int i = 0; i < list.size(); i++) {

        }
    }

    @Override
    public View getView(ViewGroup container, final int position) {
        ImageView imageView = new ImageView(ctx);
        imageView.setLayoutParams(new
                ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setBackgroundResource(list.get(position));
        //加载图片
        //ImageLoader.load(ctx, list.get(position), imageView);
        //点击事件
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return imageView;
    }

    @Override
    public int getCount() {
        return list.size();
    }
}