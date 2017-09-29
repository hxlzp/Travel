package com.example.hxl.travel.ui.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.hxl.travel.R;
import com.example.hxl.travel.base.BaseActivity;
import com.example.hxl.travel.presenter.GroupChatPresenter;
import com.example.hxl.travel.ui.view.GroupChatView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GroupChatActivity extends BaseActivity {
    @BindView(R.id.activity_group_chat_view)
    GroupChatView groupChatView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);
        unbinder = ButterKnife.bind(this);
        mPresenter = new GroupChatPresenter(groupChatView);
    }
}
