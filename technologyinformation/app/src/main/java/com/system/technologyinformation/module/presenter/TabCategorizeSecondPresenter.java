package com.system.technologyinformation.module.presenter;

import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.system.technologyinformation.common.mvp.BasePresenter;
import com.system.technologyinformation.common.net.ApiService;
import com.system.technologyinformation.common.rx.CommonSubscriber;
import com.system.technologyinformation.common.rx.RxUtil;
import com.system.technologyinformation.model.DailyMissionBean;
import com.system.technologyinformation.model.HomeDataBean;
import com.system.technologyinformation.model.LoginBean;
import com.system.technologyinformation.model.PagingInformationBean;
import com.system.technologyinformation.model.RewardBean;
import com.system.technologyinformation.model.SignBean;
import com.system.technologyinformation.model.StoreInfoBean;
import com.system.technologyinformation.model.http.base.BaseResponseInfo;
import com.system.technologyinformation.module.contract.TabCategorizeSecondContract;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

public class TabCategorizeSecondPresenter extends BasePresenter<TabCategorizeSecondContract.View> implements TabCategorizeSecondContract.Presenter {
    @Inject
    ApiService apiService;

    @Inject
    public TabCategorizeSecondPresenter() {

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
    public void getUserSignDayInfoResult(String date) {
        addSubscribe(apiService.getUserSignDayInfoResult(date)
                .compose(RxUtil.<BaseResponseInfo<PagingInformationBean<List<SignBean>>>>rxSchedulerHelper())
                .compose(RxUtil.<PagingInformationBean<List<SignBean>>>handleResult())
                .doOnNext(new Consumer<PagingInformationBean<List<SignBean>>>() {
                    @Override
                    public void accept(PagingInformationBean<List<SignBean>> productItemInfos) throws Exception {
                        LogUtils.d("signDayInfo" + productItemInfos.getList().size());
                    }
                })
                .subscribeWith(new CommonSubscriber<PagingInformationBean<List<SignBean>>>(getView()) {
                    @Override
                    public void onNext(PagingInformationBean<List<SignBean>> pagingInformationBean) {
                        getView().setUserSignDayInfoResult(pagingInformationBean.getList());
                    }
                }));
    }

    @Override
    public void getUserSignMonthInfoResult(final Calendar date, final int type) {
        addSubscribe(apiService.getUserSignMonthInfoResult(TimeUtils.date2String(date.getTime(), "yyyy-MM"))
                .compose(RxUtil.<BaseResponseInfo<List<String>>>rxSchedulerHelper())
                .compose(RxUtil.<List<String>>handleResult())
                .doOnNext(new Consumer<List<String>>() {
                    @Override
                    public void accept(List<String> productItemInfos) throws Exception {
                        LogUtils.d("signMonthInfo" + productItemInfos.size());
                    }
                })
                .subscribeWith(new CommonSubscriber<List<String>>(getView()) {
                    @Override
                    public void onNext(List<String> pagingInformationBean) {
                        getView().setUserSignMonthInfoResult(pagingInformationBean, type, date);
                    }
                }));
    }

    /*
    * 废弃
    * */
    @Override
    public void getUserOpenRewardInfoResult(int type) {
        addSubscribe(apiService.getUserOpenRewardInfoResult(type)
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
                        getView().getUserOpenRewardInfoResult(rewardBean);
                    }
                }));
    }

    @Override
    public void getUserSignRepairAddResult(String checkinDate) {
        Map<String, String> objectMap = new HashMap<>();
        objectMap.put("checkinDate", checkinDate);
        addSubscribe(apiService.getUserSignRepairAddResult(objectMap)
                .compose(RxUtil.<BaseResponseInfo<RewardBean>>rxSchedulerHelper())
                .compose(RxUtil.<RewardBean>handleResult())
                .doOnNext(new Consumer<RewardBean>() {
                    @Override
                    public void accept(RewardBean rewardBean) throws Exception {

                    }
                })
                .subscribeWith(new CommonSubscriber<RewardBean>(getView()) {
                    @Override
                    public void onNext(RewardBean rewardBean) {
                        getView().setUserSignRepairAddResult(rewardBean);
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
    public void getDailyMissionListResult() {
        addSubscribe(apiService.getDailyMissionListResult()
                .compose(RxUtil.<BaseResponseInfo<List<DailyMissionBean>>>rxSchedulerHelper())
                .compose(RxUtil.<List<DailyMissionBean>>handleResult())
                .doOnNext(new Consumer<List<DailyMissionBean>>() {
                    @Override
                    public void accept(List<DailyMissionBean> str) throws Exception {

                    }
                })
                .subscribeWith(new CommonSubscriber<List<DailyMissionBean>>(getView()) {
                    @Override
                    public void onNext(List<DailyMissionBean> dailyMissionBeanList) {
                        getView().setDailyMissionListResult(dailyMissionBeanList);
                    }
                }));
    }

    @Override
    public void getUserShareContentResult() {
        addSubscribe(apiService.getUserShareContentResult(1)
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
                        getView().setUserShareContentResult(str);
                    }
                }));
    }

    @Override
    public void addUserShareResult() {
        addSubscribe(apiService.addUserShareResult()
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
                        getView().getUserShareResult(str);
                    }
                }));
    }
}
