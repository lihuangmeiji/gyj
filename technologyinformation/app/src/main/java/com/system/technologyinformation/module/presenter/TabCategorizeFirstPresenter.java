package com.system.technologyinformation.module.presenter;

import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.LogUtils;
import com.system.technologyinformation.common.mvp.BasePresenter;
import com.system.technologyinformation.common.net.ApiService;
import com.system.technologyinformation.common.rx.CommonSubscriber;
import com.system.technologyinformation.common.rx.RxUtil;
import com.system.technologyinformation.model.ConvenientFunctionsBean;
import com.system.technologyinformation.model.FlickerScreenBean;
import com.system.technologyinformation.model.HomeBannerBean;
import com.system.technologyinformation.model.HomeConfigurationInformationBean;
import com.system.technologyinformation.model.HomeDataBean;
import com.system.technologyinformation.model.HomeShoppingSpreeBean;
import com.system.technologyinformation.model.LoginBean;
import com.system.technologyinformation.model.PagingInformationBean;
import com.system.technologyinformation.model.ProductItemInfo;
import com.system.technologyinformation.model.RewardBean;
import com.system.technologyinformation.model.StoreInfoBean;
import com.system.technologyinformation.model.http.base.BaseResponseInfo;
import com.system.technologyinformation.module.contract.TabCategorizeFirstContract;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

public class TabCategorizeFirstPresenter extends BasePresenter<TabCategorizeFirstContract.View> implements TabCategorizeFirstContract.Presenter {
    @Inject
    ApiService apiService;

    @Inject
    public TabCategorizeFirstPresenter() {
    }

