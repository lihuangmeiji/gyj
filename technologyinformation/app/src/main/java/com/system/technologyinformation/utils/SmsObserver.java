package com.system.technologyinformation.utils;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;

import com.system.technologyinformation.module.ui.account.LoginActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SmsObserver extends ContentObserver {
    private Handler mHandler;
    private Context mContext;
    public SmsObserver(Context context,Handler handler) {
        super(handler);
        mContext = context;
        mHandler = handler;
    }

    @Override
    public void onChange(boolean selfChange, Uri uri) {
        super.onChange(selfChange, uri);
        String code;
        if (uri.toString().equals("content://sms/raw"))  ////onChange会执行二次,第二次短信才会入库
        {
            return ;
        }

        Uri inboxUri = Uri.parse("content://sms/inbox");
        Cursor c = mContext.getContentResolver().query(inboxUri, null, null, null, "date desc");
        if (c != null) {
            if (c.moveToFirst()) {
                String body = c.getString(c.getColumnIndex("body"));//获取短信内容
                Pattern pattern = Pattern.compile("(\\d{4})");//正则表达式   连续4位数字
                Matcher matcher = pattern.matcher(body);
                if (matcher.find()) {
                    code = matcher.group(0);
                    mHandler.obtainMessage(LoginActivity.MSG_RECEIVED_CODE, code).sendToTarget();
                }
            }
            c.close();
        }
    }

}
