package com.system.technologyinformation.module.contract;

import android.widget.ScrollView;

import com.system.technologyinformation.common.mvp.BaseView;
import com.system.technologyinformation.model.LoginBean;
import com.system.technologyinformation.model.QuestionBean;
import com.system.technologyinformation.model.UserMessage;
import com.system.technologyinformation.model.http.base.BaseResponseInfo;
import com.wjj.easy.easyandroid.mvp.EasyBasePresenter;

import java.io.File;
import java.util.List;

public interface QuestionMessageContract {
    interface View extends BaseView {
        void addquestionMessageResult(QuestionBean questionBean);
        void setFileUploadResult(String str);
        void refreshUserTimeResult(BaseResponseInfo result);
        void setUserLoginResult(LoginBean loginBean);
    }

    interface Presenter extends EasyBasePresenter<View> {
        void addquestionMessage(String img, String feedback, String groupCode);
        void getFileUploadResult(File file);
        void refreshUserTime();
        void getUserLoginResult(String phone, String invCode, String smsCode, String token);
    }
}
