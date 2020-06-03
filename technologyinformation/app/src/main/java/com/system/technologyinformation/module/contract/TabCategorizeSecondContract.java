package com.system.technologyinformation.module.contract;

import com.system.technologyinformation.common.mvp.BaseView;
import com.system.technologyinformation.model.DailyMissionBean;
import com.system.technologyinformation.model.LoginBean;
import com.system.technologyinformation.model.RewardBean;
import com.system.technologyinformation.model.SignBean;
import com.system.technologyinformation.model.UserIntegralBean;
import com.system.technologyinformation.model.http.base.BaseResponseInfo;
import com.wjj.easy.easyandroid.mvp.EasyBasePresenter;

import java.util.Calendar;
import java.util.List;

public interface TabCategorizeSecondContract {
    interface View extends BaseView {
        void setUserSignAddResult(RewardBean rewardBean);
        void setUpdateUserInfoResult(LoginBean loginBean);
        void setUserSignDayInfoResult(List<SignBean> signDayInfoResult);
        void setUserSignMonthInfoResult(List<String> signMonthInfoResult, int type, Calendar date);
        void getUserOpenRewardInfoResult(RewardBean rewardBean);
        void setUserSignRepairAddResult(RewardBean rewardBean);
        void setUserLoginResult(LoginBean loginBean);
        void setDailyMissionListResult(List<DailyMissionBean> dailyMissionBeanList);
        void setUserShareContentResult(String str);
        void getUserShareResult(String str);
    }

    interface Presenter extends EasyBasePresenter<View> {
        void getUserSignAddResult();
        void getUpdateUserInfoResult(int id);
        void getUserSignDayInfoResult(String date);
        void getUserSignMonthInfoResult(Calendar date, int type);
        void getUserOpenRewardInfoResult(int type);
        void getUserSignRepairAddResult(String checkinDate);
        void getUserLoginResult(String phone, String invCode, String smsCode, String token);
        void getDailyMissionListResult();
        void getUserShareContentResult();
        void addUserShareResult();
    }
}
