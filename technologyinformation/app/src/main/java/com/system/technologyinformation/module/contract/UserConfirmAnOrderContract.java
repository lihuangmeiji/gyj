package com.system.technologyinformation.module.contract;

import com.system.technologyinformation.common.mvp.BaseView;
import com.system.technologyinformation.model.ConfirmOrderBean;
import com.system.technologyinformation.model.LoginBean;
import com.system.technologyinformation.model.PayMethodBean;
import com.system.technologyinformation.model.UserRechargeWxBean;
import com.system.technologyinformation.model.http.base.BaseResponseInfo;
import com.wjj.easy.easyandroid.mvp.EasyBasePresenter;

public interface UserConfirmAnOrderContract {
    interface View extends BaseView {
        void getDeliveryResult(BaseResponseInfo baseResponseInfo);
        void setUserConfirmAnOrderPayWx(UserRechargeWxBean userRechargeWxBean);
        void setUserConfirmAnOrderPayZfb(String str);
        void setUserLoginResult(LoginBean loginBean);
        void setOnlineSupermarketGoodsOreder(ConfirmOrderBean confirmOrderBean,int payType);
        void setMethodOfPayment(PayMethodBean payMethodBean);
    }

    interface Presenter extends EasyBasePresenter<View> {
        void addDeliveryResult(String dateTime, int storeId, int deliveryId);
        void getUserConfirmAnOrderPayWx(int deliveryId, int orderId, String dateTime);
        void getUserConfirmAnOrderPayZfb(int deliveryId, int orderId, String dateTime);
        void getUserLoginResult(String phone, String invCode, String smsCode, String token);
        void getOnlineSupermarketGoodsOreder(String jsonStr,String dateTime,int payType);
        void getMethodOfPayment();
    }
}
