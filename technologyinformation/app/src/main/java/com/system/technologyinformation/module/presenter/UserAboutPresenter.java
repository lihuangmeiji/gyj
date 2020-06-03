package com.system.technologyinformation.module.presenter;

import com.blankj.utilcode.util.LogUtils;
import com.system.technologyinformation.common.mvp.BasePresenter;
import com.system.technologyinformation.common.net.ApiService;
import com.system.technologyinformation.common.rx.CommonSubscriber;
import com.system.technologyinformation.common.rx.RxUtil;
import com.system.technologyinformation.model.http.base.BaseResponseInfo;
import com.system.technologyinformation.module.contract.UserAboutContract;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

public class UserAboutPresenter extends BasePresenter<UserAboutContract.View> implements UserAboutContract.Presenter {
    @Inject
    ApiService apiService;

    @Inject
    public UserAboutPresenter() {

    }

    @Override
    public void getUserLogoutResult() {
        addSubscribe(apiService.getUserLogoutResult()
                .compose(RxUtil.<BaseResponseInfo<String>>rxSchedulerHelper())
                .compose(RxUtil.<String>handleResult())
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String str) throws Exception {
                        LogUtils.d("productItemInfos" + str);
                    }
                })
                .subscribeWith(new CommonSubscriber<String>(getView()) {
                    @Override
                    public void onNext(String str) {
                        getView().setUserLogoutResult(str);
                    }
                }));
    }
}
