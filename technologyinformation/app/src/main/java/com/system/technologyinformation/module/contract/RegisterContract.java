package com.system.technologyinformation.module.contract;

import com.system.technologyinformation.common.mvp.BaseView;
import com.system.technologyinformation.model.http.base.BaseResponseInfo;
import com.wjj.easy.easyandroid.mvp.EasyBasePresenter;

public interface RegisterContract {
    interface View extends BaseView {
        void registerUserInfoResult(BaseResponseInfo baseResponseInfo);
        void registerUserInfoSmsResult(BaseResponseInfo baseResponseInfo);
        void registerCheckPhoneResult(BaseResponseInfo baseResponseInfo);
    }

    interface Presenter extends EasyBasePresenter<View> {
        void registerUserInfo(String phone, String password, String code);
        void registerUserInfoSms(String phone, String type);
        void registerCheckPhone(String phone);
    }
}
