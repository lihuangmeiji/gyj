package com.system.technologyinformation.module.contract;

import com.system.technologyinformation.common.mvp.BaseView;
import com.system.technologyinformation.model.ConvenientFunctionsBean;
import com.system.technologyinformation.model.FlickerScreenBean;
import com.system.technologyinformation.model.HomeBannerBean;
import com.system.technologyinformation.model.HomeConfigurationInformationBean;
import com.system.technologyinformation.model.HomeDataBean;
import com.system.technologyinformation.model.HomeShoppingSpreeBean;
import com.system.technologyinformation.model.LoginBean;
import com.system.technologyinformation.model.PagingInformationBean;
import com.system.technologyinformation.model.RewardBean;
import com.system.technologyinformation.model.StoreInfoBean;
import com.system.technologyinformation.model.http.base.BaseResponseInfo;
import com.wjj.easy.easyandroid.mvp.EasyBasePresenter;

import java.util.List;

public interface TabCategorizeFirstContract {
    interface View extends BaseView {
        void setHomeBannerResult(List<HomeBannerBean> homeBannerBeanList);
        void setHomeShoppingDataResult(List<HomeShoppingSpreeBean> homeShoppingDataResult);
        void setUpdateUserInfoResult(BaseResponseInfo baseResponseInfo);
        void setFunctionDivisionOne(List<ConvenientFunctionsBean> convenientFunctionsBeanList);
        void setFunctionDivisionTwo(BaseResponseInfo baseResponseInfo);
        void setUserLoginResult(LoginBean loginBean);
        void setUserSignAddResult(RewardBean rewardBean);
        void setShopInfoResult(StoreInfoBean storeInfoBean);
        void setHomeConfigurationInformation(List<HomeConfigurationInformationBean> homeConfigurationInformationBeanList);
        void setFlickerScreenResult(List<FlickerScreenBean> flickerScreenBeans);
    }

    interface Presenter extends EasyBasePresenter<View> {
        void getHomeBannerResult();
        void getHomeShoppingDataResult();
        void getUpdateUserInfoResult(int id);
        void getFunctionDivisionOne();
        void getFunctionDivisionTwo();
        void getUserLoginResult(String phone, String invCode, String smsCode, String token);
        void getUserSignAddResult();
        void getShopInfo(String content);
        void getHomeConfigurationInformation();
        void getFlickerScreenInfo();
    }
}
