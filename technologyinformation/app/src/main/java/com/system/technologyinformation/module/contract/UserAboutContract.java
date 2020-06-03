package com.system.technologyinformation.module.contract;

import com.system.technologyinformation.common.mvp.BaseView;
import com.wjj.easy.easyandroid.mvp.EasyBasePresenter;

public interface UserAboutContract {
    interface View extends BaseView {
        void setUserLogoutResult(String str);
    }

    interface Presenter extends EasyBasePresenter<View> {
        void getUserLogoutResult();
    }
}
