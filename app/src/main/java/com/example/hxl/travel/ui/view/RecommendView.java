package com.example.hxl.travel.ui.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hxl.travel.R;
import com.example.hxl.travel.base.BaseRecyclerAdapter;
import com.example.hxl.travel.base.RootView;
import com.example.hxl.travel.model.bean.ImageRecommend;
import com.example.hxl.travel.model.bean.RecommendList;
import com.example.hxl.travel.model.event.MarqueeStart;
import com.example.hxl.travel.model.event.MarqueeStop;
import com.example.hxl.travel.presenter.contract.RecommendContract;
import com.example.hxl.travel.ui.activitys.MainActivity;
import com.example.hxl.travel.ui.activitys.MoreScenicActivity;
import com.example.hxl.travel.ui.activitys.ScannerActivity;
import com.example.hxl.travel.ui.activitys.ScenicDetailsActivity;
import com.example.hxl.travel.ui.activitys.SelectLocationActivity;
import com.example.hxl.travel.ui.adapter.RecommendGridAdapter;
import com.example.hxl.travel.ui.adapter.RecyclerViewAdapter;
import com.example.hxl.travel.ui.adapter.RollPagerAdapter;
import com.example.hxl.travel.utils.EventUtil;
import com.example.hxl.travel.utils.JumpUtil;
import com.example.hxl.travel.utils.ScreenUtil;
import com.example.hxl.travel.utils.ToastUtil;
import com.example.hxl.travel.utils.springutil.IconHeader;
import com.example.hxl.travel.widget.CustomGridView;
import com.example.hxl.travel.widget.RollPagerView;
import com.example.hxl.travel.widget.SpringView;
import com.example.hxl.travel.widget.UpMarqueeView;
import com.google.common.base.Preconditions;
import com.jude.rollviewpager.hintview.IconHintView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**
 * Created by hxl on 2016/12/26 at haiChou.
 * 首页界面层：展示数据，响应首页用户事件，并通知Presenter
 */
public class RecommendView extends RootView<RecommendContract.Presenter>  implements
        RecommendContract.View, SpringView.OnFreshListener, View.OnClickListener,
        BaseRecyclerAdapter.OnItemClickListener,
        UpMarqueeView.OnItemClickListener {
    @BindView(R.id.fragment_recommend_spring)
    SpringView springView ;
    @BindView(R.id.fragment_recommend_list)
    RecyclerView recyclerView ;
    @BindView(R.id.title)
    RelativeLayout title;
    @BindView(R.id.iv_progress)
    ImageView ivProgress ;
    @BindView(R.id.rl_progress)
    RelativeLayout rlProgress;
    View headView;
    //RelativeLayout rlGoSearch;
    RollPagerView rollPagerView ;
    private RecyclerViewAdapter adapter;

    private int[] refreshAnimSrcs = new int[]{R.mipmap.ptr_1,R.mipmap.ptr_2,
            R.mipmap.ptr_3,R.mipmap.ptr_4,R.mipmap.ptr_5,
            R.mipmap.ptr_6};
    private Intent intent;
    private UpMarqueeView upMarqueeView;
    private List<View> views = new ArrayList<>();
    private CustomGridView gridView;
    private TextView tvMoreScenic;

    /*进度条*/
    private AnimationDrawable animationDrawable;


    public RecommendView(Context context) {
        super(context);
    }

    public RecommendView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RecommendView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    protected void getLayout() {
        inflate(mContext, R.layout.fragment_recommend_view,this);
    }

    @Override
    protected void initView() {

        intent = new Intent();
        //加载头部布局
        headView = inflate(mContext,R.layout.header_recommend, null);
        gridView = ButterKnife.findById(headView, R.id.grid_view_fragment_recommend);
        //rlGoSearch = ButterKnife.findById(headView, R.id.rl_go_search);
        rollPagerView = ButterKnife.findById(headView,R.id.pv_fragment_recommend_banner);
        upMarqueeView = ButterKnife.findById(headView, R.id.marquee_fragment_recommend_marquee);
        tvMoreScenic = ButterKnife.findById(headView, R.id.tv_recommend_more_scenic);
        //轮播
        rollPagerView.setPlayDelay(2000);
        //获得网格布局管理器对象
        //GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext,3);
        //获得线性布局管理器对象
        LinearLayoutManager manager = new LinearLayoutManager(mContext,
                LinearLayoutManager.VERTICAL,false);
        //设置管理器对象
        recyclerView.setLayoutManager(manager);
        //设置动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        /**
         * setHasFixedSize 设置具有固定的尺寸
         * 作用就是确保尺寸是通过用户输入从而确保RecyclerView的尺寸是一个常数。
         * RecyclerView 的Item宽或者高不会变。每一个Item添加或者删除都不会变。
         * 如果你没有设置setHasFixedSized没有设置的代价将会是非常昂贵的。
         * 因为RecyclerView会需要而外计算每个item的size，
         */
        recyclerView.setHasFixedSize(true);
        springView.setType(SpringView.Type.FOLLOW);

    }

    @Override
    protected void initEvent() {
        springView.setListener(this);
        springView.setHeader(new IconHeader(mContext,null,refreshAnimSrcs));
        //rlGoSearch.setOnClickListener(this);
        upMarqueeView.setOnItemClickListener(this);
        tvMoreScenic.setOnClickListener(this);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (getHeaderScroll() <= ScreenUtil.dip2px(mContext, 150)){
                    new Handler().postAtTime(new Runnable() {
                        @Override
                        public void run() {
                            float percentage = (float) getHeaderScroll() /
                                    ScreenUtil.dip2px(mContext, 150);
                            title.setAlpha(percentage);
                            if (percentage > 0)
                               title.setVisibility(View.VISIBLE);
                            else
                                title.setVisibility(View.GONE);

                        }
                    }, 300);
                } else {
                    title.setAlpha(1.0f);
                    title.setVisibility(View.VISIBLE);
                }
            }
        });
    }
    private int getHeaderScroll() {
        if (headView == null) {
            return 0;
        }
        int distance = headView.getTop();
        distance = Math.abs(distance);
        return distance;
    }

    @Override
    public boolean isActive() {
        return mActive;
    }

    @Override
    public void showContent(List<RecommendList> data, List<Integer> icons) {
        if (data!=null){
            adapter = new RecyclerViewAdapter(data,mContext);
            recyclerView.setAdapter(adapter);
            adapter.addHeaderView(headView);
            rollPagerView.setHintView(new IconHintView(getContext(),
                    R.mipmap.ic_page_indicator_focused,
                    R.mipmap.ic_page_indicator, ScreenUtil.dip2px(getContext(), 10)));
            rollPagerView.setHintPadding(0, 0, 0, ScreenUtil.dip2px(getContext(), 8));
            rollPagerView.setAdapter(new RollPagerAdapter(getContext(), icons));
            adapter.setOnItemClickListener(this);
        }

    }

    @Override
    public void refreshError(String msg) {
        EventUtil.showToast(mContext,msg);
    }

    @Override
    public void showProgress() {
        rlProgress.setVisibility(VISIBLE);
        animationDrawable = (AnimationDrawable) ivProgress.getBackground();
        animationDrawable.start();

    }

    @Override
    public void hideProgress() {
        if (animationDrawable.isRunning())
            animationDrawable.stop();
        rlProgress.setVisibility(GONE);
    }
    /*显示跑马灯数据*/
    private List<String> datas = new ArrayList<>() ;

    @Override
    public void showMarqueeData(List<String> datas) {
        this.datas = datas;
        initMarqueeData();

    }

    private void initMarqueeData() {
        if (views != null)
            views.removeAll(views);
        for(int i = 0;i<datas.size();i++){
            /*加载布局*/
            View marqueeView = LayoutInflater.from(mContext).inflate(R.layout.item_marquee,null);
            TextView textViewOne = ButterKnife.findById(marqueeView,R.id.tv_item_marquee_one);
            TextView textViewTwo = ButterKnife.findById(marqueeView,R.id.tv_item_marquee_two);
            /*赋值*/
            textViewOne.setText(datas.get(i));
            if (datas.size() > i+1){
                textViewTwo.setText(datas.get(i+1));
            }else {
                textViewTwo.setVisibility(GONE);
            }
            views.add(marqueeView);
        }
        upMarqueeView.setViews(views);
        upMarqueeView.startFlip();
    }

    @Override
    public Context getRecommendContext() {
        return mContext;
    }

    /*tab数据*/
    @Override
    public void showTabData(List<ImageRecommend> datas) {
        RecommendGridAdapter adapter = new RecommendGridAdapter(mContext,datas) ;
        gridView.setAdapter(adapter);
    }



    @Override
    public void setPresenter(RecommendContract.Presenter presenter) {
        mPresenter = Preconditions.checkNotNull(presenter);
    }

    @Override
    public void showError(String message) {
        EventUtil.showToast(mContext,message);
    }

    /*springView刷新加载监听*/
    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                springView.onFinishFreshAndLoad();
            }
        }, 2000);
    }
    @Override
    public void onLoadmore() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                springView.onFinishFreshAndLoad();
            }
        }, 2000);
    }

    /*********************Button按钮点击事件****************************/
    @OnClick({R.id.btn_fragment_recommend_grcode,R.id.tv_fragment_recommend_location})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.rl_go_search:
