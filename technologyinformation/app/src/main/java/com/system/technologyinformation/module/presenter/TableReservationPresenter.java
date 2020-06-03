package com.system.technologyinformation.module.presenter;

import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.system.technologyinformation.common.mvp.BasePresenter;
import com.system.technologyinformation.common.net.ApiService;
import com.system.technologyinformation.common.rx.CommonSubscriber;
import com.system.technologyinformation.common.rx.RxUtil;
import com.system.technologyinformation.model.ConfirmOrderBean;
import com.system.technologyinformation.model.DeliveryBean;
import com.system.technologyinformation.model.LoginBean;
import com.system.technologyinformation.model.OnlineSupermarketBean;
import com.system.technologyinformation.model.PagingInformationBean;
import com.system.technologyinformation.model.http.base.BaseResponseInfo;
import com.system.technologyinformation.module.contract.TableReservationContract;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

public class TableReservationPresenter extends BasePresenter<TableReservationContract.View> implements TableReservationContract.Presenter {
    @Inject
    ApiService apiService;

    @Inject
    public TableReservationPresenter() {

    }

    @Override
    public void getTableReservationList(String name, int pageSize, int pageNum) {
        addSubscribe(apiService.getTableReservationList(name, pageSize, pageNum)
                .compose(RxUtil.<BaseResponseInfo<PagingInformationBean<List<OnlineSupermarketBean>>>>rxSchedulerHelper())
                .compose(RxUtil.<PagingInformationBean<List<OnlineSupermarketBean>>>handleResult())
                .doOnNext(new Consumer<PagingInformationBean<List<OnlineSupermarketBean>>>() {
                    @Override
                    public void accept(PagingInformationBean<List<OnlineSupermarketBean>> constructionPlaceBeanList) throws Exception {
                        LogUtils.d("onlineSupermarketGoodsList" + constructionPlaceBeanList.getList().size());
                    }
                })
                .subscribeWith(new CommonSubscriber<PagingInformationBean<List<OnlineSupermarketBean>>>(getView()) {
                    @Override
                    public void onNext(PagingInformationBean<List<OnlineSupermarketBean>> constructionPlaceBeanList) {
                        getView().setTableReservationList(constructionPlaceBeanList.getList());
                    }
                }));
    }

    @Override
    public void getOnlineSupermarketGoodsOreder(String jsonStr) {
        Map<String, String> objectMap = new HashMap<>();
   /*     objectMap.put("jsonStr", jsonStr);
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.add(GregorianCalendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);*/
        objectMap.put("jsonStr", jsonStr);
        //objectMap.put("dateTime", TimeUtils.date2String(calendar.getTime()));
        addSubscribe(apiService.setOnlineSupermarketGoodsOreder(objectMap)
                .compose(RxUtil.<BaseResponseInfo<ConfirmOrderBean>>rxSchedulerHelper())
                .compose(RxUtil.<ConfirmOrderBean>handleResult())
                .doOnNext(new Consumer<ConfirmOrderBean>() {
                    @Override
                    public void accept(ConfirmOrderBean confirmOrderBean) throws Exception {

                    }
                })
                .subscribeWith(new CommonSubscriber<ConfirmOrderBean>(getView()) {
                    @Override

                    public void onNext(ConfirmOrderBean o) {
                        getView().setOnlineSupermarketGoodsOreder(o);
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
       /* addSubscribe(apiService.getUserLoginResult(objectMap)
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
}
