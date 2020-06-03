package com.system.technologyinformation.common.ui;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;

import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.system.technologyinformation.AppApplication;
import com.system.technologyinformation.common.mvp.BaseView;
import com.system.technologyinformation.common.ui.interfaces.DiActivitySupport;
import com.system.technologyinformation.common.ui.interfaces.StatusBarSupport;
import com.system.technologyinformation.model.LoginBean;
import com.system.technologyinformation.module.ui.account.LoginActivity;
import com.wjj.easy.easyandroid.mvp.di.modules.ActivityModule;
import com.wjj.easy.easyandroid.ui.EasyActivity;

import com.system.technologyinformation.common.di.ActivityCommonComponent;
import com.system.technologyinformation.common.di.DaggerActivityCommonComponent;
import com.system.technologyinformation.utils.StatusBarUtils;
import com.system.technologyinformation.widget.dialog.DialogLoading;

import java.lang.reflect.Type;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Activity业务基类简单实现（不包含Presenter）
 *
 * @author wujiajun
 */

public abstract class SimpleActivity extends EasyActivity implements BaseView, DiActivitySupport, StatusBarSupport {

    protected DialogLoading loading;
    private Unbinder unbinder;
    LoginBean login;
    protected boolean showLog;
    AudioManager audio;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initInject();
        loadUserInfo();
        showLog=false;
        initEventAndData();
        audio = (AudioManager) getSystemService(Service.AUDIO_SERVICE);
    }

    protected void initView() {
        unbinder = ButterKnife.bind(this);
        loading = new DialogLoading(this);
        StatusBarUtils.adjustStatusBarHeight(this);
    }

    public void loadUserInfo(){
        SPUtils spUtils = new SPUtils("saveUser");
        String respone = spUtils.getString("userInfo");
        if (!EmptyUtils.isEmpty(respone)) {
            Type type = new TypeToken<LoginBean>() {
            }.getType();
            login = new Gson().fromJson(respone, type);
        } else {
            login = null;
        }
    }

    public void clearUserInfo(){
        SPUtils spUtils = new SPUtils("saveUser");
        spUtils.remove("userInfo");
    }

    public void openLogin() {
        clearUserInfo();
        Intent intent = new Intent(getBaseContext(), LoginActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    //di
    @Override
    public ActivityCommonComponent getActivityComponent() {
        return DaggerActivityCommonComponent.builder()
                .appCommonComponent(((AppApplication) getApplication()).getAppComponent())
                .activityModule(getActivityModule())
                .build();
    }

    @Override
    public ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }

    @Override
    public void initInject() {

    }

    //baseview
    @Override
    public void toast(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtils.showShortToast(msg);
            }
        });
    }

    @Override
    public void showLoading() {
        loading.show();
    }

    @Override
    public void hiddenLoading() {
        loading.hide();
    }

    @Override
    public void showErrorMsg(String msg,int code) {
        if (code == -10) {
            return;
        }
    }

    @Override
    public void stateError() {

    }

    //statusbar
    public void commonTheme() {
        StatusBarUtils.commonTheme(this);
    }

    //custom
    protected abstract void initEventAndData();


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1) {
            loadUserInfo();
        }
        if (requestCode == 1 && resultCode == 2) {
            finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                audio.adjustStreamVolume(
                        AudioManager.STREAM_MUSIC,
                        AudioManager.ADJUST_RAISE,
                        AudioManager.FLAG_PLAY_SOUND | AudioManager.FLAG_SHOW_UI);
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                audio.adjustStreamVolume(
                        AudioManager.STREAM_MUSIC,
                        AudioManager.ADJUST_LOWER,
                        AudioManager.FLAG_PLAY_SOUND | AudioManager.FLAG_SHOW_UI);
                return true;
            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }
}
