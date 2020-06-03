package com.system.technologyinformation.module.contract;

import com.system.technologyinformation.common.mvp.BaseView;
import com.system.technologyinformation.model.LoginBean;
import com.system.technologyinformation.model.StoreInfoBean;
import com.system.technologyinformation.model.StoreOrderBean;
import com.system.technologyinformation.model.UserRechargeAliBean;
import com.system.technologyinformation.model.UserRechargeWxBean;
import com.system.technologyinformation.model.UserWxPayBean;
import com.system.technologyinformation.model.http.base.BaseResponseInfo;
import com.umeng.socialize.sina.message.BaseResponse;
import com.wjj.easy.easyandroid.mvp.EasyBasePresenter;

import java.util.List;

public interface UserPayContract {
    interface View extends BaseView {
        void setStoreInfoResult(StoreInfoBean storeInfoResult);

        void setStoreOrderResult(StoreOrderBean storeOrderResult);

        void setUserRechargeWx(UserWxPayBean userWxPayBean);

        void setUserRechargeZfb(UserRechargeAliBean userRechargeAliBean);

        void setUserRechargeXj(String str);

        void setUserOrderCancelResult(String str);

        void setUserLoginResult(LoginBean loginBean);

        void setUserFreeOfChargeResult(String str);

        void setShopInfoResult(StoreInfoBean storeInfoBean);

        void setUpdateUserInfoResult(LoginBean loginBean);
    }

    interface Presenter extends EasyBasePresenter<View> {
        void getStoreInfoResult(int id);

        void getStoreOrderResult(int storeId, String price, String point);

        void getUserRechargeWx(int shopId, boolean usePoint, double price, double finalPrice);

        void getUserRechargeZfb(int shopId, boolean usePoint, double price, double finalPrice);

        void getUserRechargeXj(int orderId);

        void getUserOrderCancelResult(int orderId);

        void getUserLoginResult(String phone, String invCode, String smsCode, String token);

        void getUserFreeOfChargeResult(long orderId, Integer payType);

        void getShopInfo(String content);

        void getUpdateUserInfoResult(int id);
    }
}
