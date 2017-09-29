package com.example.hxl.travel.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by hxl on 2017/1/21 at haiChou.
 */
public class RecordSQLiteOpenHelper extends SQLiteOpenHelper {
    private static String name = "temp.db";
    private static Integer version = 1;
    public RecordSQLiteOpenHelper(Context context) {
        super(context, name, null, version);
    }

    /**
     * 创建数据库
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //执行数据库
        sqLiteDatabase.execSQL("create table records(id integer primary key autoincrement," +
                "name varchar(200))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

