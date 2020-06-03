package com.system.technologyinformation.module.contract;

import com.system.technologyinformation.common.mvp.BasePresenter;
import com.system.technologyinformation.common.mvp.BaseView;
import com.system.technologyinformation.model.LoginBean;
import com.system.technologyinformation.model.RechargesHistoryBean;
import com.wjj.easy.easyandroid.mvp.EasyBasePresenter;

import java.util.List;

public interface RechargesHistoryContract {

    interface View extends BaseView{

        void setUserLoginResult(LoginBean loginBean);
        void setRechargesHistoryResult(List<RechargesHistoryBean> rechargesHistory);

    }

    interface Presenter extends EasyBasePresenter<View>{

        void getUserLoginResult(String phone, String invCode,String smsCode, String token);
        void getRechargesHistoryResult(int pageNum, int pageSize);

    }
}
