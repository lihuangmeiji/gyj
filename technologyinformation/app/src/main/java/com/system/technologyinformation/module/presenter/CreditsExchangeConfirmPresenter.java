package com.system.technologyinformation.module.presenter;

import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.LogUtils;
import com.system.technologyinformation.common.mvp.BasePresenter;
import com.system.technologyinformation.common.net.ApiService;
import com.system.technologyinformation.common.rx.CommonSubscriber;
import com.system.technologyinformation.common.rx.RxUtil;
import com.system.technologyinformation.model.CreditsExchangePayBean;
import com.system.technologyinformation.model.DeliveryAddressBean;
import com.system.technologyinformation.model.HomeShoppingSpreeBean;
import com.system.technologyinformation.model.LoginBean;
import com.system.technologyinformation.model.PayMethodBean;
import com.system.technologyinformation.model.http.base.BaseResponseInfo;
import com.system.technologyinformation.module.contract.CreditsExchangeConfirmContract;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

public class CreditsExchangeConfirmPresenter extends BasePresenter<CreditsExchangeConfirmContract.View> implements CreditsExchangeConfirmContract.Presenter {

    @Inject
    ApiService apiService;

    @Inject
    public CreditsExchangeConfirmPresenter() {
    }

    @Override
    public void getShoppingDetailedResult(int id) {
        addSubscribe(apiService.getShoppingDetailedResult(id)
                .compose(RxUtil.<BaseResponseInfo<HomeShoppingSpreeBean>>rxSchedulerHelper())
                .compose(RxUtil.<HomeShoppingSpreeBean>handleResult())
                .doOnNext(new Consumer<HomeShoppingSpreeBean>() {
                    @Override
                    public void accept(HomeShoppingSpreeBean productItemInfos) throws Exception {
                        LogUtils.d("HomeDetailedBean" + productItemInfos.getName());
                    }
                })
                .subscribeWith(new CommonSubscriber<HomeShoppingSpreeBean>(getView()) {
                    @Override
                    public void onNext(HomeShoppingSpreeBean homeShoppingSpreeBean) {
                        getView().setShoppingDetailedResult(homeShoppingSpreeBean);
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
    public void addUserMarketCreateOrderResult(String phone, String jsonStr, String name, String address, String orderNo, final String payType) {
        Map<String, String> objectMap = new HashMap<>();
        if (!EmptyUtils.isEmpty(phone)) {
            objectMap.put("phone", phone);
        }
        if (!EmptyUtils.isEmpty(name)) {
            objectMap.put("name", name);
        }
        if (!EmptyUtils.isEmpty(address)) {
            objectMap.put("address", address);
        }
        if (!EmptyUtils.isEmpty(jsonStr)) {
            objectMap.put("jsonStr", jsonStr);
        }
        if (!EmptyUtils.isEmpty(orderNo)) {
            objectMap.put("orderNo", orderNo);
        }
        if (!EmptyUtils.isEmpty(payType)) {
            objectMap.put("payType", payType);
        }
        addSubscribe(apiService.addUserMarketCreateOrderResult(objectMap)
                .compose(RxUtil.<BaseResponseInfo<CreditsExchangePayBean>>rxSchedulerHelper())
                .compose(RxUtil.<CreditsExchangePayBean>handleResult())
                .doOnNext(new Consumer<CreditsExchangePayBean>() {
                    @Override
                    public void accept(CreditsExchangePayBean productItemInfos) throws Exception {
                        LogUtils.d("productItemInfos" + productItemInfos.getQrCode());
                    }
                })
                .subscribeWith(new CommonSubscriber<CreditsExchangePayBean>(getView()) {
                    @Override
                    public void onNext(CreditsExchangePayBean creditsExchangePayBean) {
                        getView().getUserMarketCreateOrderResult(creditsExchangePayBean, payType);
                    }
                }));
    }

    @Override
    public void getUserDeliveryAddressResult() {
        addSubscribe(apiService.getUserDeliveryAddressResult()
                .compose(RxUtil.<BaseResponseInfo<List<DeliveryAddressBean>>>rxSchedulerHelper())
                .compose(RxUtil.<List<DeliveryAddressBean>>handleResult())
                .doOnNext(new Consumer<List<DeliveryAddressBean>>() {
                    @Override
                    public void accept(List<DeliveryAddressBean> groupInfoBeanList) throws Exception {
                        LogUtils.d("productItemInfos" + groupInfoBeanList.toString());
                    }
                })
                .subscribeWith(new CommonSubscriber<List<DeliveryAddressBean>>(getView()) {
                    @Override
                    public void onNext(List<DeliveryAddressBean> groupInfoBeanList) {
                        getView().setUserDeliveryAddressResult(groupInfoBeanList);
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

    @Override
    public void getUserMarketOrderCancelResult(String orderNo) {
        Map<String, Object> objectMap = new HashMap<>();
        if (!EmptyUtils.isEmpty(orderNo)) {
            objectMap.put("orderNo", orderNo);
        }
        addSubscribe(apiService.getUserMarketOrderCancelResult(objectMap)
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
                        getView().setUserMarketOrderCancelResult(str);
                    }
                }));
    }
}
