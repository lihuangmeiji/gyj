package com.system.technologyinformation.module.presenter;

import com.blankj.utilcode.util.LogUtils;
import com.system.technologyinformation.common.mvp.BasePresenter;
import com.system.technologyinformation.common.net.ApiService;
import com.system.technologyinformation.common.rx.CommonSubscriber;
import com.system.technologyinformation.common.rx.RxUtil;
import com.system.technologyinformation.model.AliyunPlayerSkinBean;
import com.system.technologyinformation.model.EnrollmentBean;
import com.system.technologyinformation.model.OnDemandBean;
import com.system.technologyinformation.model.http.base.BaseResponseInfo;
import com.system.technologyinformation.module.contract.AliyunPlayerSkinContract;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

public class AliyunPlayerSkinPresenter extends BasePresenter<AliyunPlayerSkinContract.View> implements AliyunPlayerSkinContract.Presenter {
    @Inject
    ApiService apiService;

    @Inject
    public AliyunPlayerSkinPresenter() {
    }

    @Override
    public void getAliyunPlayerSkinInfoResult() {
        addSubscribe(apiService.getAliyunPlayerSkinInfoResult()
                .compose(RxUtil.<BaseResponseInfo<AliyunPlayerSkinBean>>rxSchedulerHelper())
                .compose(RxUtil.<AliyunPlayerSkinBean>handleResult())
                .doOnNext(new Consumer<AliyunPlayerSkinBean>() {
                    @Override
                    public void accept(AliyunPlayerSkinBean productItemInfos) throws Exception {

                        LogUtils.d(productItemInfos.getDescription());

                    }
                })
                .subscribeWith(new CommonSubscriber<AliyunPlayerSkinBean>(getView()) {
                    @Override
                    public void onNext(AliyunPlayerSkinBean o) {
                        getView().setAliyunPlayerSkinInfoResult(o);
                    }
                }));
    }

    @Override
    public void getOnDemandListInfoResult(int vodPageNum,int vodPageSize) {
        addSubscribe(apiService.getOnDemandListInfoResult(vodPageNum,vodPageSize)
                .compose(RxUtil.<BaseResponseInfo<OnDemandBean>>rxSchedulerHelper())
                .compose(RxUtil.<OnDemandBean>handleResult())
                .doOnNext(new Consumer<OnDemandBean>() {
                    @Override
                    public void accept(OnDemandBean productItemInfos) throws Exception {

                        LogUtils.d(productItemInfos.getDescription());

                    }
                })
                .subscribeWith(new CommonSubscriber<OnDemandBean>(getView()) {
                    @Override
                    public void onNext(OnDemandBean o) {
                        getView().setOnDemandListInfoResult(o);
                    }
                }));
    }

    @Override
    public void getOnDemandDetailesInfoResult(String vodId) {
        addSubscribe(apiService.getOnDemandDetailesInfoResult(vodId)
                .compose(RxUtil.<BaseResponseInfo>rxSchedulerHelper())
                .doOnNext(new Consumer<BaseResponseInfo>() {
                    @Override
                    public void accept(BaseResponseInfo baseResponseInfo) throws Exception {
                        LogUtils.d(baseResponseInfo.getMsg());
                    }
                })
                .subscribeWith(new CommonSubscriber<BaseResponseInfo>(getView()) {
                    @Override
                    public void onNext(BaseResponseInfo result) {
                        getView().setOnDemandDetailesInfoResult(result);
                    }
                }));
    }
}
