package com.system.technologyinformation.module.presenter;

import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.LogUtils;
import com.system.technologyinformation.common.mvp.BasePresenter;
import com.system.technologyinformation.common.net.ApiService;
import com.system.technologyinformation.common.rx.CommonSubscriber;
import com.system.technologyinformation.common.rx.RxUtil;
import com.system.technologyinformation.model.ConstructionPlaceBean;
import com.system.technologyinformation.model.LoginBean;
import com.system.technologyinformation.model.PagingInformationBean;
import com.system.technologyinformation.model.ProvinceBean;
import com.system.technologyinformation.model.UserIntegralBean;
import com.system.technologyinformation.model.http.base.BaseResponseInfo;
import com.system.technologyinformation.module.contract.UserAddressContract;
import com.system.technologyinformation.module.contract.UserConsumptionContract;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

public class UserAddressPresenter extends BasePresenter<UserAddressContract.View> implements UserAddressContract.Presenter {

    @Inject
    ApiService apiService;

    @Inject
    public UserAddressPresenter() {

    }


    @Override
    public void getUserAddressResult(int cpId) {
        Map<String, String> objectMap = new HashMap<>();
        objectMap.put("cpId", cpId + "");
        //String gson = new Gson().toJson(objectMap);
        //RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), gson);
        addSubscribe(apiService.getUserAddressResult(objectMap)
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
                        getView().setUserAddressResult(str);
                    }
                }));
    }

    @Override
    public void getProvinceOrCityInfoResult() {
        addSubscribe(apiService.getProvinceOrCityInfoResult()
                .compose(RxUtil.<BaseResponseInfo<PagingInformationBean<List<ProvinceBean>>>>rxSchedulerHelper())
                .compose(RxUtil.<PagingInformationBean<List<ProvinceBean>>>handleResult())
                .doOnNext(new Consumer<PagingInformationBean<List<ProvinceBean>>>() {
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

    @Override
    public void getConstructionPlaceInfoResult() {
        addSubscribe(apiService.getConstructionPlaceInfoResult()
                .compose(RxUtil.<BaseResponseInfo<List<ConstructionPlaceBean>>>rxSchedulerHelper())
                .compose(RxUtil.<List<ConstructionPlaceBean>>handleResult())
                .doOnNext(new Consumer<List<ConstructionPlaceBean>>() {
                    @Override
                    public void accept(List<ConstructionPlaceBean> constructionPlaceBeanList) throws Exception {
                        LogUtils.d("productItemInfos" + constructionPlaceBeanList.size());
                    }
                })
                .subscribeWith(new CommonSubscriber<List<ConstructionPlaceBean>>(getView()) {
                    @Override
                    public void onNext(List<ConstructionPlaceBean> constructionPlaceBeanList) {
                        getView().setConstructionPlaceInfoResult(constructionPlaceBeanList);
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
}
