package com.system.technologyinformation.module.contract;

import com.system.technologyinformation.common.mvp.BaseView;
import com.system.technologyinformation.model.ConstructionPlaceBean;
import com.system.technologyinformation.model.LoginBean;
import com.system.technologyinformation.model.ProvinceBean;
import com.wjj.easy.easyandroid.mvp.EasyBasePresenter;

import java.util.List;

public interface UserAddressContract {
    interface View extends BaseView {
        void setUserAddressResult(String str);
        void setProvinceOrCityInfoResult(List<ProvinceBean> provinceBeanList);
        void setConstructionPlaceInfoResult(List<ConstructionPlaceBean> constructionPlaceBeanList);
        void setUserLoginResult(LoginBean loginBean);
    }

    interface Presenter extends EasyBasePresenter<View> {
        void getUserAddressResult(int cpId);
        void getProvinceOrCityInfoResult();
        void getConstructionPlaceInfoResult();
        void getUserLoginResult(String phone, String invCode, String smsCode, String token);
    }
}
