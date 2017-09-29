package com.example.hxl.travel.presenter;

import android.content.Context;

import com.example.hxl.travel.R;
import com.example.hxl.travel.base.RxPresenter;
import com.example.hxl.travel.model.bean.User;
import com.example.hxl.travel.presenter.contract.LinkerContract;
import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by hxl on 2017/8/11 0011 at haichou.
 */

public class LinkerPresenter extends RxPresenter implements LinkerContract.Presenter{
    private LinkerContract.View mView;
    private final Context linkerContext;

    public LinkerPresenter(LinkerContract.View view){
        mView = Preconditions.checkNotNull(view);
        mView.setPresenter(this);
        linkerContext = mView.getLinkerContext();
        getData();
    }
    @Override
    public void getData() {
        String[] names = {"孙尚香", "安其拉", "白起", "不知火舞", "@小马快跑", "_德玛西亚之力_", "妲己",
                "狄仁杰", "典韦", "韩信",
                "老夫子", "刘邦", "刘禅", "鲁班七号", "墨子", "孙膑", "孙尚香", "孙悟空", "项羽", "亚瑟",
                "周瑜", "庄周", "蔡文姬", "甄姬", "廉颇", "程咬金", "后羿", "扁鹊", "钟无艳", "小乔",
                "王昭君", "虞姬",
                "李元芳", "张飞", "刘备", "牛魔", "张良", "兰陵王", "露娜", "貂蝉", "达摩", "曹操",
                "芈月", "荆轲", "高渐离",
                "钟馗", "花木兰", "关羽", "李白", "宫本武藏", "吕布", "嬴政", "娜可露露", "武则天",
                "赵云", "姜子牙"};

        List<User> datas = new ArrayList<>();
        int icon = R.mipmap.photo;
        if (datas!=null&&datas.size()>0)
            datas.removeAll(datas);
        for(int i= 0;i<names.length;i++){
            datas.add(new User(names[i],icon));
        }
        Collections.sort(datas);
        mView.showData(datas);
    }
}
