package com.system.technologyinformation.module.contract;

import com.system.technologyinformation.common.mvp.BaseView;
import com.system.technologyinformation.model.GroupUserInfoBean;
import com.system.technologyinformation.model.http.base.BaseResponseInfo;
import com.wjj.easy.easyandroid.mvp.EasyBasePresenter;

import java.util.List;

public interface GroupDetailsContract {
    interface View extends BaseView {
        void refreshUserTimeResult(BaseResponseInfo result);
        void loadGroupUserInforResult(List<GroupUserInfoBean> object);
    }

    interface Presenter extends EasyBasePresenter<View> {
        void refreshUserTime();
        void loadGroupUserInfo(String groupId);
    }
}
