package com.system.technologyinformation.module.presenter;

import com.blankj.utilcode.util.LogUtils;
import com.system.technologyinformation.common.mvp.BasePresenter;
import com.system.technologyinformation.common.net.ApiService;
import com.system.technologyinformation.common.rx.CommonSubscriber;
import com.system.technologyinformation.common.rx.RxUtil;
import com.system.technologyinformation.model.EnrollmentBean;
import com.system.technologyinformation.model.LoginBean;
import com.system.technologyinformation.model.StoreInfoBean;
import com.system.technologyinformation.model.http.base.BaseResponseInfo;
import com.system.technologyinformation.module.contract.EnrollmentContract;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

public class EnrollmentPresenter extends BasePresenter<EnrollmentContract.View> implements EnrollmentContract.Presenter {

    @Inject
    ApiService apiService;

    @Inject
    public EnrollmentPresenter() {
    }

    @Override
    public void getEnrollmentInfoResult() {
        addSubscribe(apiService.getEnrollmentInfoResult()
                .compose(RxUtil.<BaseResponseInfo<EnrollmentBean>>rxSchedulerHelper())
                .compose(RxUtil.<EnrollmentBean>handleResult())
                .doOnNext(new Consumer<EnrollmentBean>() {
                    @Override
                    public void accept(EnrollmentBean productItemInfos) throws Exception {

                        LogUtils.d(productItemInfos.getName());

                    }
                })
                .subscribeWith(new CommonSubscriber<EnrollmentBean>(getView()) {
                    @Override
                    public void onNext(EnrollmentBean o) {
                        getView().setEnrollmentInfoResult(o);
                    }
                }));
    }

    @Override
    public void getUpdateUserInfoResult(int id) {
        addSubscribe(apiService.getUpdateUserInfoResult()
                .compose(RxUtil.<BaseResponseInfo<LoginBean>>rxSchedulerHelper())
                .compose(RxUtil.<LoginBean>handleResult())
                .doOnNext(new Consumer<LoginBean>() {
                    @Override
                    public void accept(LoginBean loginBean) throws Exception {
                        LogUtils.d("productItemInfos" + loginBean.getNickName());
                    }
                })
                .subscribeWith(new CommonSubscriber<LoginBean>(getView()) {
                    @Override
                    public void onNext(LoginBean loginBean) {
                        getView().setUpdateUserInfoResult(loginBean);
                    }
                }));
    }

    @Override
    public void getWhetherTheRegistrationIsSuccessful(int raceId, String project) {
        addSubscribe(apiService.getWhetherTheRegistrationIsSuccessful(raceId, project)
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
                        getView().setWhetherTheRegistrationIsSuccessful(str);
                    }
                }));
    }

    @Override
    public void getWhetherTheRegistration(int raceId) {
        addSubscribe(apiService.getWhetherTheRegistration(raceId)
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
                        getView().setWhetherTheRegistration(str);
                    }
                }));
    }
}
