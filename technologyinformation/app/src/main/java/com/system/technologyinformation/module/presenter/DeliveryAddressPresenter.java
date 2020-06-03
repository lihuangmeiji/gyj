package com.system.technologyinformation.module.presenter;

import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.LogUtils;
import com.system.technologyinformation.common.mvp.BasePresenter;
import com.system.technologyinformation.common.net.ApiService;
import com.system.technologyinformation.common.rx.CommonSubscriber;
import com.system.technologyinformation.common.rx.RxUtil;
import com.system.technologyinformation.model.DeliveryAddressBean;
import com.system.technologyinformation.model.LoginBean;
import com.system.technologyinformation.model.PagingInformationBean;
import com.system.technologyinformation.model.ProvinceBean;
import com.system.technologyinformation.model.RechargersTelephoneChargesBean;
import com.system.technologyinformation.model.http.base.BaseResponseInfo;
import com.system.technologyinformation.module.contract.DeliveryAddressContract;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import javax.inject.Inject;

public class DeliveryAddressPresenter extends BasePresenter<DeliveryAddressContract.View> implements DeliveryAddressContract.Presenter {
    @Inject
    ApiService apiService;

    @Inject
    public DeliveryAddressPresenter() {
    }

    @Override
    public void addUserDeliveryAddress(String name, String phone, Integer areaCode, String address) {
        Map<String, Object> objectMap = new HashMap<>();
        if (!EmptyUtils.isEmpty(name)) {
            objectMap.put("name", name);
        }
        if (!EmptyUtils.isEmpty(phone)) {
            objectMap.put("phone", phone);
        }
        if (!EmptyUtils.isEmpty(areaCode)) {
            objectMap.put("areaCode", areaCode);
        }
        if (!EmptyUtils.isEmpty(address)) {
            objectMap.put("address", address);
        }
        addSubscribe(apiService.addUserDeliveryAddress(objectMap)
                .compose(RxUtil.<BaseResponseInfo<String>>rxSchedulerHelper())
                .compose(RxUtil.<String>handleResult())
                .doOnNext(new io.reactivex.functions.Consumer<String>() {
                    @Override
                    public void accept(String str) throws Exception {
                        LogUtils.d("productItemInfos" + str);
                    }
                })
                .subscribeWith(new CommonSubscriber<String>(getView()) {
                    @Override
                    public void onNext(String str) {
                        getView().addUserDeliveryAddressResult(str);
                    }
                }));
    }

    @Override
    public void updateUserDeliveryAddress(long id, String name, String phone, Integer areaCode, String address) {
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("id", id);
        if (!EmptyUtils.isEmpty(name)) {
            objectMap.put("name", name);
        }
        if (!EmptyUtils.isEmpty(phone)) {
            objectMap.put("phone", phone);
        }
        if (!EmptyUtils.isEmpty(areaCode)) {
            objectMap.put("areaCode", areaCode);
        }
        if (!EmptyUtils.isEmpty(address)) {
            objectMap.put("address", address);
        }
        addSubscribe(apiService.updateUserDeliveryAddress(objectMap)
                .compose(RxUtil.<BaseResponseInfo<String>>rxSchedulerHelper())
                .compose(RxUtil.<String>handleResult())
                .doOnNext(new io.reactivex.functions.Consumer<String>() {
                    @Override
                    public void accept(String str) throws Exception {
                        LogUtils.d("productItemInfos" + str);
                    }
                })
                .subscribeWith(new CommonSubscriber<String>(getView()) {
                    @Override
                    public void onNext(String str) {
                        getView().updateUserDeliveryAddressResult(str);
                    }
                }));
    }

    @Override
    public void getUserDeliveryAddressResult() {
        addSubscribe(apiService.getUserDeliveryAddressResult()
                .compose(RxUtil.<BaseResponseInfo<List<DeliveryAddressBean>>>rxSchedulerHelper())
                .compose(RxUtil.<List<DeliveryAddressBean>>handleResult())
                .doOnNext(new io.reactivex.functions.Consumer<List<DeliveryAddressBean>>() {
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
                .doOnNext(new io.reactivex.functions.Consumer<LoginBean>() {
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
    public void getProvinceOrCityInfoResult() {
        addSubscribe(apiService.getProvinceOrCityInfoResult()
                .compose(RxUtil.<BaseResponseInfo<PagingInformationBean<List<ProvinceBean>>>>rxSchedulerHelper())
                .compose(RxUtil.<PagingInformationBean<List<ProvinceBean>>>handleResult())
                .doOnNext(new io.reactivex.functions.Consumer<PagingInformationBean<List<ProvinceBean>>>() {
                    @Override
                    public void accept(PagingInformationBean<List<ProvinceBean>> productItemInfos) throws Exception {
                        LogUtils.d("productItemInfos" + productItemInfos.getList().size());
                    }
                })
                .subscribeWith(new CommonSubscriber<PagingInformationBean<List<ProvinceBean>>>(getView()) {
                    @Override
                    public void onNext(PagingInformationBean<List<ProvinceBean>> pagingInformationBean) {
                        getView().setProvinceOrCityInfoResult(pagingInformationBean.getList());
                    }
                }));
    }
}
