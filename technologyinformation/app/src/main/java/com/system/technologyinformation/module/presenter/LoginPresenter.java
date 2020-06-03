package com.system.technologyinformation.module.presenter;

import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.LogUtils;
import com.system.technologyinformation.common.mvp.BasePresenter;
import com.system.technologyinformation.common.net.ApiService;
import com.system.technologyinformation.common.rx.CommonSubscriber;
import com.system.technologyinformation.common.rx.RxUtil;
import com.system.technologyinformation.model.LoginBean;
import com.system.technologyinformation.model.http.base.BaseResponseInfo;
import com.system.technologyinformation.module.contract.LoginContract;
import com.system.technologyinformation.utils.MD5Util;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {
    @Inject
    ApiService apiService;

    @Inject
    public LoginPresenter() {
    }

    @Override
    public void getUserLoginResult(String phone, String invCode,String smsCode,String token) {
        Map<String, String> objectMap = new HashMap<>();
        objectMap.put("phone", phone);
        if(!EmptyUtils.isEmpty(invCode)){
            objectMap.put("invCode",invCode);
        }
        objectMap.put("smsCode",smsCode);
        if(!EmptyUtils.isEmpty(token)){
            objectMap.put("token",token);
        }
        //String gson = new Gson().toJson(objectMap);
        //RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), gson);
        addSubscribe(apiService.getUserLoginResult(objectMap)
                .compose(RxUtil.<BaseResponseInfo<LoginBean>>rxSchedulerHelper())
                .compose(RxUtil.<LoginBean>handleResult())
                .doOnNext(new Consumer<LoginBean>() {
                    @Override
                    public void accept(LoginBean productItemInfos) throws Exception {
                        LogUtils.d("loginInfo--->"+productItemInfos.getNickName());
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
    public void registerCheckPhone(String phone) {
        Map<String, String> objectMap = new HashMap<>();
        objectMap.put("phone", phone);
        addSubscribe(apiService.registerCheckPhone(objectMap)
                .compose(RxUtil.<BaseResponseInfo>rxSchedulerHelper())
                .doOnNext(new Consumer<BaseResponseInfo>() {
                    @Override
                    public void accept(BaseResponseInfo reuslt) throws Exception {
                        LogUtils.d(reuslt);
                    }
                })
                .subscribeWith(new CommonSubscriber<BaseResponseInfo>(getView()) {
                    @Override
                    public void onNext(BaseResponseInfo result) {
                        getView().registerCheckPhoneResult(result);
                    }
                }));
    }

    @Override
    public void registerUserInfoSms(String phone) {
        Map<String, String> objectMap = new HashMap<>();
        objectMap.put("phone", phone);
        //String gson = new Gson().toJson(objectMap);
        //RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), gson);
        addSubscribe(apiService.registerUserInfoSms(objectMap)
                .compose(RxUtil.<BaseResponseInfo>rxSchedulerHelper())
                .doOnNext(new Consumer<BaseResponseInfo>() {
                    @Override
                    public void accept(BaseResponseInfo reuslt) throws Exception {
                        LogUtils.d(reuslt);
                    }
                })
                .subscribeWith(new CommonSubscriber<BaseResponseInfo>(getView()) {
                    @Override
                    public void onNext(BaseResponseInfo result) {
                        getView().registerUserInfoSmsResult(result);
                    }
                }));
    }
}
