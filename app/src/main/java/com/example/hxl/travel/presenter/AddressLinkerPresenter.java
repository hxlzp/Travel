package com.example.hxl.travel.presenter;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.example.hxl.travel.base.RxPresenter;
import com.example.hxl.travel.model.bean.User;
import com.example.hxl.travel.presenter.contract.AddressLinkerContract;
import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by hxl on 2017/8/23 0023 at haichou.
 */

public class AddressLinkerPresenter extends RxPresenter implements
        AddressLinkerContract.Presenter {
    AddressLinkerContract.View mView;
    private final Context addressLinkerContext;

    public AddressLinkerPresenter(AddressLinkerContract.View view){
        mView = Preconditions.checkNotNull(view);
        mView.setPresenter(this);
        addressLinkerContext = mView.getAddressLinkerContext();
        getData();
    }

    @Override
    public void getData() {
        List<User> list = new ArrayList<>();
        Cursor cursor = addressLinkerContext.getContentResolver().
                query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null, null);
        //moveToNext方法返回的是一个boolean类型的数据
        while (cursor.moveToNext()) {
            //读取通讯录的姓名
            String name = cursor.getString(cursor
                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            //读取通讯录的号码
            String number = cursor.getString(cursor
                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            User user = new User(name, number,-1);
            list.add(user);
            Collections.sort(list);
            mView.showData(list);
        }

    }
}
