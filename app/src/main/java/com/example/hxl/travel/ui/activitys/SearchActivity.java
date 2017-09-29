package com.example.hxl.travel.ui.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.hxl.travel.R;
import com.example.hxl.travel.base.BaseActivity;
import com.example.hxl.travel.presenter.SearchPresenter;
import com.example.hxl.travel.presenter.SettingsPresenter;
import com.example.hxl.travel.ui.view.SearchView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends BaseActivity {
    @BindView(R.id.search_view)
    SearchView searchView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        unbinder = ButterKnife.bind(this);
        mPresenter = new SearchPresenter(searchView);
    }
}
