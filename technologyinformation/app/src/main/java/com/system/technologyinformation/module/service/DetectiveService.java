package com.system.technologyinformation.module.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

public class DetectiveService extends Service {
    public DetectiveService() {
    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
        /* 注册屏幕唤醒时的广播 */
        IntentFilter mScreenOnFilter = new IntentFilter("android.intent.action.SCREEN_ON");
        this.registerReceiver(mScreenOReceiver, mScreenOnFilter);

        /* 注册机器锁屏时的广播 */
        IntentFilter mScreenOffFilter = new IntentFilter("android.intent.action.SCREEN_OFF");
        this.registerReceiver(mScreenOReceiver, mScreenOffFilter);
    }

    public void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(mScreenOReceiver);
    }

    private BroadcastReceiver mScreenOReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (action.equals("android.intent.action.SCREEN_ON")) {
                System.out.println("—— SCREEN_ON ——");
            } else if (action.equals("android.intent.action.SCREEN_OFF")) {
                System.out.println("—— SCREEN_OFF ——");
            }
        }

    };
}
