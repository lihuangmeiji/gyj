package com.system.technologyinformation.module.presenter;

import com.blankj.utilcode.util.LogUtils;
import com.system.technologyinformation.common.mvp.BasePresenter;
import com.system.technologyinformation.common.net.ApiService;
import com.system.technologyinformation.common.rx.CommonSubscriber;
import com.system.technologyinformation.common.rx.RxUtil;
import com.system.technologyinformation.model.GroupInfoBean;
import com.system.technologyinformation.model.http.base.BaseResponseInfo;
import com.system.technologyinformation.module.contract.TabCategorizeFiveContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

public class TabCategorizeFivePresenter extends BasePresenter<TabCategorizeFiveContract.View> implements TabCategorizeFiveContract.Presenter {
    @Inject
    ApiService apiService;

    @Inject
    public TabCategorizeFivePresenter() {

    }

    @Override
    public void getUserGroupInfo() {
        addSubscribe(apiService.loadGroupInfo()
                .compose(RxUtil.<BaseResponseInfo<List<GroupInfoBean>>>rxSchedulerHelper())
                .compose(RxUtil.<List<GroupInfoBean>>handleResult())
                .doOnNext(new Consumer<List<GroupInfoBean>>() {
                    @Override
                    public void accept(List<GroupInfoBean> groupInfoBeanList) throws Exception {
                        LogUtils.d("productItemInfos" + groupInfoBeanList.toString());
                    }
                })
                .subscribeWith(new CommonSubscriber<List<GroupInfoBean>>(getView()) {
                    @Override
                    public void onNext(List<GroupInfoBean> groupInfoBeanList) {
                        getView().setUserGroupInfo(groupInfoBeanList);
                    }
                }));
    }

    @Override
    public void refreshUserTime() {
        addSubscribe(apiService.refreshUserTime()
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
                        getView().refreshUserTimeResult(result);
                    }
                }));
    }
}
