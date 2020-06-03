package com.system.technologyinformation.module.contract;

import com.system.technologyinformation.common.mvp.BaseView;
import com.system.technologyinformation.model.HomeBannerBean;
import com.system.technologyinformation.model.HomeShoppingSpreeBean;
import com.system.technologyinformation.model.LoginBean;
import com.wjj.easy.easyandroid.mvp.EasyBasePresenter;

import java.util.List;

public interface PanicBuyingContract {
    interface View extends BaseView {
        void setHomeShoppingDataResult(List<HomeShoppingSpreeBean> homeShoppingDataResult);
        void setHomeBannerResult(List<HomeBannerBean> homeBannerBeanList);
        void setUserLoginResult(LoginBean loginBean);
    }

    interface Presenter extends EasyBasePresenter<View> {
        void getHomeShoppingDataResult(int pageNum, int pageSize);
        void getHomeBannerResult();
        void getUserLoginResult(String phone, String invCode, String smsCode, String token);
    }
}
