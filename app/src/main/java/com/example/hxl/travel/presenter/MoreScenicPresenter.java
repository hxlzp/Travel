package com.example.hxl.travel.presenter;

import com.example.hxl.travel.R;
import com.example.hxl.travel.base.RxPresenter;
import com.example.hxl.travel.model.bean.RecommendList;
import com.example.hxl.travel.presenter.contract.MoreScenicContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hxl on 2017/8/14 0014 at haichou.
 */

public class MoreScenicPresenter extends RxPresenter
        implements MoreScenicContract.Presenter {
    private MoreScenicContract.View mView;
    public MoreScenicPresenter(MoreScenicContract.View view){
        mView = view;
        mView.setPresenter(this);
        getData();
    }
    @Override
    public void getData() {
        final List<RecommendList> datas = new ArrayList<>();
        int[] iconLists = {R.mipmap.ic_home_list1,R.mipmap.ic_home_list2,
                R.mipmap.ic_home_list3,R.mipmap.ic_home_list4,
                R.mipmap.ic_home_list5,R.mipmap.ic_home_list6};
        String[] titleItems = {"三清茶","石牛山生态旅游区","华克山庄","牛歌山庄","向天岭水库",
                "亿年火山石"};
        String[] contentItems = {"三清茶故乡——杭州·戴村","石牛山生态旅游区——云石生态示范区",
                "华克山庄","云石星空民宿","休闲避暑好地方","亿年火山石"};
        if (datas!=null&datas.size()>0)
            datas.removeAll(datas);
        for(int i = 0;i< iconLists.length;i++){
            datas.add(new RecommendList(iconLists[i],titleItems[i],contentItems[i]));
        }
        mView.showData(datas);
    }
}
