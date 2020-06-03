package com.system.technologyinformation.module.contract;

import com.system.technologyinformation.common.mvp.BaseView;
import com.system.technologyinformation.model.ConfirmOrderBean;
import com.system.technologyinformation.model.DeliveryBean;
import com.system.technologyinformation.model.LoginBean;
import com.system.technologyinformation.model.OnlineSupermarketBean;
import com.system.technologyinformation.model.http.base.BaseResponseInfo;
import com.wjj.easy.easyandroid.mvp.EasyBasePresenter;

import java.util.List;

public interface TableReservationContract {
    interface View extends BaseView {
        void setTableReservationList(List<OnlineSupermarketBean> onlineSupermarketGoodsList);
        void setOnlineSupermarketGoodsOreder(ConfirmOrderBean confirmOrderBean);
        void refreshUserTimeResult(BaseResponseInfo result);
        void setUserLoginResult(LoginBean loginBean);
        void setUpdateUserInfoResult(LoginBean loginBean);
    }

    interface Presenter extends EasyBasePresenter<View> {
        void getTableReservationList(String name, int pageSize, int pageNum);
        void getOnlineSupermarketGoodsOreder(String jsonStr);
        void refreshUserTime();
        void getUserLoginResult(String phone, String invCode, String smsCode, String token);
        void getUpdateUserInfoResult(int id);
    }
}
