package com.example.hxl.travel.ui.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.hxl.travel.R;
import com.example.hxl.travel.base.BaseActivity;
import com.example.hxl.travel.presenter.GroupDeleteMemberPresenter;
import com.example.hxl.travel.ui.view.GroupDeleteMemberView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GroupDeleteMemberActivity extends BaseActivity {
    @BindView(R.id.group_delete_member_view)
    GroupDeleteMemberView groupDeleteMemberView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_delete_member);
        unbinder = ButterKnife.bind(this);
        mPresenter = new GroupDeleteMemberPresenter(groupDeleteMemberView);
    }
}
