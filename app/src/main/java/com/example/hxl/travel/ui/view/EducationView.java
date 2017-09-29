package com.example.hxl.travel.ui.view;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.hxl.travel.R;
import com.example.hxl.travel.base.RootView;
import com.example.hxl.travel.model.event.Education;
import com.example.hxl.travel.presenter.contract.EducationContract;
import com.example.hxl.travel.ui.activitys.EducationActivity;
import com.example.hxl.travel.ui.adapter.PersonRecyclerAdapter;
import com.example.hxl.travel.utils.EventUtil;
import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by hxl on 2017/1/6 at haiChou.
 */
public class EducationView extends RootView<EducationContract.Presenter>
        implements EducationContract.View,  PersonRecyclerAdapter.OnItemClickListener<String> ,
        View.OnClickListener{
    @BindView(R.id.rv_activity_education)
    RecyclerView recyclerView ;
    private List<String> datas = new ArrayList<>();
    public EducationView(Context context) {
        super(context);
    }

    public EducationView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EducationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    protected void getLayout() {
        inflate(mContext, R.layout.activity_education_view,this);
    }

    @Override
    protected void initView() {
        //获得线性管理器对象
        LinearLayoutManager manager =
                new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false);
        //设置管理器对象
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected void initEvent() {

    }
    @Override
    public boolean isActive() {
        return mActive;
    }

    @Override
    public void showData(List<String> data) {
        //创建适配器
        PersonRecyclerAdapter adapter = new PersonRecyclerAdapter(data,mContext);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
    }

    @Override
    public Context getEducationContext() {
        return mContext;
    }

    @Override
    public void setPresenter(EducationContract.Presenter presenter) {
        mPresenter = Preconditions.checkNotNull(presenter);
    }

    @Override
    public void showError(String message) {
        EventUtil.showToast(mContext,message);
    }

    /**
     * RecyclerView点击事件
     */
    @Override
    public void onItemClick(View view, String data, int position) {
        ViewGroup viewGroup = (ViewGroup) view.getParent();
        for (int i = 0 ;i<datas.size();i++){
            View childView = viewGroup.getChildAt(i);
            ImageView imageView = (ImageView) childView.findViewById(R.id.iv_activity_person);
            imageView.setVisibility(INVISIBLE);
        }
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_activity_person);
        Button button = (Button) view.findViewById(R.id.btn_activity_person);
        if (imageView.INVISIBLE == INVISIBLE){
            imageView.setVisibility(VISIBLE);
        }
        //发送消息
        EventBus.getDefault().post(new Education(button.getText().toString().trim()));
        ((EducationActivity)mContext).finish();
    }
    @OnClick({R.id.iv_activity_education_back})
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_activity_education_back:
                ((EducationActivity)mContext).finish();
            break;
        }
    }
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (unbinder!=null){
            unbinder.unbind();
        }
        mContext = null;
    }
}
