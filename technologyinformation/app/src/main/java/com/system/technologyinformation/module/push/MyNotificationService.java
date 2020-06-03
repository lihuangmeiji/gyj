package com.system.technologyinformation.module.push;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

import com.blankj.utilcode.util.EmptyUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.system.technologyinformation.R;
import com.system.technologyinformation.common.net.Constant;
import com.system.technologyinformation.common.net.EnvConstant;
import com.system.technologyinformation.model.PushMessageBean;
import com.system.technologyinformation.utils.AudioUtils;
import com.system.technologyinformation.utils.PushMessagePlayUtil;
import com.umeng.message.UTrack;
import com.umeng.message.entity.UMessage;


import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Random;

public class MyNotificationService extends Service {
    private static final String TAG = "UMessage";
    public static UMessage oldMessage = null;
    UMessage msg;
    private Handler handler;
    private PowerManager.WakeLock mWakeLock;
    private PowerManager mPowerManager;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) {
            return super.onStartCommand(intent, flags, startId);
        }
        String message = intent.getStringExtra("UmengMsg");
        Log.i(TAG, "pushMessage: " + message);
        try {
            msg = new UMessage(new JSONObject(message));
            PushMessageDbOpenHelper pushMessageDbOpenHelper = new PushMessageDbOpenHelper(getBaseContext(), Constant.pushMessageDbName, null, Constant.pushMessageDbVersion);
            boolean isDate = PushMessageDbOperation.queryPushMessage(pushMessageDbOpenHelper.getReadableDatabase(), msg.extra.get("unique_code"));
            if (!isDate) {
                handler = new Handler(getMainLooper());
                handler.post(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                            PushMessagePlayUtil.getInstance().pushMessagePlay("友盟", msg.text, msg.extra.get("unique_code"), getBaseContext());
                        } catch (Exception e) {
                            Log.i("xgMessage", "pushMessageBean: " + e.toString());
                        }
                    }
                });
                showNotification(msg);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void showNotification(UMessage msg) {
        int id = new Random(System.nanoTime()).nextInt();
        oldMessage = msg;
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancelAll();
        Notification.Builder mBuilder = new Notification.Builder(this);
        mBuilder.setContentTitle(msg.title)
                .setContentText(msg.text)
                .setTicker(msg.ticker)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_launcher)
                .setAutoCancel(true);
        Notification notification = mBuilder.getNotification();
        PendingIntent clickPendingIntent = getClickPendingIntent(this, msg);
        PendingIntent dismissPendingIntent = getDismissPendingIntent(this, msg);
        notification.deleteIntent = dismissPendingIntent;
        notification.contentIntent = clickPendingIntent;
        manager.notify(id, notification);
    }

    public PendingIntent getClickPendingIntent(Context context, UMessage msg) {
        Intent clickIntent = new Intent();
        clickIntent.setClass(context, NotificationBroadcast.class);
        clickIntent.putExtra(NotificationBroadcast.EXTRA_KEY_MSG,
                msg.getRaw().toString());
        clickIntent.putExtra(NotificationBroadcast.EXTRA_KEY_ACTION,
                NotificationBroadcast.ACTION_CLICK);
        PendingIntent clickPendingIntent = PendingIntent.getBroadcast(context,
                (int) (System.currentTimeMillis()),
                clickIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        return clickPendingIntent;
    }

    public PendingIntent getDismissPendingIntent(Context context, UMessage msg) {
        Intent deleteIntent = new Intent();
        deleteIntent.setClass(context, NotificationBroadcast.class);
        deleteIntent.putExtra(NotificationBroadcast.EXTRA_KEY_MSG,
                msg.getRaw().toString());
        deleteIntent.putExtra(
                NotificationBroadcast.EXTRA_KEY_ACTION,
                NotificationBroadcast.ACTION_DISMISS);
        PendingIntent deletePendingIntent = PendingIntent.getBroadcast(context,
                (int) (System.currentTimeMillis() + 1),
                deleteIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        return deletePendingIntent;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
