package com.system.technologyinformation.module.contract;

import com.system.technologyinformation.common.mvp.BaseView;
import com.system.technologyinformation.model.LoginBean;
import com.system.technologyinformation.model.PayMethodBean;
import com.system.technologyinformation.model.RechargersTelephoneChargesBean;
import com.system.technologyinformation.model.RtcOrderBean;
import com.system.technologyinformation.model.UserIntegralBean;
import com.system.technologyinformation.model.UserRechargeAliBean;
import com.system.technologyinformation.model.UserRechargeWxBean;
import com.wjj.easy.easyandroid.mvp.EasyBasePresenter;

import java.util.List;

public interface RechargersTelephoneChargesContract {
    interface View extends BaseView {
        void setRechargersTelephoneChargesListResult(List<RechargersTelephoneChargesBean> rechargersTelephoneChargesListResult);
        void getRtcOrderResult(RtcOrderBean rtcOrderBean);
        void setUserLoginResult(LoginBean loginBean);
        void setRtcRechargeWx(UserRechargeWxBean userRechargeWxBean);
        void setRtcrRechargeZfb(String str);
        void setMethodOfPayment(PayMethodBean payMethodBean);
    }

    interface Presenter extends EasyBasePresenter<View> {
        void getRechargersTelephoneChargesListResult();
        void getUserLoginResult(String phone, String invCode, String smsCode, String token);
        void addRtcOrderResult(String phone, Integer price);
        void getRtcRechargeWx(int billId);
        void getRtcrRechargeZfb(int billId);
        void getMethodOfPayment();
    }
}
