package com.system.technologyinformation.module.contract;

import com.system.technologyinformation.common.mvp.BaseView;
import com.system.technologyinformation.model.LoginBean;
import com.system.technologyinformation.model.ProvinceBean;
import com.system.technologyinformation.model.http.base.BaseResponseInfo;
import com.wjj.easy.easyandroid.mvp.EasyBasePresenter;

import java.io.File;
import java.util.List;

public interface UserPerfectInformationContract {
    interface View extends BaseView {
        void setUpdateUserInfoResult(String str);
        void setFileUploadResult(String str);
        void setUserLoginResult(LoginBean loginBean);
        void setProvinceOrCityInfoResult(List<ProvinceBean> provinceBeanList);
        void setUpdateUserInfoResult(LoginBean loginBean, int type);
    }

    interface Presenter extends EasyBasePresenter<View> {
        void getUpdateUserInfoResult(String nickName, String gender, String icon, String bornDate, Integer areaCode);
        void getFileUploadResult(File file);
        void getUserLoginResult(String phone, String invCode, String smsCode, String token);
        void getProvinceOrCityInfoResult();
        void getUpdateUserInfoResult(int id, int type);
    }
}
