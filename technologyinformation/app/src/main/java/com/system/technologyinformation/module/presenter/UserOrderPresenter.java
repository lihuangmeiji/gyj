package com.system.technologyinformation.module.presenter;

import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.LogUtils;
import com.system.technologyinformation.common.mvp.BasePresenter;
import com.system.technologyinformation.common.net.ApiService;
import com.system.technologyinformation.common.rx.CommonSubscriber;
import com.system.technologyinformation.common.rx.RxUtil;
import com.system.technologyinformation.model.DeliveryBean;
import com.system.technologyinformation.model.LoginBean;
import com.system.technologyinformation.model.PagingInformationBean;
import com.system.technologyinformation.model.UserIntegralBean;
import com.system.technologyinformation.model.UserOrderBean;
import com.system.technologyinformation.model.UserRechargeWxBean;
import com.system.technologyinformation.model.http.base.BaseResponseInfo;
import com.system.technologyinformation.module.contract.UserOrderContract;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

public class UserOrderPresenter extends BasePresenter<UserOrderContract.View> implements UserOrderContract.Presenter {
    @Inject
    ApiService apiService;

    @Inject
    public UserOrderPresenter() {
    }


    @Override
    public void getUserOrderListResult(int pageNum, int pageSize, Integer status) {
        addSubscribe(apiService.getUserOrderListResult(pageNum, pageSize, status)
                .compose(RxUtil.<BaseResponseInfo<PagingInformationBean<List<UserOrderBean>>>>rxSchedulerHelper())
                .compose(RxUtil.<PagingInformationBean<List<UserOrderBean>>>handleResult())
                .doOnNext(new Consumer<PagingInformationBean<List<UserOrderBean>>>() {
                    @Override
                    public void accept(PagingInformationBean<List<UserOrderBean>> productItemInfos) throws Exception {
                        LogUtils.d("productItemInfos" + productItemInfos.getList().size());
                    }
                })
                .subscribeWith(new CommonSubscriber<PagingInformationBean<List<UserOrderBean>>>(getView()) {
                    @Override
                    public void onNext(PagingInformationBean<List<UserOrderBean>> pagingInformationBean) {
                        getView().setUserOrderListResult(pagingInformationBean.getList());
                    }
                }));
    }

    @Override
    public void getOnlineSupermarketGoodsOreder(String jsonStr) {
     /*   Map<String, String> objectMap = new HashMap<>();
        objectMap.put("jsonStr", jsonStr);
        addSubscribe(apiService.setOnlineSupermarketGoodsOreder(objectMap)
                .compose(RxUtil.<BaseResponseInfo<List<DeliveryBean>>>rxSchedulerHelper())
                .compose(RxUtil.<List<DeliveryBean>>handleResult())
                .doOnNext(new Consumer<List<DeliveryBean>>() {
                    @Override
                    public void accept(List<DeliveryBean> productItemInfos) throws Exception {

                    }
                })
                .subscribeWith(new CommonSubscriber<List<DeliveryBean>>(getView()) {
                    @Override

                    public void onNext(List<DeliveryBean> o) {
                        getView().setOnlineSupermarketGoodsOreder(o);
                    }
                }));*/
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
    public void addUserOrderRefunds(String reasons, long orderId) {
        addSubscribe(apiService.addUserOrderRefunds(orderId, reasons)
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
                        getView().addUserOrderRefundsResult(str);
                    }
                }));
    }
}