//                JumpUtil.jump(mContext,SearchActivity.class);
//                ((MainActivity) mContext).overridePendingTransition(R.anim.enter_in_active,
//                        R.anim.exit_out_inactive);
//                break;
            case R.id.btn_fragment_recommend_grcode:
                JumpUtil.jump(mContext,ScannerActivity.class);
                ((MainActivity) mContext).overridePendingTransition(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                break;
            case R.id.tv_fragment_recommend_location://定位
                JumpUtil.jump(mContext, SelectLocationActivity.class);
                ((MainActivity) mContext).overridePendingTransition(R.anim.enter_in_active,
                        R.anim.exit_out_inactive);
                break;
            case R.id.tv_recommend_more_scenic:
                JumpUtil.jump(mContext, MoreScenicActivity.class);
                break;
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        EventBus.getDefault().unregister(this);
        unbinder.unbind();
        unbinder = null;
        mContext = null;
    }
    /*Recycler Item点击事件*/
    @Override
    public void onItemClick(View view, Object data, int position) {
        RecommendList recommendList = (RecommendList) data;
        String title = recommendList.getTitle();
        jumpWebActivity(title);
    }

    private void jumpWebActivity(String title) {
        Bundle bundle = new Bundle() ;
        bundle.putString("title",title);
        intent.putExtras(bundle);
        intent.setClass(mContext,ScenicDetailsActivity.class);
        mContext.startActivity(intent);
    }
    /*跑马灯监听*/
    @Override
    public void onItemClick(View view, int position) {
        ToastUtil.showToast(mContext,"点击了第" + position +"条数据");
    }
    /*跑马灯事件*/
    @Subscribe(threadMode = ThreadMode.PostThread)
    public void handlerMarqueeStart(MarqueeStart marqueeStart){
        upMarqueeView.startFlip();
    }
    /*跑马灯事件*/
    @Subscribe(threadMode = ThreadMode.PostThread)
    public void handlerMarqueeStop(MarqueeStop marqueeStop){
        upMarqueeView.stopFlip();
    }


}
