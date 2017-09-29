package com.example.hxl.travel.model.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;

import com.example.hxl.travel.R;
import com.example.hxl.travel.widget.SearchListView;

/**
 * Created by hxl on 2017/1/22 at haiChou.
 */
public class SQLiteUtil {

    private static RecordSQLiteOpenHelper recordSQLiteOpenHelper;

    public static RecordSQLiteOpenHelper getSQLiteOpenHelper(Context context){
        recordSQLiteOpenHelper = new RecordSQLiteOpenHelper(context);
        return recordSQLiteOpenHelper;
    }

    /**
     * 插入数据
     */
    public static void insertData(String data){
        SQLiteDatabase database = recordSQLiteOpenHelper.getWritableDatabase();
        database.execSQL("insert into records(name) values('" + data + "')");
        database.close();
    }
    /**
     * 判断数据库中是否存在数据
     */
    public static boolean hasData(String data){
        SQLiteDatabase database = recordSQLiteOpenHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("select id as _id,name from records where name = ?",
                new String[]{data});
        return cursor.moveToNext();
    }
    /**
     * 清空数据
     */
    public static void deleteData(){
        SQLiteDatabase database = recordSQLiteOpenHelper.getWritableDatabase();
        database.execSQL("delete from records");
        database.close();
    }
    /**
     * 模糊查询数据库
     */
    public static void queryData(Context mContext, SearchListView listView,String data){
        SQLiteDatabase database = recordSQLiteOpenHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("select id as _id,name from records where name " +
                "like '%" + data + "%' order by id desc", null);
        // 创建adapter适配器对象
        BaseAdapter adapter = new SimpleCursorAdapter(mContext, R.layout.search_recycler_item ,
                cursor, new String[] { "name" },
                new int[] { R.id.item_recycler_search },
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        // 设置适配器
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

}
