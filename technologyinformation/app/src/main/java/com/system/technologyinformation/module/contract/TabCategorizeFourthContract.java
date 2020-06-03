package com.system.technologyinformation.module.contract;

import com.system.technologyinformation.common.mvp.BaseView;
import com.system.technologyinformation.model.LoginBean;
import com.system.technologyinformation.model.http.base.BaseResponseInfo;
import com.wjj.easy.easyandroid.mvp.EasyBasePresenter;

import java.io.File;

public interface TabCategorizeFourthContract  {

    interface View extends BaseView {
        void setUpdateUserInfoResult(LoginBean loginBean);
        void setUserLogoutResult(String str);
        void setUserSalarytResult(String str);
        void setUpdateUserIcoResult(String str);
        void setFileUploadResult(String str);
        void setUserLoginResult(LoginBean loginBean);
    }

    interface Presenter extends EasyBasePresenter<View> {
        void getUpdateUserInfoResult(int id);
        void getUserLogoutResult();
        void getUserSalarytResult(String salary);
        void getUpdateUserIcoResult(String icon);
        void getFileUploadResult(File file);
        void getUserLoginResult(String phone, String invCode, String smsCode, String token);
    }
}
