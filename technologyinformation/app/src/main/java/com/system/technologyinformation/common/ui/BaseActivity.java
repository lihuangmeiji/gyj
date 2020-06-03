package com.system.technologyinformation.common.ui;

import android.app.ProgressDialog;
import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.system.technologyinformation.AppApplication;
import com.system.technologyinformation.common.mvp.BaseView;
import com.system.technologyinformation.common.ui.interfaces.DiActivitySupport;
import com.system.technologyinformation.common.ui.interfaces.PresenterSupport;
import com.system.technologyinformation.common.ui.interfaces.StatusBarSupport;
import com.system.technologyinformation.model.LoginBean;
import com.system.technologyinformation.module.ui.account.LoginActivity;
import com.wjj.easy.easyandroid.mvp.EasyBasePresenter;
import com.wjj.easy.easyandroid.mvp.di.modules.ActivityModule;
import com.wjj.easy.easyandroid.ui.EasyActivity;
import com.system.technologyinformation.common.di.ActivityCommonComponent;
import com.system.technologyinformation.common.di.DaggerActivityCommonComponent;
import com.system.technologyinformation.utils.StatusBarUtils;
import com.system.technologyinformation.widget.dialog.DialogLoading;

import java.lang.reflect.Type;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Activity业务基类
 * Created by wujiajun on 17/4/10.
 */

public abstract class BaseActivity<P extends EasyBasePresenter> extends EasyActivity implements BaseView, DiActivitySupport, PresenterSupport<P>, StatusBarSupport {

    protected DialogLoading loading;
    private Unbinder unbinder;
    private boolean isAlive = false;
    @Inject
    protected P mPresenter;

    protected LoginBean login;

    protected boolean showLog;
    protected boolean isClose;
    AudioManager audio;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isAlive = true;
        initView();
        initInject();
        loadUserInfo();
        showLog = false;
        isClose = false;
        if (mPresenter != null)
            mPresenter.attachView(this);
        initEventAndData();
        audio = (AudioManager) getSystemService(Service.AUDIO_SERVICE);
    }

    protected void initView() {
        unbinder = ButterKnife.bind(this);
        loading = new DialogLoading(this);
        StatusBarUtils.adjustStatusBarHeight(this);
    }

    public void loadUserInfo() {
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

    public void clearUserInfo() {
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
        isAlive = false;
        unbinder.unbind();
        mPresenter.detachView();
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
    public abstract void initInject();

    //presenter
    @Override
    public P getPresenter() {
        return mPresenter;
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
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (loading == null) {
                    loading = new DialogLoading(getApplicationContext());
                }
                if (loading.isShowing()) {
                    loading.dismiss();
                }
                loading.setCanceledOnTouchOutside(false);
                loading.show();
            }
        });
    }

    @Override
    public void hiddenLoading() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (loading == null || loading.isShowing() == false) {
                    Log.w("dismissProgressDialog", "dismissProgressDialog  progressDialog == null" +
                            " || progressDialog.isShowing() == false >> return;");
                    return;
                }
                loading.dismiss();
            }
        });
    }

    @Override
    public void showErrorMsg(String msg, int code) {
        if (code == -10) {
            return;
        }
    }

    @Override
    public void stateError() {

    }

    //statusbar
    @Override
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
            Intent intent = new Intent("userupdate");
            sendBroadcast(intent);
        }

        if (requestCode == 1 && resultCode == 2) {
            if (isClose) {
                finish();
            }
        }
    }


    /**
     * 隐藏键盘
     */
    protected void hideInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        View v = getWindow().peekDecorView();
        if (null != v) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    /**
     * 判断是否是手机号
     *
     * @param mobile
     * @return
     */
    public boolean isMobile(String mobile) {
        String regex = "^1\\d{10}$";
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(mobile);
        return m.matches();
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
