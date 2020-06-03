package com.system.technologyinformation.module.presenter;

import com.blankj.utilcode.util.LogUtils;
import com.system.technologyinformation.common.mvp.BasePresenter;
import com.system.technologyinformation.common.net.ApiService;
import com.system.technologyinformation.common.rx.CommonSubscriber;
import com.system.technologyinformation.common.rx.RxUtil;
import com.system.technologyinformation.model.PagingInformationBean;
import com.system.technologyinformation.model.ProfessionalTypeBean;
import com.system.technologyinformation.model.http.base.BaseResponseInfo;
import com.system.technologyinformation.module.contract.UserProfessionalAddContract;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public class UserProfessionalAddPresenter extends BasePresenter<UserProfessionalAddContract.View> implements UserProfessionalAddContract.Presenter {
    @Inject
    ApiService apiService;

    @Inject
    public UserProfessionalAddPresenter() {

    }


    @Override
    public void getProfessionalTypeListResult() {
        addSubscribe(apiService.getProfessionalTypeListResult()
                .compose(RxUtil.<BaseResponseInfo<PagingInformationBean<List<ProfessionalTypeBean>>>>rxSchedulerHelper())
                .compose(RxUtil.<PagingInformationBean<List<ProfessionalTypeBean>>>handleResult())
                .doOnNext(new Consumer<PagingInformationBean<List<ProfessionalTypeBean>>>() {
                    @Override
                    public void accept(PagingInformationBean<List<ProfessionalTypeBean>> productItemInfos) throws Exception {
                        LogUtils.d("ProfessionalTypeBean" + productItemInfos.getList().size());
                    }
                })
                .subscribeWith(new CommonSubscriber<PagingInformationBean<List<ProfessionalTypeBean>>>(getView()) {
                    @Override
                    public void onNext(PagingInformationBean<List<ProfessionalTypeBean>> pagingInformationBean) {
                        getView().setProfessionalTypeListResult(pagingInformationBean.getList());
                    }
                }));
    }

    @Override
    public void getFileUploadResult(File file) {
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("application/otcet-stream"), file);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        String descriptionString = "fileUpload";
        RequestBody description =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), descriptionString);
        Map<String, RequestBody> objectMap = new HashMap<>();
        objectMap.put("type", RequestBody.create(MediaType.parse("text/plain"), 3+""));
        addSubscribe(apiService.getFileUploadResult(description, body,objectMap)
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
                    public void onNext(String pagingInformationBean) {
                        getView().setFileUploadResult
                                (pagingInformationBean);
                    }
                }));
    }

    @Override
    public void getProfessionalAddResult(int type, int ckId, String carrerCertificate) {
        Map<String, String> objectMap = new HashMap<>();
        objectMap.put("type", type + "");
        objectMap.put("ckId", ckId + "");
        //objectMap.put("carrerCertificate", carrerCertificate);
        addSubscribe(apiService.getProfessionalAddResult(objectMap)
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
                        getView().setProfessionalAddResult(str);
                    }
                }));
    }

    @Override
    public void getProfessionalUpdateResult(int id, int type, int ckId, String carrerCertificate) {
        Map<String, String> objectMap = new HashMap<>();
        objectMap.put("type", type + "");
        objectMap.put("ckId", ckId + "");
        objectMap.put("carrerCertificate", carrerCertificate);
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
                        getView().setProfessionalUpdateResult(str);
                    }
                }));
    }
}
