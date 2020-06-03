package com.system.technologyinformation.module.contract;

import com.system.technologyinformation.common.mvp.BaseView;
import com.system.technologyinformation.model.AdvertiseInfoBean;
import com.system.technologyinformation.model.LoginBean;
import com.system.technologyinformation.model.VersionShowBean;
import com.wjj.easy.easyandroid.mvp.EasyBasePresenter;

import java.util.List;

public interface MainContract {
    interface View extends BaseView {
        void setVersionResult(VersionShowBean versionShowBean);
        void setUpdateUserInfoResult(LoginBean loginBean);
        void addPushMessageTokenResult(String str);
        void setAdvertiseWindow(List<AdvertiseInfoBean> advertiseInfoBeans);
    }

    interface Presenter extends EasyBasePresenter<View> {
        void getVersionResult();
        void getUpdateUserInfoResult(int id);
        void addPushMessageToken(String xingeToken,String youmengToken,String jiguangToken);
        void getAdvertiseInfo();
    }
}
