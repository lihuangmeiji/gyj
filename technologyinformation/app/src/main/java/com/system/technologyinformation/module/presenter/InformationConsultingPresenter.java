package com.system.technologyinformation.module.presenter;

import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.LogUtils;
import com.system.technologyinformation.common.mvp.BasePresenter;
import com.system.technologyinformation.common.net.ApiService;
import com.system.technologyinformation.common.rx.CommonSubscriber;
import com.system.technologyinformation.common.rx.RxUtil;
import com.system.technologyinformation.model.HomeDataBean;
import com.system.technologyinformation.model.LoginBean;
import com.system.technologyinformation.model.PagingInformationBean;
import com.system.technologyinformation.model.http.base.BaseResponseInfo;
import com.system.technologyinformation.module.contract.InformationConsultingContract;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

public class InformationConsultingPresenter extends BasePresenter<InformationConsultingContract.View> implements InformationConsultingContract.Presenter{
    @Inject
    ApiService apiService;

    @Inject
    public InformationConsultingPresenter() {
    }

    @Override
    public void getHomeDataResult(int pageNum, int pageSize) {
        addSubscribe(apiService.getHomeDataResult(pageNum, pageSize)
                .compose(RxUtil.<BaseResponseInfo<PagingInformationBean<List<HomeDataBean>>>>rxSchedulerHelper())
                .compose(RxUtil.<PagingInformationBean<List<HomeDataBean>>>handleResult())
                .doOnNext(new Consumer<PagingInformationBean<List<HomeDataBean>>>() {
                    @Override
                    public void accept(PagingInformationBean<List<HomeDataBean>> productItemInfos) throws Exception {
                        LogUtils.d("BannerData" + productItemInfos.getList().size());
                    }
                })
                .subscribeWith(new CommonSubscriber<PagingInformationBean<List<HomeDataBean>>>(getView()) {
                    @Override
                    public void onNext(PagingInformationBean<List<HomeDataBean>> pagingInformationBean) {
                        getView().setHomeDataResult(pagingInformationBean.getList());
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
