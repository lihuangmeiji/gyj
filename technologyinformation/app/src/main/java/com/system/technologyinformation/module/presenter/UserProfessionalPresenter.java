package com.system.technologyinformation.module.presenter;

import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.LogUtils;
import com.system.technologyinformation.common.mvp.BasePresenter;
import com.system.technologyinformation.common.net.ApiService;
import com.system.technologyinformation.common.rx.CommonSubscriber;
import com.system.technologyinformation.common.rx.RxUtil;
import com.system.technologyinformation.model.IdentificationInfosBean;
import com.system.technologyinformation.model.LoginBean;
import com.system.technologyinformation.model.PagingInformationBean;
import com.system.technologyinformation.model.ProfessionalTypeBean;
import com.system.technologyinformation.model.UserIntegralBean;
import com.system.technologyinformation.model.http.base.BaseResponseInfo;
import com.system.technologyinformation.module.contract.UserPerfectInformationContract;
import com.system.technologyinformation.module.contract.UserProfessionalContract;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

public class UserProfessionalPresenter extends BasePresenter<UserProfessionalContract.View> implements UserProfessionalContract.Presenter {
    @Inject
    ApiService apiService;

    @Inject
    public UserProfessionalPresenter() {

    }


    @Override
    public void getProfessionalListResult() {
        addSubscribe(apiService.getProfessionalListResult()
                .compose(RxUtil.<BaseResponseInfo<List<IdentificationInfosBean>>>rxSchedulerHelper())
                .compose(RxUtil.<List<IdentificationInfosBean>>handleResult())
                .doOnNext(new Consumer<List<IdentificationInfosBean>>() {
                    @Override
                    public void accept(List<IdentificationInfosBean> productItemInfos) throws Exception {
                        LogUtils.d("ProfessionalTypeBean" + productItemInfos.size());
                    }
                })
                .subscribeWith(new CommonSubscriber<List<IdentificationInfosBean>>(getView()) {
                    @Override
                    public void onNext(List<IdentificationInfosBean> identificationInfosBeanList) {
                        getView().setProfessionalListResult(identificationInfosBeanList);
                    }
                }));
    }

    @Override
    public void addProfessionalResult(String ckIds) {
        Map<String, String> objectMap = new HashMap<>();
        objectMap.put("ckIds", ckIds );
        addSubscribe(apiService.getProfessionalAddResult(objectMap)
                .compose(RxUtil.<BaseResponseInfo<String>>rxSchedulerHelper())
                .compose(RxUtil.<String>handleResult())
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String str) throws Exception {
                        LogUtils.d("str" + str);
                    }
                })
                .subscribeWith(new CommonSubscriber<String>(getView()) {
                    @Override
                    public void onNext(String str) {
                        getView().getProfessionalResult(str);
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
      /*  addSubscribe(apiService.getUserLoginResult(objectMap)
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
                }));*/
    }
}
