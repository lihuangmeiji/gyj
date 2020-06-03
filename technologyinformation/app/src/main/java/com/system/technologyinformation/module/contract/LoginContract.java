package com.system.technologyinformation.module.contract;

import com.system.technologyinformation.common.mvp.BaseView;
import com.system.technologyinformation.model.LoginBean;
import com.system.technologyinformation.model.http.base.BaseResponseInfo;
import com.wjj.easy.easyandroid.mvp.EasyBasePresenter;

public interface LoginContract {
    interface View extends BaseView {
        void setUserLoginResult(LoginBean loginBean);
        void registerCheckPhoneResult(BaseResponseInfo baseResponseInfo);
        void registerUserInfoSmsResult(BaseResponseInfo baseResponseInfo);
    }

    interface Presenter extends EasyBasePresenter<View> {
        void getUserLoginResult(String phone, String invCode, String smsCode, String token);
        void registerCheckPhone(String phone);
        void registerUserInfoSms(String phone);
    }
}
