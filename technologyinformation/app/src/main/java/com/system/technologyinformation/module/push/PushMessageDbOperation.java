package com.system.technologyinformation.module.push;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.system.technologyinformation.common.net.Constant;

import java.util.Date;

public class PushMessageDbOperation {
    private static final String TAG = "PushMessageDbOperation";
    public static boolean queryPushMessage(SQLiteDatabase db, String ident) {
        boolean isExist=false;
        if (!EmptyUtils.isEmpty(db)) {
            Cursor cursor = db.query(Constant.pushMessageDbTable, new String[]{Constant.pushMessageDbTableColumn2, Constant.pushMessageDbTableColumn3}, Constant.pushMessageDbTableColumn3 + "=?", new String[]{ident}, null, null, null);
            while (cursor.moveToNext()) {
                String content = cursor.getString(cursor.getColumnIndex(Constant.pushMessageDbTableColumn3));
                System.out.println("query------->" + "内容：" + content);
                return true;
            }
            //关闭数据库
            db.close();
        } else {
            isExist = false;
        }
        return isExist;
    }

    public static void queryPushMessageAll(SQLiteDatabase db) {
        if (!EmptyUtils.isEmpty(db)) {
            Cursor cursor = db.query(Constant.pushMessageDbTable, new String[]{Constant.pushMessageDbTableColumn2, Constant.pushMessageDbTableColumn3}, null, null, null, null, null);
            while (cursor.moveToNext()) {
                String content = cursor.getString(cursor.getColumnIndex(Constant.pushMessageDbTableColumn2));
                String ident = cursor.getString(cursor.getColumnIndex(Constant.pushMessageDbTableColumn3));
                String time = cursor.getString(cursor.getColumnIndex(Constant.pushMessageDbTableColumn4));
                System.out.println("query------->" + "内容：" + content);
                Log.i(TAG, "queryPushMessageAll: "+ "内容：" + content+"   标识码:"+ident+"  发送时间:"+time);
            }
            //关闭数据库
            db.close();
        }
    }

    public static void addPushMessage(SQLiteDatabase db, String content, String ident) {
        //生成ContentValues对象 //key:列名，value:想插入的值
        try {
            ContentValues cv = new ContentValues();
            //往ContentValues对象存放数据，键-值对模式
            cv.put(Constant.pushMessageDbTableColumn2, content);
            cv.put(Constant.pushMessageDbTableColumn3, ident);
            cv.put(Constant.pushMessageDbTableColumn4, TimeUtils.date2String(new Date()));
            //调用insert方法，将数据插入数据库
            db.insert(Constant.pushMessageDbTable, null, cv);
            //关闭数据库
            db.close();
        } catch (Exception e) {
            Log.i("addPushMessage", "addPushMessage: " + e.toString());
        }
    }

    public static void deletePushMessage(SQLiteDatabase db, String whereClauses, String[] whereArgs) {
        //生成ContentValues对象 //key:列名，value:想插入的值
        try {
            //调用delete方法，删除数据
            db.delete(Constant.pushMessageDbTable, whereClauses, whereArgs);
        } catch (Exception e) {
            Log.i("deletePushMessage", "deletePushMessage: " + e.toString());
        }
    }
}
