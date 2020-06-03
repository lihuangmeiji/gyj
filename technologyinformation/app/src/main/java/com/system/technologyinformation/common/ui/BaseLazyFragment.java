package com.system.technologyinformation.common.ui;

import android.app.Application;
import android.app.ProgressDialog;
import android.util.Log;
import android.view.View;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.system.technologyinformation.AppApplication;
import com.system.technologyinformation.common.di.DaggerFragmentCommonComponent;
import com.system.technologyinformation.common.di.FragmentCommonComponent;
import com.system.technologyinformation.widget.dialog.DialogLoading;
import com.wjj.easy.easyandroid.mvp.EasyBasePresenter;
import com.wjj.easy.easyandroid.mvp.di.modules.FragmentModule;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by miya95 on 2017/3/15.
 */
public abstract class BaseLazyFragment<P extends EasyBasePresenter> extends BaseFragment<P> {

    @Inject
    protected P mPresenter;

    protected DialogLoading loading;
    private Unbinder unbinder;

    protected boolean isVisible = false;//页面是否可见
    protected boolean isPrepared = false;//是否布局已创建
    protected boolean isLoaded = false; //是否已经加载过

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInVisible();
        }
    }

    public void onInVisible() {

    }

    @Override
    protected void init(View view) {
        unbinder = ButterKnife.bind(this, view);
        loading = new DialogLoading(getActivity());
        initInject();
        if (mPresenter != null)
            mPresenter.attachView(this);
        initEventAndData();
    }

    @Override
    public void onResume() {
        super.onResume();
        isPrepared = true;
        onVisible();
    }

    protected void onVisible() {
        if (!isVisible || !isPrepared || isLoaded) {
            return;
        }
        loadLazyData();
    }

    /**
     * 懒加载数据 只有页面可见 已经onCreat 并且以前未加载过数据才会加载
     */
    protected abstract void loadLazyData();




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isPrepared = false;
        isLoaded = false;
        isVisible = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        mPresenter.detachView();
       /* RefWatcher refWatcher = MiyaApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);*/
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

}
