package com.example.hxl.travel.ui.view;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hxl.travel.R;
import com.example.hxl.travel.base.RootView;
import com.example.hxl.travel.model.db.RecordSQLiteOpenHelper;
import com.example.hxl.travel.model.db.SQLiteUtil;
import com.example.hxl.travel.presenter.contract.SearchContract;
import com.example.hxl.travel.ui.activitys.SearchActivity;
import com.example.hxl.travel.utils.EventUtil;
import com.example.hxl.travel.widget.SearchListView;
import com.google.common.base.Preconditions;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by hxl on 2017/1/21 at haiChou.
 */
public class SearchView extends RootView<SearchContract.Presenter> implements
        SearchContract.View, View.OnClickListener {
    @BindView(R.id.et_activity_search)
    EditText etSearch;
    @BindView(R.id.tv_activity_search_tip)
    TextView tvTip;
    @BindView(R.id.lv_activity_search)
    SearchListView listView;
    @BindView(R.id.iv_activity_search_input_clear)
    ImageView ivInputClear;

    //数据库
    private SQLiteDatabase db;
    private RecordSQLiteOpenHelper recordSQLiteOpenHelper;


    public SearchView(Context context) {
        super(context);
    }

    public SearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    protected void getLayout() {
        inflate(mContext, R.layout.activity_search_view,this);
    }

    @Override
    protected void initView() {
        //数据库帮助类
        recordSQLiteOpenHelper = SQLiteUtil.getSQLiteOpenHelper(mContext);
    }

    @Override
    protected void initEvent() {
        // 搜索框的键盘搜索键点击回调
        etSearch.setOnKeyListener(new View.OnKeyListener() {// 输入完后按键盘上的搜索键

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction()
                        == KeyEvent.ACTION_DOWN) {// 修改回车键功能
                    // 按完搜索键后将当前查询的关键字保存起来,如果该关键字已经存在就不执行保存
                    boolean hasData = SQLiteUtil.hasData(etSearch.getText().toString().trim());
                    if (!hasData) {
                        SQLiteUtil.insertData(etSearch.getText().toString().trim());
                        SQLiteUtil.queryData(mContext,listView,"");
                    }
                    // 根据输入的内容模糊查询商品，并跳转到另一个界面
                    Toast.makeText(mContext, "clicked!", Toast.LENGTH_SHORT).show();

                }
                return false;
            }
        });
        // 搜索框的文本变化实时监听
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().length() == 0) {
                    tvTip.setText(mContext.getResources().getString(R.string.search_history));
                    ivInputClear.setVisibility(GONE);
                } else {
                    tvTip.setText(mContext.getResources().getString(R.string.search_result));
                    ivInputClear.setVisibility(VISIBLE);
                }
                String tempName = etSearch.getText().toString();
                // 根据tempName去模糊查询数据库中有没有数据
                SQLiteUtil.queryData(mContext,listView,tempName);

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view.findViewById(R.id.item_recycler_search);
                String name = textView.getText().toString();
                etSearch.setText(name);
                Toast.makeText(mContext, name, Toast.LENGTH_SHORT).show();
                // 获取到item上面的文字，根据该关键字跳转到另一个页面查询
            }
        });
        // 第一次进入查询所有的历史记录
        SQLiteUtil.queryData(mContext,listView,"");
    }
    @Override
    public boolean isActive() {
        return mActive;
    }

    @Override
    public Context getSearchContext() {
        return mContext;
    }

    @Override
    public void showMassage(String massage) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void setPresenter(SearchContract.Presenter presenter) {
        mPresenter = Preconditions.checkNotNull(presenter);
    }

    @Override
    public void showError(String message) {
        EventUtil.showToast(mContext,message);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mContext = null;
        if (unbinder != null){
            unbinder.unbind();
        }
    }
    @OnClick({R.id.iv_activity_search_back,R.id.iv_activity_search_clear,
            R.id.iv_activity_search_input_clear})
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_activity_search_back:
                ((SearchActivity)mContext).finish();
                ((SearchActivity)mContext).overridePendingTransition(R.anim.enter_in_inactive,
                        R.anim.exit_out_active);
            break;
            case R.id.iv_activity_search_clear:
                SQLiteUtil.deleteData();
                SQLiteUtil.queryData(mContext,listView,"");
                break;
            case R.id.iv_activity_search_input_clear:
                String input = etSearch.getText().toString().trim();
                if (!TextUtils.isEmpty(input)){
                    etSearch.setText("");
                }
                break;
        }
    }
}
