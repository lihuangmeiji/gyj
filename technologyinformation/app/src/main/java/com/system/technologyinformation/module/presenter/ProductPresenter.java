package com.system.technologyinformation.module.presenter;

import com.blankj.utilcode.util.LogUtils;
import com.system.technologyinformation.common.mvp.BasePresenter;
import com.system.technologyinformation.common.net.ApiService;
import com.system.technologyinformation.common.rx.CommonSubscriber;
import com.system.technologyinformation.common.rx.RxUtil;
import com.system.technologyinformation.model.ProductItemInfo;
import com.system.technologyinformation.model.http.base.BaseResponseInfo;
import com.system.technologyinformation.module.contract.ProductContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

/**
 * Created by wujiajun on 2017/10/18.
 */

public class ProductPresenter extends BasePresenter<ProductContract.View> implements ProductContract.Presenter {

    @Inject
    ApiService apiService;

    @Inject
    public ProductPresenter() {
    }

    @Override
    public void getData(int limit, int size) {
        addSubscribe(apiService.getProductList(limit, size, "tj")
                .compose(RxUtil.<BaseResponseInfo<List<ProductItemInfo>>>rxSchedulerHelper())
                .compose(RxUtil.<List<ProductItemInfo>>handleResult())
                .doOnNext(new Consumer<List<ProductItemInfo>>() {
                    @Override
                    public void accept(List<ProductItemInfo> productItemInfos) throws Exception {
                        for (ProductItemInfo o : productItemInfos) {
                            LogUtils.d(o.getName());
                        }
                    }
                })
                .subscribeWith(new CommonSubscriber<List<ProductItemInfo>>(getView()) {
                    @Override
                    public void onNext(List<ProductItemInfo> o) {
                        getView().callback(true, o);
                    }
                }));
    }
}
