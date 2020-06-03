package com.system.technologyinformation.module.contract;

import com.system.technologyinformation.common.mvp.BaseView;
import com.system.technologyinformation.model.DeliveryAddressBean;
import com.system.technologyinformation.model.LoginBean;
import com.system.technologyinformation.model.ProvinceBean;
import com.wjj.easy.easyandroid.mvp.EasyBasePresenter;

import java.util.List;

public interface DeliveryAddressContract {
    interface View extends BaseView {
        void addUserDeliveryAddressResult(String str);
        void updateUserDeliveryAddressResult(String str);
        void setUserDeliveryAddressResult(List<DeliveryAddressBean> userDeliveryAddressResult);
        void setUserLoginResult(LoginBean loginBean);
        void setProvinceOrCityInfoResult(List<ProvinceBean> provinceBeanList);
    }

    interface Presenter extends EasyBasePresenter<View> {
        void addUserDeliveryAddress(String name, String phone, Integer areaCode, String address);
        void updateUserDeliveryAddress(long id, String name, String phone, Integer areaCode, String address);
        void getUserDeliveryAddressResult();
        void getUserLoginResult(String phone, String invCode, String smsCode, String token);
        void getProvinceOrCityInfoResult();
    }
}
