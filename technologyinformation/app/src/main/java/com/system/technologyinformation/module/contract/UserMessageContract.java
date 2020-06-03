package com.system.technologyinformation.module.contract;
import com.system.technologyinformation.model.LoginBean;
import com.system.technologyinformation.common.mvp.BaseView;
import com.system.technologyinformation.model.UserIntegralBean;
import com.system.technologyinformation.model.UserMessage;
import com.wjj.easy.easyandroid.mvp.EasyBasePresenter;

import java.util.List;

public interface UserMessageContract {
    interface View extends BaseView {
        void setUserMessageListResult(List<UserMessage> userMessageListResult);
        void setUserLoginResult(LoginBean loginBean);
    }

    interface Presenter extends EasyBasePresenter<View> {
        void getUserMessageListResult(int pageNum, int pageSize);
        void getUserLoginResult(String phone, String invCode, String smsCode, String token);
    }
}
