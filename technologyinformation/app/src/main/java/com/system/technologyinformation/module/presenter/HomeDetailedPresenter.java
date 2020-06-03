package com.system.technologyinformation.module.presenter;

import com.blankj.utilcode.util.LogUtils;
import com.system.technologyinformation.common.mvp.BasePresenter;
import com.system.technologyinformation.common.net.ApiService;
import com.system.technologyinformation.common.rx.CommonSubscriber;
import com.system.technologyinformation.common.rx.RxUtil;
import com.system.technologyinformation.model.FeedbacksBean;
import com.system.technologyinformation.model.HomeDataBean;
import com.system.technologyinformation.model.HomeDetailedBean;
import com.system.technologyinformation.model.PagingInformationBean;
import com.system.technologyinformation.model.UserMessage;
import com.system.technologyinformation.model.http.base.BaseResponseInfo;
import com.system.technologyinformation.module.contract.HomeDetailedContract;
import com.system.technologyinformation.utils.MD5Util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

public class HomeDetailedPresenter extends BasePresenter<HomeDetailedContract.View> implements HomeDetailedContract.Presenter {
    @Inject
    ApiService apiService;

    @Inject
    public HomeDetailedPresenter() {

    }

    @Override
    public void getHomeDetailedResult(int id, boolean countReadNum) {
        addSubscribe(apiService.getHomeDetailedResult(id, countReadNum)
                .compose(RxUtil.<BaseResponseInfo<HomeDetailedBean>>rxSchedulerHelper())
                .compose(RxUtil.<HomeDetailedBean>handleResult())
                .doOnNext(new Consumer<HomeDetailedBean>() {
                    @Override
                    public void accept(HomeDetailedBean productItemInfos) throws Exception {
                        LogUtils.d("HomeDetailedBean" + productItemInfos.getContent());
                    }
                })
                .subscribeWith(new CommonSubscriber<HomeDetailedBean>(getView()) {
                    @Override
                    public void onNext(HomeDetailedBean homeDetailedBean) {
                        getView().setHomeDetailedResult(homeDetailedBean);
                    }
                }));
    }

    @Override
    public void getHomeDetailedAddVoteResult(String ids, int viId) {
        Map<String, String> objectMap = new HashMap<>();
        objectMap.put("ids", ids);
        objectMap.put("viId", viId + "");
        //String gson = new Gson().toJson(objectMap);
        //RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), gson);
        addSubscribe(apiService.getHomeDetailedAddVoteResult(objectMap)
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
                        getView().setHomeDetailedAddVoteResult(str);
                    }
                }));
    }

    @Override
    public void getHomeDetailedAddFeedbackResult(int cId, int cftId, final int position) {
        Map<String, String> objectMap = new HashMap<>();
        objectMap.put("cId", cId + "");
        objectMap.put("cftId", cftId + "");
        //String gson = new Gson().toJson(objectMap);
        //RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), gson);
        addSubscribe(apiService.getHomeDetailedAddFeedbackResult(objectMap)
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
                        getView().setHomeDetailedAddFeedbackResult(str,position);
                    }
                }));
    }

    @Override
    public void getHomeDetailedAddForwardResult(int id) {
        Map<String, String> objectMap = new HashMap<>();
        objectMap.put("id", id + "");
        //String gson = new Gson().toJson(objectMap);
        //RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), gson);
        addSubscribe(apiService.getHomeDetailedAddForwardResult(objectMap)
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
                        getView().setHomeDetailedAddForwardResult(str);
                    }
                }));
    }

    @Override
    public void getHomeDetailedUpdateFeedbackResult(int cId) {
        Map<String, String> objectMap = new HashMap<>();
        objectMap.put("cId", cId + "");
        //String gson = new Gson().toJson(objectMap);
        //RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), gson);
        addSubscribe(apiService.getHomeDetailedUpdateFeedbackResult(objectMap)
                .compose(RxUtil.<BaseResponseInfo<PagingInformationBean<List<FeedbacksBean>>>>rxSchedulerHelper())
                .compose(RxUtil.<PagingInformationBean<List<FeedbacksBean>>>handleResult())
                .doOnNext(new Consumer<PagingInformationBean<List<FeedbacksBean>>>() {
                    @Override
                    public void accept(PagingInformationBean<List<FeedbacksBean>> productItemInfos) throws Exception {
                        LogUtils.d("productItemInfos" + productItemInfos.getList().size());
                    }
                })
                .subscribeWith(new CommonSubscriber<PagingInformationBean<List<FeedbacksBean>>>(getView()) {
                    @Override
                    public void onNext(PagingInformationBean<List<FeedbacksBean>> pagingInformationBean) {
                        getView().setHomeDetailedUpdateFeedbackResult(pagingInformationBean.getList());
                    }
                }));
    }
}
