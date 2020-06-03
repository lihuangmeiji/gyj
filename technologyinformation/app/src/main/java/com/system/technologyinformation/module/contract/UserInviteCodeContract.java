package com.system.technologyinformation.module.contract;

import com.system.technologyinformation.common.mvp.BaseView;
import com.system.technologyinformation.model.LoginBean;
import com.wjj.easy.easyandroid.mvp.EasyBasePresenter;

public interface UserInviteCodeContract {
    interface View extends BaseView {
        void setUserShareContentResult(String str);
        void setUserLoginResult(LoginBean loginBean);
        void getUserShareResult(String str);
    }

    interface Presenter extends EasyBasePresenter<View> {
        void getUserShareContentResult();
        void getUserLoginResult(String phone, String invCode, String smsCode, String token);
        void addUserShareResult();
    }
}
