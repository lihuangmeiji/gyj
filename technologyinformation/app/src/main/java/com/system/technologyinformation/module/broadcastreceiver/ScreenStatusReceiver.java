package com.system.technologyinformation.module.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ScreenStatusReceiver extends BroadcastReceiver {
    boolean mScreenPowerStatus = true;//全局标识位，方便收到广播后针对性操作
    @Override
    public void onReceive(Context context, Intent intent) {
        if("android.intent.action.SCREEN_ON".equals(intent.getAction())) {
            Log.d("mScreenStatusReceiver", "Detect screen on and set mScreenPowerStatus false");
            mScreenPowerStatus = true;
        } else if("android.intent.action.SCREEN_OFF".equals(intent.getAction())) {
            Log.d("mScreenStatusReceiver", "Detect screen off and set mScreenPowerStatus ture");
            mScreenPowerStatus = false;
        }
    }
}
