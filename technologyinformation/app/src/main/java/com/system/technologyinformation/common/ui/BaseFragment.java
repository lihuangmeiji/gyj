package com.system.technologyinformation.common.ui;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.system.technologyinformation.AppApplication;
import com.system.technologyinformation.common.mvp.BaseView;
import com.system.technologyinformation.common.ui.interfaces.DiFragmentSupport;
import com.system.technologyinformation.common.ui.interfaces.PresenterSupport;
import com.system.technologyinformation.model.LoginBean;
import com.system.technologyinformation.module.ui.account.LoginActivity;
import com.wjj.easy.easyandroid.mvp.EasyBasePresenter;
import com.wjj.easy.easyandroid.mvp.di.modules.FragmentModule;
import com.wjj.easy.easyandroid.ui.EasyFragment;
import com.system.technologyinformation.common.di.DaggerFragmentCommonComponent;
import com.system.technologyinformation.common.di.FragmentCommonComponent;
import com.system.technologyinformation.widget.dialog.DialogLoading;

import java.lang.reflect.Type;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Fragment业务基类
 *
 * @author wujiajun
 */

public abstract class BaseFragment<P extends EasyBasePresenter> extends EasyFragment implements BaseView, DiFragmentSupport, PresenterSupport<P> {

    @Inject
    protected P mPresenter;

    protected DialogLoading loading;
    private Unbinder unbinder;
    public   LoginBean login;
    protected boolean showLog;
    @Override
    protected void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
        loading = new DialogLoading(getActivity());
        loadUserInfo();
        showLog=false;
    }

    @Override
    protected void init(View view) {
        initInject();
        if (mPresenter != null)
            mPresenter.attachView(this);
        initEventAndData();
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
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        mPresenter.detachView();
    }

    //di
    @Override
    public FragmentCommonComponent getFragmentComponent() {
        return DaggerFragmentCommonComponent.builder()
                .appCommonComponent(((AppApplication) getActivity().getApplication()).getAppComponent())
                .fragmentModule(getFragmentModule())
                .build();
    }

    @Override
    public FragmentModule getFragmentModule() {
        return new FragmentModule(this);
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
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtils.showShortToast(msg);
            }
        });
    }

    @Override
    public void showLoading() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (loading == null) {
                    loading = new DialogLoading(getActivity());
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
        getActivity().runOnUiThread(new Runnable() {
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
    public void showErrorMsg(String msg,int code) {
        if (code == -10) {
            return;
        }
    }

    @Override
    public void stateError() {

    }

    //custom
    protected abstract void initEventAndData();

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1) {
            loadUserInfo();
        }
    }
}
