package com.system.technologyinformation.module.contract;

import com.system.technologyinformation.common.mvp.BaseView;
import com.system.technologyinformation.model.CreditsExchangePayBean;
import com.system.technologyinformation.model.DeliveryAddressBean;
import com.system.technologyinformation.model.HomeShoppingSpreeBean;
import com.system.technologyinformation.model.LoginBean;
import com.system.technologyinformation.model.PayMethodBean;
import com.wjj.easy.easyandroid.mvp.EasyBasePresenter;

import java.util.List;

public interface CreditsExchangeConfirmContract {
    interface View extends BaseView {
        void setShoppingDetailedResult(HomeShoppingSpreeBean homeShoppingSpreeBean);
        void setUserLoginResult(LoginBean loginBean);
        void getUserMarketCreateOrderResult(CreditsExchangePayBean creditsExchangePayBean,String payType);
        void setUserDeliveryAddressResult(List<DeliveryAddressBean> userDeliveryAddressResult);
        void setMethodOfPayment(PayMethodBean payMethodBean);
        void setUserMarketOrderCancelResult(String str);
    }

    interface Presenter extends EasyBasePresenter<View> {
        void getShoppingDetailedResult(int id);
        void getUserLoginResult(String phone, String invCode, String smsCode, String token);
        void addUserMarketCreateOrderResult(String phone, String jsonStr, String name, String address,String orderNo,String payType);
        void getUserDeliveryAddressResult();
        void getMethodOfPayment();
        void getUserMarketOrderCancelResult(String orderNo);
    }
}
