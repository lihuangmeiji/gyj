package com.system.technologyinformation.module.contract;

import com.system.technologyinformation.model.HomeDataBean;
import com.wjj.easy.easyandroid.mvp.EasyBasePresenter;
import com.system.technologyinformation.common.mvp.BaseView;
import com.system.technologyinformation.model.LoginBean;
import java.util.List;
public interface InformationConsultingContract {
    interface View extends BaseView {
        void setHomeDataResult(List<HomeDataBean> homeDataBeanList);
        void setUserLoginResult(LoginBean loginBean);
    }

    interface Presenter extends EasyBasePresenter<View> {
        void getHomeDataResult(int pageNum, int pageSize);
        void getUserLoginResult(String phone, String invCode, String smsCode, String token);
    }
}
