package com.system.technologyinformation.module.presenter;

import com.blankj.utilcode.util.LogUtils;
import com.system.technologyinformation.common.mvp.BasePresenter;
import com.system.technologyinformation.common.net.ApiService;
import com.system.technologyinformation.common.rx.CommonSubscriber;
import com.system.technologyinformation.common.rx.RxUtil;
import com.system.technologyinformation.model.DeliveryBean;
import com.system.technologyinformation.model.GroupUserInfoBean;
import com.system.technologyinformation.model.OnDemandBean;
import com.system.technologyinformation.model.http.base.BaseResponseInfo;
import com.system.technologyinformation.module.contract.GroupDetailsContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

public class GroupDetailsPresenter extends BasePresenter<GroupDetailsContract.View> implements GroupDetailsContract.Presenter {

    @Inject
    ApiService apiService;

    @Inject
    public GroupDetailsPresenter() {

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

    @Override
    public void loadGroupUserInfo(String groupId) {
        addSubscribe(apiService.loadGroupUserInfo(groupId)
                .compose(RxUtil.<BaseResponseInfo<List<GroupUserInfoBean>>>rxSchedulerHelper())
                .compose(RxUtil.<List<GroupUserInfoBean>>handleResult())
                .doOnNext(new Consumer<List<GroupUserInfoBean>>() {
                    @Override
                    public void accept(List<GroupUserInfoBean> productItemInfos) throws Exception {

                        LogUtils.d(productItemInfos.toString());

                    }
                })
                .subscribeWith(new CommonSubscriber<List<GroupUserInfoBean>>(getView()) {
                    @Override
                    public void onNext(List<GroupUserInfoBean> o) {
                        getView().loadGroupUserInforResult(o);
                    }
                }));
    }
}
