package com.system.technologyinformation.utils;

import android.content.Context;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;

import com.blankj.utilcode.util.EmptyUtils;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.system.technologyinformation.common.net.Constant;
import com.system.technologyinformation.common.net.EnvConstant;
import com.system.technologyinformation.module.push.PushMessageDbOpenHelper;
import com.system.technologyinformation.module.push.PushMessageDbOperation;

public class PushMessagePlayUtil {
    private static final String TAG = "PushMessagePlayUtil";
    private static PushMessagePlayUtil pushMessagePlayUtil;
    private PowerManager.WakeLock mWakeLock;
    private PowerManager mPowerManager;

    public PushMessagePlayUtil() {

    }

    public static PushMessagePlayUtil getInstance() {
        if (pushMessagePlayUtil == null) {
            synchronized (PushMessagePlayUtil.class) {
                if (pushMessagePlayUtil == null) {
                    pushMessagePlayUtil = new PushMessagePlayUtil();
                }
            }
        }
        return pushMessagePlayUtil;
    }

    public void pushMessagePlay(String pushType, String content, String ident, Context context) {
        mPowerManager = (PowerManager) context.getSystemService(context.POWER_SERVICE);
        mWakeLock = mPowerManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "tag");
        mWakeLock.acquire();
        mWakeLock.release();
        PushMessageDbOpenHelper pushMessageDbOpenHelper = new PushMessageDbOpenHelper(context, Constant.pushMessageDbName, null, Constant.pushMessageDbVersion);
        try {
            Log.i(TAG, "unique_code: " + ident + "   推送来源:" + pushType);
            if (!EmptyUtils.isEmpty(ident)) {
                PushMessageDbOperation.addPushMessage(pushMessageDbOpenHelper.getReadableDatabase(), content, ident);
                synchronized (pushMessagePlayUtil) {
                    Log.i(TAG, "-----播放开始-----   推送来源:" + pushType+"消息标识:"+ident);
                    /*    AudioUtils.getInstance().init(context); //初始化语音对象
                        AudioUtils.getInstance().speakText(content); //播放语音*/
                    SpeechSynthesizer mySynthesizer = SpeechSynthesizer.createSynthesizer(context, new InitListener() {
                        @Override
                        public void onInit(int code) {
                        }
                    });
                    mySynthesizer.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");
                    mySynthesizer.setParameter(SpeechConstant.PITCH, "50");
                    mySynthesizer.setParameter(SpeechConstant.VOLUME, "50");
                    mySynthesizer.startSpeaking(content, new SynthesizerListener() {
                        @Override
                        public void onSpeakBegin() {

                        }

                        @Override
                        public void onBufferProgress(int i, int i1, int i2, String s) {

                        }

                        @Override
                        public void onSpeakPaused() {

                        }

                        @Override
                        public void onSpeakResumed() {

                        }

                        @Override
                        public void onSpeakProgress(int i, int i1, int i2) {

                        }

                        @Override
                        public void onCompleted(SpeechError speechError) {
                            Log.i(TAG, "-----AudioPlay播放完成-----");
                        }

                        @Override
                        public void onEvent(int i, int i1, int i2, Bundle bundle) {

                        }
                    });
                    Thread.sleep(4500);
                    Log.i(TAG, "-----AudioPlay播放完成-----");
                    pushMessagePlayUtil.notify();
                }
            }

        } catch (Exception e) {
            Log.i(TAG, "pushMessageBean: " + e.toString());
            pushMessagePlayUtil.notify();
        }
    }

}
