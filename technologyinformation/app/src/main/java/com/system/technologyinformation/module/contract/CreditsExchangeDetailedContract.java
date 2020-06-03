package com.system.technologyinformation.module.contract;

import com.system.technologyinformation.common.mvp.BaseView;
import com.system.technologyinformation.model.ConfirmOrderBean;
import com.system.technologyinformation.model.HomeBannerBean;
import com.system.technologyinformation.model.HomeShoppingSpreeBean;
import com.system.technologyinformation.model.LoginBean;
import com.wjj.easy.easyandroid.mvp.EasyBasePresenter;

public interface CreditsExchangeDetailedContract {
    interface View extends BaseView {
        void setShoppingDetailedResult(HomeShoppingSpreeBean homeShoppingSpreeBean);
        void setUserLoginResult(LoginBean loginBean);
        void setOnlineSupermarketGoodsOreder(ConfirmOrderBean confirmOrderBean);
        void setUpdateUserInfoResult(LoginBean loginBean);
    }

    interface Presenter extends EasyBasePresenter<View> {
        void getShoppingDetailedResult(int id);
        void getUserLoginResult(String phone, String invCode, String smsCode, String token);
        void getOnlineSupermarketGoodsOreder(String jsonStr);
        void getUpdateUserInfoResult(int id);
    }
}
