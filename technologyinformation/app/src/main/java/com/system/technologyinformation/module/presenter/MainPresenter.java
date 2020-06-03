package com.system.technologyinformation.module.presenter;

import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.LogUtils;
import com.system.technologyinformation.common.mvp.BasePresenter;
import com.system.technologyinformation.common.net.ApiService;
import com.system.technologyinformation.common.rx.CommonSubscriber;
import com.system.technologyinformation.common.rx.RxUtil;
import com.system.technologyinformation.model.AdvertiseInfoBean;
import com.system.technologyinformation.model.LoginBean;
import com.system.technologyinformation.model.VersionShowBean;
import com.system.technologyinformation.model.http.base.BaseResponseInfo;
import com.system.technologyinformation.module.contract.MainContract;
import com.system.technologyinformation.utils.MD5Util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter {
    @Inject
    ApiService apiService;

    @Inject
    public MainPresenter() {

    }

    @Override
    public void getVersionResult() {
        Map<String, String> objectMap = new HashMap<>();
        objectMap.put("osType", "android");
        addSubscribe(apiService.getVersionResult(objectMap)
                .compose(RxUtil.<BaseResponseInfo<VersionShowBean>>rxSchedulerHelper())
                .compose(RxUtil.<VersionShowBean>handleResult())
                .doOnNext(new Consumer<VersionShowBean>() {
                    @Override
                    public void accept(VersionShowBean productItemInfos) throws Exception {

                        LogUtils.d(productItemInfos.getId()+"");

                    }
                })
                .subscribeWith(new CommonSubscriber<VersionShowBean>(getView()) {
                    @Override
                    public void onNext(VersionShowBean o) {
                        getView().setVersionResult(o);
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
    public void addPushMessageToken(String xingeToken, String youmengToken, String jiguangToken) {
        Map<String, String> objectMap = new HashMap<>();
        if (!EmptyUtils.isEmpty(xingeToken)) {
            objectMap.put("xingeToken", xingeToken);
        }
        if (!EmptyUtils.isEmpty(youmengToken)) {
            objectMap.put("youmengToken", youmengToken);
        }
        if (!EmptyUtils.isEmpty(jiguangToken)) {
            objectMap.put("jiguangToken", jiguangToken);
        }
        addSubscribe(apiService.addPushMessageToken(objectMap)
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
                        getView().addPushMessageTokenResult(str);
                    }
                }));
    }

    @Override
    public void getAdvertiseInfo(){

        addSubscribe(apiService.getAdvertiseInfo()
                .compose(RxUtil.<BaseResponseInfo<List<AdvertiseInfoBean>>>rxSchedulerHelper())
                .compose(RxUtil.<List<AdvertiseInfoBean>>handleResult())
        .doOnNext(new Consumer<List<AdvertiseInfoBean>>() {
                      @Override
                      public void accept(List<AdvertiseInfoBean> advertiseInfoBeans) throws Exception {

                          LogUtils.d("advertiseInfo"+advertiseInfoBeans.toArray().toString());
                      }



                  }

        ).subscribeWith(new CommonSubscriber<List<AdvertiseInfoBean>>(getView())
                {
                    @Override
                    public void onNext(List<AdvertiseInfoBean> advertiseInfoBeans) {
                        getView().setAdvertiseWindow(advertiseInfoBeans);
                    }
                }))
        ;
    }
}

