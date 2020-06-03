package com.system.technologyinformation.common.rx;

import android.text.TextUtils;

import com.blankj.utilcode.util.LogUtils;
import com.google.gson.JsonSyntaxException;
import com.system.technologyinformation.common.mvp.BaseView;
import com.system.technologyinformation.model.http.exception.ApiException;

import org.json.JSONException;

import java.net.SocketException;
import java.net.SocketTimeoutException;

import io.reactivex.subscribers.ResourceSubscriber;
import retrofit2.HttpException;

/**
 * 通用订阅者
 *
 * @param <T>
 */
public abstract class CommonSubscriber<T> extends ResourceSubscriber<T> {
    private BaseView mView;
    private String mErrorMsg;
    private boolean isShowErrorState = true;

    protected CommonSubscriber(BaseView view) {
        this.mView = view;
    }

    protected CommonSubscriber(BaseView view, String errorMsg) {
        this.mView = view;
        this.mErrorMsg = errorMsg;
    }

    protected CommonSubscriber(BaseView view, boolean isShowErrorState) {
        this.mView = view;
        this.isShowErrorState = isShowErrorState;
    }

    protected CommonSubscriber(BaseView view, String errorMsg, boolean isShowErrorState) {
        this.mView = view;
        this.mErrorMsg = errorMsg;
        this.isShowErrorState = isShowErrorState;
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onError(Throwable e) {
        if (mView == null) {
            return;
        }

        if (mErrorMsg != null && !TextUtils.isEmpty(mErrorMsg)) {
            mView.showErrorMsg(mErrorMsg, 9999);
        } else if (e instanceof ApiException) {
            mView.showErrorMsg(e.getMessage(), ((ApiException) e).getCode());
        } else if (e instanceof IllegalArgumentException) {
            LogUtils.d(e.toString());
        } else if (e instanceof JSONException || e instanceof JsonSyntaxException) {
            mView.showErrorMsg("数据异常", 707);
        } else if (e instanceof SocketTimeoutException) {
            mView.showErrorMsg("网络连接超时", 606);
            LogUtils.d(e.toString());
        } else if (e instanceof HttpException) {
            mView.showErrorMsg("数据加载失败ヽ(≧Д≦)ノ", 505);
            LogUtils.d(e.toString());
        } else {
            //ヽ(≧Д≦)ノ
            mView.showErrorMsg("服务异常，请检查当前网络", 405);
            //mView.showErrorMsg(e.toString(), 405);
            LogUtils.d(e.toString());
        }
        if (isShowErrorState) {
            mView.stateError();
        }

        e.printStackTrace();
    }
}
