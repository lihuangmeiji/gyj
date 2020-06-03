package com.system.technologyinformation.module.contract;

import com.system.technologyinformation.common.mvp.BaseView;
import com.system.technologyinformation.model.LoginBean;
import com.system.technologyinformation.model.UserConsumptionBean;
import com.system.technologyinformation.model.UserIntegralBean;
import com.wjj.easy.easyandroid.mvp.EasyBasePresenter;

import java.util.List;

public interface UserIntegralContract {
    interface View extends BaseView {
        void setUserIntegralListResult(List<UserIntegralBean> userIntegralListResult);
        void setUserLoginResult(LoginBean loginBean);
    }

    interface Presenter extends EasyBasePresenter<View> {
        void getUserIntegralListResult(int pageNum, int pageSize, String type);
        void getUserLoginResult(String phone, String invCode, String smsCode, String token);
    }
}
