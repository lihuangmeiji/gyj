package com.system.technologyinformation.module.contract;

import com.system.technologyinformation.common.mvp.BaseView;
import com.system.technologyinformation.model.GroupInfoBean;
import com.system.technologyinformation.model.http.base.BaseResponseInfo;
import com.wjj.easy.easyandroid.mvp.EasyBasePresenter;

import java.util.List;

public interface TabCategorizeFiveContract {
    interface View extends BaseView {
        void setUserGroupInfo(List<GroupInfoBean> groupInfoBeanList);
        void refreshUserTimeResult(BaseResponseInfo result);
    }

    interface Presenter extends EasyBasePresenter<View> {
        void getUserGroupInfo();
        void refreshUserTime();
    }
}
