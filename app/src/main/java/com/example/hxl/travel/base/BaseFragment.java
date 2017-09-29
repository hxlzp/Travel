package com.example.hxl.travel.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by hxl on 2016/12/21 at haiChou.
 */
public abstract class BaseFragment<T extends BasePresenter> extends SupportFragment {
    protected T mPresenter;
    protected Context mContext;
    protected Unbinder unbinder;
    protected View rootView;
    private boolean isViewPrepared;//标识fragment视图已经初始化完毕
    private boolean hasFetchData;//标识已经触发过懒加载数据

    /**
     * fragment已经关联到activity 只调用一次
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e(this.getClass()+"", this.getClass().getName()+"---->onAttach" );
        if (mContext !=null){
            mContext = context;
        }else {
            mContext = getContext();
        }
    }

    /**
     * 系统创建fragment的时候回调
     * 可以用于 文件保护 只调用一次
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(this.getClass()+"", this.getClass().getName()+"---->onCreate" );
    }
    /**
     * 第一次使用的时候 fragment会在这上面画一个layout出来， 为了可以画控件 要返回一个 布局的view，
     * 也可以返回null。
     * 当系统用到fragment的时候 fragment就要返回他的view，越快越好 ，所以尽量在这里不要做耗时操作，
     * 比如从数据库加载大量数据显示listview.
     * 加载fragment的布局
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.e(this.getClass()+"", this.getClass().getName()+"---->onCreateView" );
        if (rootView == null){
            rootView = inflater.inflate(getLayout(),container,false);
        }
        unbinder = ButterKnife.bind(this,rootView);
        initView(inflater);
        //EventBus.getDefault().register(this);
        return rootView;
    }

    /**
     *  当Activity中的onCreate方法执行完后调用。
     *  所以不能在onCreateView()中进行 与activity有交互的UI操作，
     *  UI交互操作可以在onActivityCreated()里面进行。
     *  所以呢：这个方法主要是初始化那些你需要你的父Activity
     *  或者Fragment的UI已经被完整初始化才能初始化的元素。
     *  如果在onCreateView里面初始化空间 会慢很多，比如listview等
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e(this.getClass()+"", this.getClass().getName()+"---->onActivityCreated" );
        initEvent();
    }

    /**
     * 视图被创建完成的时候回调
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e(this.getClass()+"", this.getClass().getName()+"---->onViewCreated" );
        isViewPrepared = true;
        lazyFetchDataIfPrepared();

    }

    /**
     * 和activity一致，启动Fragement 启动时回调,，此时Fragement可见
     */

    @Override
    public void onStart() {
        super.onStart();
        Log.e(this.getClass()+"", this.getClass().getName()+"---->onStart" );
    }
    /**
     * 和activity一致  在activity中运行是可见的。激活, Fragement 进入前台, 可获取焦点时激活
     */
    @Override
    public void onResume() {
        super.onResume();
        Log.e(this.getClass()+"", this.getClass().getName()+"---->onResume" );
    }
    /**
     *  和activity一致  其他的activity获得焦点，这个仍然可见
     *  第一次调用的时候，指的是 用户 离开这个fragment（并不是被销毁）
     *  通常用于 用户的提交（可能用户离开后不会回来了）
     */
    @Override
    public void onPause() {
        super.onPause();
        Log.e(this.getClass()+"", this.getClass().getName()+"---->onPause" );
    }
    /**
     *  和activity一致， fragment不可见的，
     *  可能情况：activity被stopped了OR fragment被移除但被加入到回退栈中，
     *  一个stopped的fragment仍然是活着的如果长时间不用也会被移除。
     */
    @Override
    public void onStop() {
        super.onStop();
        Log.e(this.getClass()+"", this.getClass().getName()+"---->onStop" );
    }
    /**
     *   Fragment中的布局被移除时调用。
     *   表示fragemnt销毁相关联的UI布局， 清除所有跟视图相关的资源。
     *   ViewPager+Fragment，由于ViewPager的缓存机制，每次都会加载3页。
     *   例如：有四个 fragment 当滑动到第四页的时候 第一页执行onDestroyView(),但没有
     *   执行onDestroy。他依然和activity关联。当在滑动到第一页的时候又执行了onCreateView()。
     *   会出现重复加载view的局面
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // view被销毁后，将可以重新触发数据懒加载，因为在viewpager下，
        // fragment不会再次新建并走onCreate的生命周期流程，将从onCreateView开始
        hasFetchData = false;
        isViewPrepared = false;
        mPresenter = null;
        Log.e(this.getClass()+"", this.getClass().getName()+"---->onDestroyView" );
    }
    /**
     *   销毁fragment对象， 跟activity类似
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(this.getClass()+"", this.getClass().getName()+"---->onDestroy" );
        if (unbinder != null){
            unbinder.unbind();
        }
        mContext = null;
    }
    /**
     *   Fragment和Activity解除关联的时候调用。 脱离activity
     */
    @Override
    public void onDetach() {
        super.onDetach();
        Log.e(this.getClass()+"", this.getClass().getName()+"---->onDetach" );
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            lazyFetchDataIfPrepared();
        }
    }

    protected void initView(LayoutInflater inflater){}

    protected void initEvent(){}

    protected abstract int getLayout();

    private void lazyFetchDataIfPrepared() {
        // 用户可见fragment && 没有加载过数据 && 视图已经准备完毕
        if (getUserVisibleHint()&&!hasFetchData&&isViewPrepared){
            hasFetchData = true;
            lazyFetchData();
        }
    }
    /**
     * 懒加载的方式获取数据，仅在满足fragment可见和视图已经准备好的时候调用一次
     */
    protected void lazyFetchData() {

    }


}
