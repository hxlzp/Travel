package com.example.hxl.travel.presenter;

import com.example.hxl.travel.base.RxPresenter;
import com.example.hxl.travel.presenter.contract.SearchContract;
import com.example.hxl.travel.ui.view.SearchView;
import com.google.common.base.Preconditions;

/**
 * Created by hxl on 2017/1/21 at haiChou.
 */
public class SearchPresenter extends RxPresenter implements SearchContract.Presenter{
    SearchView mSearchView;

    public SearchPresenter(SearchView searchView) {
        mSearchView = Preconditions.checkNotNull(searchView);
        mSearchView.setPresenter(this);
        getData();
    }

    @Override
    public void getData() {

    }
}
