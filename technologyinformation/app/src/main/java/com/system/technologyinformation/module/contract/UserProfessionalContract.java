package com.system.technologyinformation.module.contract;

import com.system.technologyinformation.common.mvp.BaseView;
import com.system.technologyinformation.model.IdentificationInfosBean;
import com.system.technologyinformation.model.LoginBean;
import com.system.technologyinformation.model.ProfessionalTypeBean;
import com.system.technologyinformation.model.http.base.BaseResponseInfo;
import com.wjj.easy.easyandroid.mvp.EasyBasePresenter;

import java.util.List;

public interface UserProfessionalContract {
    interface View extends BaseView {
        void setProfessionalListResult(List<IdentificationInfosBean> professionalTypeListResult);
        void getProfessionalResult(String str);
        void setUserLoginResult(LoginBean loginBean);
    }

    interface Presenter extends EasyBasePresenter<View> {
        void getProfessionalListResult();
        void addProfessionalResult(String ckIds);
        void getUserLoginResult(String phone, String invCode, String smsCode, String token);
    }
}