    @Override
    public void getHomeBannerResult() {
        addSubscribe(apiService.getHomeBannerResult(1)
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
    public void getHomeShoppingDataResult() {
        addSubscribe(apiService.getHomeShoppingDataResult1()
                .compose(RxUtil.<BaseResponseInfo<List<HomeShoppingSpreeBean>>>rxSchedulerHelper())
                .compose(RxUtil.<List<HomeShoppingSpreeBean>>handleResult())
                .doOnNext(new Consumer<List<HomeShoppingSpreeBean>>() {
                    @Override
                    public void accept(List<HomeShoppingSpreeBean> productItemInfos) throws Exception {
                        LogUtils.d("BannerData" + productItemInfos.size());
                    }
                })
                .subscribeWith(new CommonSubscriber<List<HomeShoppingSpreeBean>>(getView()) {
                    @Override
                    public void onNext(List<HomeShoppingSpreeBean> pagingInformationBean) {
                        getView().setHomeShoppingDataResult(pagingInformationBean);
                    }
                }));
    }

    @Override
    public void getUpdateUserInfoResult(int id) {
        addSubscribe(apiService.getUpdateUserInfoResult1()
                .compose(RxUtil.<BaseResponseInfo>rxSchedulerHelper())
                .doOnNext(new Consumer<BaseResponseInfo>() {
                    @Override
                    public void accept(BaseResponseInfo baseResponseInfo) throws Exception {
                        LogUtils.d("str" + baseResponseInfo.getCode());
                    }
                })
                .subscribeWith(new CommonSubscriber<BaseResponseInfo>(getView()) {
                    @Override
                    public void onNext(BaseResponseInfo baseResponseInfo) {
                        getView().setUpdateUserInfoResult(baseResponseInfo);
                    }
                }));
    }

    @Override
    public void getFunctionDivisionOne() {
        addSubscribe(apiService.getFunctionDivisionOne()
                .compose(RxUtil.<BaseResponseInfo<List<ConvenientFunctionsBean>>>rxSchedulerHelper())
                .compose(RxUtil.<List<ConvenientFunctionsBean>>handleResult())
                .doOnNext(new Consumer<List<ConvenientFunctionsBean>>() {
                    @Override
                    public void accept(List<ConvenientFunctionsBean> productItemInfos) throws Exception {
                        LogUtils.d("BannerNumber" + productItemInfos.size());
                    }
                })
                .subscribeWith(new CommonSubscriber<List<ConvenientFunctionsBean>>(getView()) {
                    @Override
                    public void onNext(List<ConvenientFunctionsBean> pagingInformationBean) {
                        getView().setFunctionDivisionOne(pagingInformationBean);
                    }
                }));
    }

    @Override
    public void getFunctionDivisionTwo() {
        addSubscribe(apiService.getFunctionDivisionTwo()
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
                        getView().setFunctionDivisionTwo(result);
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
    public void getUserSignAddResult() {
        addSubscribe(apiService.getUserSignAddResult()
                .compose(RxUtil.<BaseResponseInfo<RewardBean>>rxSchedulerHelper())
                .compose(RxUtil.<RewardBean>handleResult())
                .doOnNext(new Consumer<RewardBean>() {
                    @Override
                    public void accept(RewardBean rewardBean) throws Exception {

                        LogUtils.d(rewardBean.isCheckIn());

                    }
                })
                .subscribeWith(new CommonSubscriber<RewardBean>(getView()) {
                    @Override
                    public void onNext(RewardBean rewardBean) {
                        getView().setUserSignAddResult(rewardBean);
                    }
                }));
    }

    @Override
    public void getShopInfo(String content) {
        Map<String, Object> objectMap = new HashMap<>();
        if (!EmptyUtils.isEmpty(content)) {
            objectMap.put("content", content);
        }
        addSubscribe(apiService.getShopInfo(objectMap)
                .compose(RxUtil.<BaseResponseInfo<StoreInfoBean>>rxSchedulerHelper())
                .compose(RxUtil.<StoreInfoBean>handleResult())
                .doOnNext(new Consumer<StoreInfoBean>() {
                    @Override
                    public void accept(StoreInfoBean storeInfoBean) throws Exception {
                        LogUtils.d("storeInfoBean" + storeInfoBean.getName());
                    }
                })
                .subscribeWith(new CommonSubscriber<StoreInfoBean>(getView()) {
                    @Override
                    public void onNext(StoreInfoBean storeInfoBean) {
                        getView().setShopInfoResult(storeInfoBean);
                    }
                }));
    }

    @Override
    public void getHomeConfigurationInformation() {
        addSubscribe(apiService.getHomeConfigurationInformation()
                .compose(RxUtil.<BaseResponseInfo<List<HomeConfigurationInformationBean>>>rxSchedulerHelper())
                .compose(RxUtil.<List<HomeConfigurationInformationBean>>handleResult())
                .doOnNext(new Consumer<List<HomeConfigurationInformationBean>>() {
                    @Override
                    public void accept(List<HomeConfigurationInformationBean> productItemInfos) throws Exception {
                        LogUtils.d("pagingInformationBean" + productItemInfos.size());
                    }
                })
                .subscribeWith(new CommonSubscriber<List<HomeConfigurationInformationBean>>(getView()) {
                    @Override
                    public void onNext(List<HomeConfigurationInformationBean> pagingInformationBean) {
                        getView().setHomeConfigurationInformation(pagingInformationBean);
                    }
                }));
    }

    @Override
    public void getFlickerScreenInfo() {
        addSubscribe(apiService.getFlickerScreenInfo()
                .compose(RxUtil.<BaseResponseInfo<List<FlickerScreenBean>>>rxSchedulerHelper())
                .compose(RxUtil.<List<FlickerScreenBean>>handleResult())
                .doOnNext(new Consumer<List<FlickerScreenBean>>() {
                    @Override
                    public void accept(List<FlickerScreenBean> productItemInfos) throws Exception {
                        LogUtils.d("pagingInformationBean" + productItemInfos.size());
                    }
                })
                .subscribeWith(new CommonSubscriber<List<FlickerScreenBean>>(getView()) {
                    @Override
                    public void onNext(List<FlickerScreenBean> pagingInformationBean) {
                        getView().setFlickerScreenResult(pagingInformationBean);
                    }
                }));
    }
}
