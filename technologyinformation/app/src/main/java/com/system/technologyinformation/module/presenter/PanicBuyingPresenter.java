package com.system.technologyinformation.module.presenter;

import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.LogUtils;
import com.system.technologyinformation.common.mvp.BasePresenter;
import com.system.technologyinformation.common.net.ApiService;
import com.system.technologyinformation.common.rx.CommonSubscriber;
import com.system.technologyinformation.common.rx.RxUtil;
import com.system.technologyinformation.model.HomeBannerBean;
import com.system.technologyinformation.model.HomeShoppingSpreeBean;
import com.system.technologyinformation.model.LoginBean;
import com.system.technologyinformation.model.PagingInformationBean;
import com.system.technologyinformation.model.http.base.BaseResponseInfo;
import com.system.technologyinformation.module.contract.PanicBuyingContract;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

public class PanicBuyingPresenter extends BasePresenter<PanicBuyingContract.View> implements PanicBuyingContract.Presenter {
    @Inject
    ApiService apiService;

    @Inject
    public PanicBuyingPresenter() {
    }

    @Override
    public void getHomeShoppingDataResult(int pageNum, int pageSize) {
        addSubscribe(apiService.getHomeShoppingDataResult(pageNum, pageSize)
                .compose(RxUtil.<BaseResponseInfo<PagingInformationBean<List<HomeShoppingSpreeBean>>>>rxSchedulerHelper())
                .compose(RxUtil.<PagingInformationBean<List<HomeShoppingSpreeBean>>>handleResult())
                .doOnNext(new Consumer<PagingInformationBean<List<HomeShoppingSpreeBean>>>() {
                    @Override
                    public void accept(PagingInformationBean<List<HomeShoppingSpreeBean>> productItemInfos) throws Exception {
                        LogUtils.d("ShoppingData" + productItemInfos.getList().size());
                    }
                })
                .subscribeWith(new CommonSubscriber<PagingInformationBean<List<HomeShoppingSpreeBean>>>(getView()) {
                    @Override
                    public void onNext(PagingInformationBean<List<HomeShoppingSpreeBean>> pagingInformationBean) {
                        getView().setHomeShoppingDataResult(pagingInformationBean.getList());
                    }
                }));
    }

    @Override
    public void getHomeBannerResult() {
        addSubscribe(apiService.getHomeBannerResult(2)
                .compose(RxUtil.<BaseResponseInfo<List<HomeBannerBean>>>rxSchedulerHelper())
                .compose(RxUtil.<List<HomeBannerBean>>handleResult())
                .doOnNext(new Consumer<List<HomeBannerBean>>() {
                    @Override
                    public void accept(List<HomeBannerBean> productItemInfos) throws Exception {
                        LogUtils.d("BannerNumber" + productItemInfos.size());
                    }
                })
                .subscribeWith(new CommonSubscriber<List<HomeBannerBean>>(getView()) {
                    @Override
                    public void onNext(List<HomeBannerBean> pagingInformationBean) {
                        getView().setHomeBannerResult(pagingInformationBean);
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
