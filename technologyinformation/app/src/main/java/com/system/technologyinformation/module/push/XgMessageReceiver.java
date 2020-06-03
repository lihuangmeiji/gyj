package com.system.technologyinformation.module.push;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;


import com.blankj.utilcode.util.EmptyUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.system.technologyinformation.common.net.Constant;
import com.system.technologyinformation.common.net.EnvConstant;
import com.system.technologyinformation.model.PushMessageBean;
import com.system.technologyinformation.module.ui.chat.QuestionDetailesActivity;
import com.system.technologyinformation.utils.AudioUtils;
import com.system.technologyinformation.utils.PushMessagePlayUtil;
import com.tencent.android.tpush.XGPushBaseReceiver;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.android.tpush.XGPushRegisterResult;
import com.tencent.android.tpush.XGPushShowedResult;
import com.tencent.android.tpush.XGPushTextMessage;
import com.umeng.message.UTrack;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class XgMessageReceiver extends XGPushBaseReceiver {
    private Intent intent = new Intent("com.qq.xgdemo.activity.UPDATE_LISTVIEW");
    public static final String LogTag = "XgMessageReceiver";
    private Handler handler;

    private void show(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    // 通知展示
    @Override
    public void onNotifactionShowedResult(final Context context,
                                          final XGPushShowedResult notifiShowedRlt) {
        if (context == null || notifiShowedRlt == null) {
            return;
        }
        Log.i(LogTag, "pushMessage: " + notifiShowedRlt.toString());
        try {
            Type type = new TypeToken<PushMessageBean>() {
            }.getType();
            final PushMessageBean pushMessageBean = new Gson().fromJson(notifiShowedRlt.getCustomContent(), type);
            PushMessageDbOpenHelper pushMessageDbOpenHelper = new PushMessageDbOpenHelper(context, Constant.pushMessageDbName, null, Constant.pushMessageDbVersion);
            boolean isDate = PushMessageDbOperation.queryPushMessage(pushMessageDbOpenHelper.getReadableDatabase(), pushMessageBean.getUnique_code());
            if (!isDate) {
                handler = new Handler(context.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        PushMessagePlayUtil.getInstance().pushMessagePlay("信鸽", notifiShowedRlt.getContent(), pushMessageBean.getUnique_code(), context);
                    }
                });
            } else {
                XGPushManager.cancelNotifaction(context, notifiShowedRlt.getNotifactionId());
            }
        } catch (Exception e) {
            Log.i("xgMessage", "pushMessageBean: " + e.toString());
        }
        //show(context, "您有1条新消息, " + "通知被展示 ， " + notifiShowedRlt.toString());
    }

    //反注册的回调
    @Override
    public void onUnregisterResult(Context context, int errorCode) {
        if (context == null) {
            return;
        }
        String text = "";
        if (errorCode == XGPushBaseReceiver.SUCCESS) {
            text = "反注册成功";
        } else {
            text = "反注册失败" + errorCode;
        }
        Log.d(LogTag, text);
        //show(context, text);

    }

    //设置tag的回调
    @Override
    public void onSetTagResult(Context context, int errorCode, String tagName) {
        if (context == null) {
            return;
        }
        String text = "";
        if (errorCode == XGPushBaseReceiver.SUCCESS) {
            text = "\"" + tagName + "\"设置成功";
        } else {
            text = "\"" + tagName + "\"设置失败,错误码：" + errorCode;
        }
        Log.d(LogTag, text);
        //show(context, text);

    }

    //删除tag的回调
    @Override
    public void onDeleteTagResult(Context context, int errorCode, String tagName) {
        if (context == null) {
            return;
        }
        String text = "";
        if (errorCode == XGPushBaseReceiver.SUCCESS) {
            text = "\"" + tagName + "\"删除成功";
        } else {
            text = "\"" + tagName + "\"删除失败,错误码：" + errorCode;
        }
        Log.d(LogTag, text);
        //show(context, text);

    }

    // 通知点击回调 actionType=1为该消息被清除，actionType=0为该消息被点击。此处不能做点击消息跳转，详细方法请参照官网的Android常见问题文档
    @Override
    public void onNotifactionClickedResult(Context context,
                                           XGPushClickedResult message) {
        Log.e("LC", "+++++++++++++++ 通知被点击 跳转到指定页面。");
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
        if (context == null || message == null) {
            return;
        }
        String text = "";
        if (message.getActionType() == XGPushClickedResult.NOTIFACTION_CLICKED_TYPE) {
            // 通知在通知栏被点击啦。。。。。
            // APP自己处理点击的相关动作
            // 这个动作可以在activity的onResume也能监听，请看第3点相关内容
            text = "通知被打开 :" + message;
            String customContent = message.getCustomContent();
            if (customContent != null && customContent.length() != 0) {
                try {
                    JSONObject obj = new JSONObject(customContent);
                    // key1为前台配置的key
                    if (!obj.isNull("target")) {
                        String value = obj.getString("target");
                        Log.i(LogTag, "target: " + value);
                        if (value.contains("main")) {
                            PackageManager manager = context.getPackageManager();
                            String scheme;
                            if (value.contains("?id=")) {
                                scheme = value + "&lytype=bd";
                            } else {
                                scheme = value + "?lytype=bd";
                            }
                            Intent intent = new Intent(Intent.ACTION_VIEW,
                                    Uri.parse(scheme));
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            List list = manager.queryIntentActivities(intent, PackageManager.GET_RESOLVED_FILTER);
                            if (list != null && list.size() > 0) {
                                context.startActivity(intent);
                            }
                        } else {
                            Intent intentWeb = new Intent(context, QuestionDetailesActivity.class);
                            intentWeb.putExtra("url", value);
                            intentWeb.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intentWeb);
                        }
                        Log.d(LogTag, "get custom value:" + value);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else if (message.getActionType() == XGPushClickedResult.NOTIFACTION_DELETED_TYPE) {
            // 通知被清除啦。。。。
            // APP自己处理通知被清除后的相关动作
            text = "通知被清除 :" + message;
        }
    /*    Toast.makeText(context, "广播接收到通知被点击:" + message.toString(),
                Toast.LENGTH_SHORT).show();*/
        // 获取自定义key-value
        // APP自主处理的过程。。。
        Log.d(LogTag, text);
        //show(context, text);
    }

    //注册的回调
    @Override
    public void onRegisterResult(Context context, int errorCode,
                                 XGPushRegisterResult message) {
        // TODO Auto-generated method stub
        if (context == null || message == null) {
            return;
        }
        String text = "";
        if (errorCode == XGPushBaseReceiver.SUCCESS) {
            text = message + "注册成功";
            // 在这里拿token
            String token = message.getToken();
        } else {
            text = message + "注册失败错误码：" + errorCode;
        }
        Log.d(LogTag, text);
        // show(context, text);
    }

    // 消息透传的回调
    @Override
    public void onTextMessage(Context context, XGPushTextMessage message) {
        // TODO Auto-generated method stub
        String text = "收到消息:" + message.toString();
        // 获取自定义key-value
        String customContent = message.getCustomContent();
        if (customContent != null && customContent.length() != 0) {
            try {
                JSONObject obj = new JSONObject(customContent);
                // key1为前台配置的key
                if (!obj.isNull("key")) {
                    String value = obj.getString("key");
                    Log.d(LogTag, "get custom value:" + value);
                }
                // ...
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.d("LC", "++++++++++++++++透传消息");
        // APP自主处理消息的过程...
        Log.d(LogTag, text);
        //show(context, text);
    }

}
