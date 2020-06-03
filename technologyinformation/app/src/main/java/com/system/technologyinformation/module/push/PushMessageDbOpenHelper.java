package com.system.technologyinformation.module.push;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.system.technologyinformation.common.net.Constant;

public class PushMessageDbOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = "TestSQLite";
    public static final int VERSION = 1;
    //必须要有构造函数
    public PushMessageDbOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                                   int version) {
        super(context, name, factory, version);
    }

    // 当第一次创建数据库的时候，调用该方法
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table "+ Constant.pushMessageDbTable+"("+Constant.pushMessageDbTableColumn2+" varchar(100),"+Constant.pushMessageDbTableColumn3+" varchar(100),"+Constant.pushMessageDbTableColumn4+" varchar(30))";
//输出创建数据库的日志信息
        Log.i(TAG, "create Database------------->");
//execSQL函数用于执行SQL语句
        db.execSQL(sql);
    }

    //当更新数据库的时候执行该方法
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //输出更新数据库的日志信息
        Log.i(TAG, "update Database------------->");
    }

}
