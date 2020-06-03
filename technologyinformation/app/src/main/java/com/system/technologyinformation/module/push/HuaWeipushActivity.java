package com.system.technologyinformation.module.push;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.system.technologyinformation.R;
import com.system.technologyinformation.utils.AudioUtils;
import com.umeng.message.UTrack;
import com.umeng.message.UmengNotifyClickActivity;

import org.android.agoo.common.AgooConstants;

public class HuaWeipushActivity extends UmengNotifyClickActivity {
    private static String TAG = HuaWeipushActivity.class.getName();

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_mipush);
    }

    @Override
    public void onMessage(Intent intent) {
        super.onMessage(intent);  //此方法必须调用，否则无法统计打开数
        final String body = intent.getStringExtra(AgooConstants.MESSAGE_BODY);
        Log.i(TAG, "onMessage: aaaaa");
        if (!TextUtils.isEmpty(body)) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();
                    AudioUtils.getInstance().init(getApplicationContext()); //初始化语音对象
                    AudioUtils.getInstance().speakText(body); //播放语音
                }
            });
        }
    }
}
