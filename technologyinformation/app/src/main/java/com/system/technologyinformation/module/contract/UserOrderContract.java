package com.system.technologyinformation.module.contract;

import com.system.technologyinformation.common.mvp.BaseView;
import com.system.technologyinformation.model.DeliveryBean;
import com.system.technologyinformation.model.LoginBean;
import com.system.technologyinformation.model.UserOrderBean;
import com.system.technologyinformation.model.UserRechargeWxBean;
import com.wjj.easy.easyandroid.mvp.EasyBasePresenter;

import java.util.List;

public interface UserOrderContract {
    interface View extends BaseView {
        void setUserOrderListResult(List<UserOrderBean> userOrderListResult);
        void setOnlineSupermarketGoodsOreder(List<DeliveryBean> deliveryBeanList);
        void setUserConfirmAnOrderPayWx(UserRechargeWxBean userRechargeWxBean);
        void setUserConfirmAnOrderPayZfb(String str);
        void setUserLoginResult(LoginBean loginBean);
        void addUserOrderRefundsResult(String str);
    }

    interface Presenter extends EasyBasePresenter<View> {
        void getUserOrderListResult(int pageNum, int pageSize, Integer status);
        void getOnlineSupermarketGoodsOreder(String jsonStr);
        void getUserConfirmAnOrderPayWx(int deliveryId, int orderId, String dateTime);
        void getUserConfirmAnOrderPayZfb(int deliveryId, int orderId, String dateTime);
        void getUserLoginResult(String phone, String invCode, String smsCode, String token);
        void addUserOrderRefunds(String reasons, long orderId);
    }
}
