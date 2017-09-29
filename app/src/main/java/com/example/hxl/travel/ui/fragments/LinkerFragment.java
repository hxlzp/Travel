package com.example.hxl.travel.ui.fragments;


import android.support.v4.app.Fragment;
import android.view.LayoutInflater;

import com.example.hxl.travel.R;
import com.example.hxl.travel.base.BaseFragment;
import com.example.hxl.travel.presenter.LinkerPresenter;
import com.example.hxl.travel.ui.view.LinkerView;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class LinkerFragment extends BaseFragment {
    @BindView(R.id.linker_view)
    LinkerView linkerView ;

    public LinkerFragment() {
        // Required empty public constructor
    }

    @Override
    protected void initView(LayoutInflater inflater) {
        super.initView(inflater);
        mPresenter = new LinkerPresenter(linkerView);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_linker;
    }

    @Override
    protected void lazyFetchData() {
        super.lazyFetchData();
        ((LinkerPresenter)mPresenter).getData();
    }
}
