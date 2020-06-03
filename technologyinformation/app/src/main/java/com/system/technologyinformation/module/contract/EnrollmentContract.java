package com.system.technologyinformation.module.contract;

import com.system.technologyinformation.common.mvp.BaseView;
import com.system.technologyinformation.model.EnrollmentBean;
import com.system.technologyinformation.model.LoginBean;
import com.system.technologyinformation.model.http.base.BaseResponseInfo;
import com.wjj.easy.easyandroid.mvp.EasyBasePresenter;

public interface EnrollmentContract {
    interface View extends BaseView {
        void setEnrollmentInfoResult(EnrollmentBean enrollmentBean);
        void setUpdateUserInfoResult(LoginBean loginBean);
        void setWhetherTheRegistrationIsSuccessful(String str);
        void setWhetherTheRegistration(String str);
    }

    interface Presenter extends EasyBasePresenter<View> {
        void getEnrollmentInfoResult();
        void getUpdateUserInfoResult(int id);
        void getWhetherTheRegistrationIsSuccessful(int raceId, String project);
        void getWhetherTheRegistration(int raceId);
    }
}
