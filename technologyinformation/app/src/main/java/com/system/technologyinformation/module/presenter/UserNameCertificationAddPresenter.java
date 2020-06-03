package com.system.technologyinformation.module.presenter;

import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.LogUtils;
import com.system.technologyinformation.common.mvp.BasePresenter;
import com.system.technologyinformation.common.net.ApiService;
import com.system.technologyinformation.common.rx.CommonSubscriber;
import com.system.technologyinformation.common.rx.RxUtil;
import com.system.technologyinformation.model.UserNameInfoBean;
import com.system.technologyinformation.model.http.base.BaseResponseInfo;
import com.system.technologyinformation.module.contract.UserNameCertificationAddContract;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UserNameCertificationAddPresenter extends BasePresenter<UserNameCertificationAddContract.View> implements UserNameCertificationAddContract.Presenter {
    @Inject
    ApiService apiService;

    @Inject
    public UserNameCertificationAddPresenter() {

    }

    @Override
    public void getFileUploadResult(File file, final int type) {
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("application/otcet-stream"), file);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        String descriptionString = "fileUpload";
        RequestBody description =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), descriptionString);
        Map<String, RequestBody> objectMap = new HashMap<>();
        objectMap.put("type", RequestBody.create(MediaType.parse("text/plain"), 3 + ""));
        addSubscribe(apiService.getFileUploadResult(description, body, objectMap)
                .compose(RxUtil.<BaseResponseInfo<String>>rxSchedulerHelper())
                .compose(RxUtil.<String>handleResult())
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String productItemInfos) throws Exception {
                        LogUtils.d("productItemInfos" + productItemInfos);
                    }
                })
                .subscribeWith(new CommonSubscriber<String>(getView()) {
                    @Override
                    public void onNext(String str) {
                        getView().setFileUploadResult
                                (str, type);
                    }
                }));
    }

    @Override
    public void getNameCertificationAddResult(int type, String realname, String idCode, String imgFront, String imgBack,String cardInfo,String validityPeriod) {
        Map<String, String> objectMap = new HashMap<>();
        if (!EmptyUtils.isEmpty(realname)) {
            objectMap.put("realName", realname);
        }
        if (!EmptyUtils.isEmpty(idCode)) {
            objectMap.put("idCode", idCode);
        }
        if (!EmptyUtils.isEmpty(imgFront)) {
            objectMap.put("imgFront", imgFront);
        }
        if (!EmptyUtils.isEmpty(imgBack)) {
            objectMap.put("imgBack", imgBack);
        }
        if (!EmptyUtils.isEmpty(cardInfo)) {
            objectMap.put("cardInfo", cardInfo);
        }
        if (!EmptyUtils.isEmpty(validityPeriod)) {
            objectMap.put("validityPeriod", validityPeriod);
        }
        addSubscribe(apiService.getNameCertificationAddResult(objectMap)
                .compose(RxUtil.<BaseResponseInfo<String>>rxSchedulerHelper())
                .compose(RxUtil.<String>handleResult())
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String str) throws Exception {
                        LogUtils.d("str" + str);
                    }
                })
                .subscribeWith(new CommonSubscriber<String>(getView()) {
                    @Override
                    public void onNext(String str) {
                        getView().setNameCertificationAddResult(str);
                    }
                }));
    }

    @Override
    public void getNameCertificationUpdateResult(int id, int type, String realname, String idCode, String imgFront, String imgBack) {
        Map<String, String> objectMap = new HashMap<>();
        objectMap.put("type", type + "");
        if (!EmptyUtils.isEmpty(realname)) {
            objectMap.put("realname", realname);
        }
        if (!EmptyUtils.isEmpty(idCode)) {
            objectMap.put("idCode", idCode);
        }
        if (!EmptyUtils.isEmpty(imgFront)) {
            objectMap.put("imgFront", imgFront);
        }
        if (!EmptyUtils.isEmpty(imgBack)) {
            objectMap.put("imgBack", imgBack);
        }
        objectMap.put("id", id + "");
        addSubscribe(apiService.getNameCertificationUpdateResult(objectMap)
                .compose(RxUtil.<BaseResponseInfo<String>>rxSchedulerHelper())
                .compose(RxUtil.<String>handleResult())
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String str) throws Exception {
                        LogUtils.d("str" + str);
                    }
                })
                .subscribeWith(new CommonSubscriber<String>(getView()) {
                    @Override
                    public void onNext(String str) {

                    }
                }));
    }

    @Override
    public void getNameCertificationInfoResult() {
        addSubscribe(apiService.getNameCertificationInfoResult()
                .compose(RxUtil.<BaseResponseInfo<UserNameInfoBean>>rxSchedulerHelper())
                .compose(RxUtil.<UserNameInfoBean>handleResult())
                .doOnNext(new Consumer<UserNameInfoBean>() {
                    @Override
                    public void accept(UserNameInfoBean userNameInfoBean) throws Exception {

                    }
                })
                .subscribeWith(new CommonSubscriber<UserNameInfoBean>(getView()) {
                    @Override
                    public void onNext(UserNameInfoBean userNameInfoBean) {
                        getView().setNameCertificationInfoResult(userNameInfoBean);
                    }
                }));
    }
}
