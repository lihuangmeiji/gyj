package com.system.technologyinformation.module.presenter;

import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.system.technologyinformation.common.mvp.BasePresenter;
import com.system.technologyinformation.common.net.ApiService;
import com.system.technologyinformation.common.rx.CommonSubscriber;
import com.system.technologyinformation.common.rx.RxUtil;
import com.system.technologyinformation.model.ConfirmOrderBean;
import com.system.technologyinformation.model.LoginBean;
import com.system.technologyinformation.model.PayMethodBean;
import com.system.technologyinformation.model.UserRechargeWxBean;
import com.system.technologyinformation.model.http.base.BaseResponseInfo;
import com.system.technologyinformation.module.contract.UserConfirmAnOrderContract;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

public class UserConfirmAnOrderPresenter extends BasePresenter<UserConfirmAnOrderContract.View> implements UserConfirmAnOrderContract.Presenter {
    @Inject
    ApiService apiService;

    @Inject
    public UserConfirmAnOrderPresenter() {
    }

    @Override
    public void addDeliveryResult(String dateTime, int storeId, int deliveryId) {
        Map<String, String> objectMap = new HashMap<>();
        objectMap.put("dateTime", dateTime);
        objectMap.put("id", deliveryId + "");
        addSubscribe(apiService.addDeliveryResult(objectMap)
                .compose(RxUtil.<BaseResponseInfo>rxSchedulerHelper())
                .doOnNext(new Consumer<BaseResponseInfo>() {
                    @Override
                    public void accept(BaseResponseInfo baseResponseInfo) throws Exception {
                        LogUtils.d(baseResponseInfo.getMsg());
                    }
                })
                .subscribeWith(new CommonSubscriber<BaseResponseInfo>(getView()) {
                    @Override
                    public void onNext(BaseResponseInfo result) {
                        getView().getDeliveryResult(result);
                    }
                }));
    }

    @Override
    public void getUserConfirmAnOrderPayWx(int deliveryId, int orderId, String dateTime) {
        Map<String, String> objectMap = new HashMap<>();
        if (!EmptyUtils.isEmpty(dateTime)) {
            objectMap.put("dateTime", dateTime);
        }
        objectMap.put("deliveryId", deliveryId + "");
        objectMap.put("orderId", orderId + "");
        addSubscribe(apiService.getUserConfirmAnOrderPayWx(objectMap)
                .compose(RxUtil.<BaseResponseInfo<UserRechargeWxBean>>rxSchedulerHelper())
                .compose(RxUtil.<UserRechargeWxBean>handleResult())
                .doOnNext(new Consumer<UserRechargeWxBean>() {
                    @Override
                    public void accept(UserRechargeWxBean productItemInfos) throws Exception {

                        LogUtils.d("");

                    }
                })
                .subscribeWith(new CommonSubscriber<UserRechargeWxBean>(getView()) {
                    @Override
                    public void onNext(UserRechargeWxBean o) {
                        getView().setUserConfirmAnOrderPayWx(o);
                    }
                }));
    }

    @Override
    public void getUserConfirmAnOrderPayZfb(int deliveryId, int orderId, String dateTime) {
        Map<String, String> objectMap = new HashMap<>();
        if (!EmptyUtils.isEmpty(dateTime)) {
            objectMap.put("dateTime", dateTime);
        }
        objectMap.put("deliveryId", deliveryId + "");
        objectMap.put("orderId", orderId + "");
        addSubscribe(apiService.getUserConfirmAnOrderPayZfb(objectMap)
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
                        getView().setUserConfirmAnOrderPayZfb(str);
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
    public void getOnlineSupermarketGoodsOreder(String jsonStr, String dateTime, final int payType) {
        Map<String, String> objectMap = new HashMap<>();
        objectMap.put("jsonStr", jsonStr);
        objectMap.put("dateTime", dateTime);
        addSubscribe(apiService.setOnlineSupermarketGoodsOreder(objectMap)
                .compose(RxUtil.<BaseResponseInfo<ConfirmOrderBean>>rxSchedulerHelper())
                .compose(RxUtil.<ConfirmOrderBean>handleResult())
                .doOnNext(new Consumer<ConfirmOrderBean>() {
                    @Override
                    public void accept(ConfirmOrderBean confirmOrderBean) throws Exception {

                    }
                })
                .subscribeWith(new CommonSubscriber<ConfirmOrderBean>(getView()) {
                    @Override

                    public void onNext(ConfirmOrderBean o) {
                        getView().setOnlineSupermarketGoodsOreder(o, payType);
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
