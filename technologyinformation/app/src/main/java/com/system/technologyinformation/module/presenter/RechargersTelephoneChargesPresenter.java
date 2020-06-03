package com.system.technologyinformation.module.presenter;

import android.content.Intent;

import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.LogUtils;
import com.system.technologyinformation.common.mvp.BasePresenter;
import com.system.technologyinformation.common.net.ApiService;
import com.system.technologyinformation.common.rx.CommonSubscriber;
import com.system.technologyinformation.common.rx.RxUtil;
import com.system.technologyinformation.model.GroupInfoBean;
import com.system.technologyinformation.model.LoginBean;
import com.system.technologyinformation.model.PayMethodBean;
import com.system.technologyinformation.model.RechargersTelephoneChargesBean;
import com.system.technologyinformation.model.RtcOrderBean;
import com.system.technologyinformation.model.UserRechargeAliBean;
import com.system.technologyinformation.model.UserRechargeWxBean;
import com.system.technologyinformation.model.http.base.BaseResponseInfo;
import com.system.technologyinformation.module.contract.RechargersTelephoneChargesContract;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

public class RechargersTelephoneChargesPresenter extends BasePresenter<RechargersTelephoneChargesContract.View> implements RechargersTelephoneChargesContract.Presenter {
    @Inject
    ApiService apiService;

    @Inject
    public RechargersTelephoneChargesPresenter() {
    }

    @Override
    public void getRechargersTelephoneChargesListResult() {
        addSubscribe(apiService.getRechargersTelephoneChargesListResult()
                .compose(RxUtil.<BaseResponseInfo<List<RechargersTelephoneChargesBean>>>rxSchedulerHelper())
                .compose(RxUtil.<List<RechargersTelephoneChargesBean>>handleResult())
                .doOnNext(new Consumer<List<RechargersTelephoneChargesBean>>() {
                    @Override
                    public void accept(List<RechargersTelephoneChargesBean> groupInfoBeanList) throws Exception {
                        LogUtils.d("productItemInfos" + groupInfoBeanList.toString());
                    }
                })
                .subscribeWith(new CommonSubscriber<List<RechargersTelephoneChargesBean>>(getView()) {
                    @Override
                    public void onNext(List<RechargersTelephoneChargesBean> groupInfoBeanList) {
                        getView().setRechargersTelephoneChargesListResult(groupInfoBeanList);
                    }
                }));
    }

    @Override
    public void getUserLoginResult(String phone, String invCode, String smsCode, String token) {
        Map<String, String> objectMap = new HashMap<>();
        objectMap.put("phone", phone);
        if (!EmptyUtils.isEmpty(invCode)) {
            objectMap.put("invCode", invCode);
        }
        if (!EmptyUtils.isEmpty(smsCode)) {
            objectMap.put("smsCode", smsCode);
        }
        if (!EmptyUtils.isEmpty(token)) {
            objectMap.put("token", token);
        }
        //String gson = new Gson().toJson(objectMap);
        //RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), gson);
        addSubscribe(apiService.getUserLoginResult(objectMap)
                .compose(RxUtil.<BaseResponseInfo<LoginBean>>rxSchedulerHelper())
                .compose(RxUtil.<LoginBean>handleResult())
                .doOnNext(new Consumer<LoginBean>() {
                    @Override
                    public void accept(LoginBean productItemInfos) throws Exception {
                        LogUtils.d("loginInfo--->" + productItemInfos.getNickName());
                    }
                })
                .subscribeWith(new CommonSubscriber<LoginBean>(getView()) {
                    @Override
                    public void onNext(LoginBean loginBean) {
                        getView().setUserLoginResult(loginBean);
                    }
                }));
    }

    @Override
    public void addRtcOrderResult(String phone, Integer price) {
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("phone", phone);
        objectMap.put("price", price);
        addSubscribe(apiService.addRtcOrderResult(objectMap)
                .compose(RxUtil.<BaseResponseInfo<RtcOrderBean>>rxSchedulerHelper())
                .compose(RxUtil.<RtcOrderBean>handleResult())
                .doOnNext(new Consumer<RtcOrderBean>() {
                    @Override
                    public void accept(RtcOrderBean productItemInfos) throws Exception {
                        LogUtils.d("productItemInfos" + productItemInfos);
                    }
                })
                .subscribeWith(new CommonSubscriber<RtcOrderBean>(getView()) {
                    @Override
                    public void onNext(RtcOrderBean rtcOrderBean) {
                        getView().getRtcOrderResult(rtcOrderBean);
                    }
                }));
    }

    @Override
    public void getRtcRechargeWx(int billId) {
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("billId", billId);
        addSubscribe(apiService.getRtcRechargeWx(objectMap)
                .compose(RxUtil.<BaseResponseInfo<UserRechargeWxBean>>rxSchedulerHelper())
                .compose(RxUtil.<UserRechargeWxBean>handleResult())
                .doOnNext(new Consumer<UserRechargeWxBean>() {
                    @Override
                    public void accept(UserRechargeWxBean productItemInfos) throws Exception {
                        LogUtils.d("productItemInfos" + productItemInfos);
                    }
                })
                .subscribeWith(new CommonSubscriber<UserRechargeWxBean>(getView()) {
                    @Override
                    public void onNext(UserRechargeWxBean questionBean) {
                        getView().setRtcRechargeWx(questionBean);
                    }
                }));
    }

    @Override
    public void getRtcrRechargeZfb(int billId) {
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("billId", billId);
        addSubscribe(apiService.getRtcrRechargeZfb(objectMap)
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
                        getView().setRtcrRechargeZfb(str);
                    }
                }));
    }

    @Override
    public void getMethodOfPayment() {
        addSubscribe(apiService.getMethodOfPayment()
                .compose(RxUtil.<BaseResponseInfo<PayMethodBean>>rxSchedulerHelper())
                .compose(RxUtil.<PayMethodBean>handleResult())
                .doOnNext(new Consumer<PayMethodBean>() {
                    @Override
                    public void accept(PayMethodBean productItemInfos) throws Exception {
                        LogUtils.d("productItemInfos" + productItemInfos.isAliPay());
                    }
                })
                .subscribeWith(new CommonSubscriber<PayMethodBean>(getView()) {
                    @Override
                    public void onNext(PayMethodBean questionBean) {
                        getView().setMethodOfPayment(questionBean);
                    }
                }));
    }
}
