package com.example.hxl.travel.presenter;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.example.hxl.travel.R;
import com.example.hxl.travel.base.RxPresenter;
import com.example.hxl.travel.model.bean.ImageRecommend;
import com.example.hxl.travel.model.bean.RecommendList;
import com.example.hxl.travel.presenter.contract.RecommendContract;
import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hxl on 2016/12/27 at haiChou.
 */
public class RecommendPresenter extends RxPresenter implements RecommendContract.Presenter {
    RecommendContract.View mView;
    private List<String> datas = new ArrayList<>() ;
    private final Context recommendContext;
    List<ImageRecommend> gridDatas = new ArrayList<>() ;

    public RecommendPresenter(@NonNull RecommendContract.View view) {
        mView = Preconditions.checkNotNull(view);
        mView.setPresenter(this);
        recommendContext = mView.getRecommendContext();
        getData();
    }

    @Override
    public void getData() {
        getMarqueeData();
        getTabData();
    }

    @Override
    public void onRefresh() {
        getPageRecommendInfo();
    }

    private void getPageRecommendInfo() {
        mView.showProgress();
        int[] icons = {R.mipmap.ic_friends_banner,R.mipmap.ic_home_banner};
        final List<RecommendList> data = new ArrayList<>();
        String[] imageItems = {"http://www.xs163.net/eweb/UploadFile/20174581640424.jpg",
        "http://img2.imgtn.bdimg.com/it/u=3197062036,695154814&fm=15&gp=0.jpg"};
        int[] iconLists = {R.mipmap.ic_home_list1,R.mipmap.ic_home_list2,
                R.mipmap.ic_home_list3,R.mipmap.ic_home_list4,
                R.mipmap.ic_home_list5,R.mipmap.ic_home_list6};
        String[] titleItems = {"三清茶","石牛山生态旅游区","华克山庄","牛歌山庄","向天岭水库",
        "亿年火山石"};
        String[] contentItems = {"三清茶故乡——杭州·戴村","石牛山生态旅游区——云石生态示范区",
        "华克山庄","云石星空民宿","休闲避暑好地方","亿年火山石"};
        for(int i = 0;i< iconLists.length;i++){
            data.add(new RecommendList(iconLists[i],titleItems[i],contentItems[i]));
        }
        final List<Integer> iconsData = new ArrayList<>();
        if (iconsData!=null&iconsData.size()>0)
            iconsData.removeAll(iconsData);
        for (int i = 0;i < icons.length;i++){
            iconsData.add(icons[i]);
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mView.showContent(data,iconsData);
                mView.hideProgress();
            }
        }, 3000);
    }

    @Override
    public void onLoad() {

    }
    /*跑马灯数据*/
    @Override
    public void getMarqueeData() {
        String[] titles = {"戴村 · 纯生态旅游度假基地","沈村 · 沈村三清茶文化节",
                "尖山下村 · 一村一景","云石 · 石牛山生态旅游区"};
        for(int i= 0;i< titles.length;i++){
            datas.add(titles[i]);
        }
        mView.showMarqueeData(datas);
    }
    /*tab数据*/
    @Override
    public void getTabData() {
        String[] names = recommendContext.getResources().getStringArray(R.array.gridNames);
        int[] icons = {R.mipmap.ic_home_cate,R.mipmap.ic_home_stay,R.mipmap.ic_home_play,
                R.mipmap.ic_home_relaxation,R.mipmap.ic_home_police,R.mipmap.ic_home_hospital,
                R.mipmap.ic_home_wc,R.mipmap.ic_home_car};
        for(int i = 0;i<names.length;i++){
            gridDatas.add(new ImageRecommend(icons[i],names[i]));
        }
        mView.showTabData(gridDatas);
    }

}
