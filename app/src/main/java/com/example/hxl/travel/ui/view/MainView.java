package com.example.hxl.travel.ui.view;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.hxl.travel.R;
import com.example.hxl.travel.base.RootView;
import com.example.hxl.travel.model.event.MarqueeStart;
import com.example.hxl.travel.model.event.MarqueeStop;
import com.example.hxl.travel.presenter.contract.MainContract;
import com.example.hxl.travel.ui.activitys.MainActivity;
import com.example.hxl.travel.ui.adapter.ContentPagerAdapter;
import com.example.hxl.travel.ui.fragments.FriendFragment;
import com.example.hxl.travel.ui.fragments.MineFragment;
import com.example.hxl.travel.ui.fragments.PrivilegeFragment;
import com.example.hxl.travel.ui.fragments.RecommendFragment;
import com.example.hxl.travel.utils.EventUtil;
import com.example.hxl.travel.widget.UnScrollViewPager;
import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import de.greenrobot.event.EventBus;

/**
 * Created by hxl on 2016/12/23 at haiChou.
 */
public class MainView extends RootView<MainContract.Presenter>
        implements MainContract.View ,RadioGroup.OnCheckedChangeListener {
    final int WAIT_TIME = 200;
    @BindView(R.id.tab_rg_menu)
    RadioGroup tabRgMenu;
    @BindView(R.id.vp_content)
    UnScrollViewPager vpContent;
    ContentPagerAdapter mPagerAdapter;
    MainActivity mActivity;

    public MainView(Context context) {
        super(context);
    }

    public MainView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    protected void getLayout() {
        inflate(mContext, R.layout.activity_main_view, this);
    }
    @Override
    protected void initView() {
        mActivity = (MainActivity) mContext;
        List<Fragment> fragments = initFragments();
        vpContent.setScrollable(false);
        mPagerAdapter = new ContentPagerAdapter(mActivity.getSupportFragmentManager(),
                fragments);
        vpContent.setAdapter(mPagerAdapter);
        vpContent.setOffscreenPageLimit(fragments.size());
    }
    @Override
    protected void initEvent() {
        tabRgMenu.setOnCheckedChangeListener(this);
        vpContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ((RadioButton) tabRgMenu.getChildAt(position)).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        mPresenter = Preconditions.checkNotNull(presenter);
    }

    @Override
    public void showError(String message) {
        EventUtil.showToast(mContext,message);
    }


    private List<Fragment> initFragments() {
        List<Fragment> fragments = new ArrayList<>();
        Fragment fragment1 = new RecommendFragment();
        Fragment fragment3 = new FriendFragment();
        Fragment fragment2 = new PrivilegeFragment();
        Fragment mineFragment = new MineFragment();
        fragments.add(fragment1);
        fragments.add(fragment2);
        fragments.add(fragment3);
        fragments.add(mineFragment);
        return fragments;
    }


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        switch (checkedId) {
            case R.id.tab_rb_1:
                vpContent.setCurrentItem(0, false);
                EventBus.getDefault().post(new MarqueeStart());
                break;
            case R.id.tab_rb_2:
                vpContent.setCurrentItem(1, false);
                EventBus.getDefault().post(new MarqueeStop());
                break;
            case R.id.tab_rb_3:
                vpContent.setCurrentItem(2, false);
                EventBus.getDefault().post(new MarqueeStop());
                break;
            case R.id.tab_rb_4:
                vpContent.setCurrentItem(3, false);
                EventBus.getDefault().post(new MarqueeStop());
                break;
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        //EventBus.getDefault().register(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        //EventBus.getDefault().unregister(this);
        unbinder.unbind();
        unbinder = null;
        mContext = null;
    }
}
