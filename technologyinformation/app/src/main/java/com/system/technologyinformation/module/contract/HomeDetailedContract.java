package com.system.technologyinformation.module.contract;

import com.system.technologyinformation.common.mvp.BaseView;
import com.system.technologyinformation.model.FeedbacksBean;
import com.system.technologyinformation.model.HomeDetailedBean;
import com.system.technologyinformation.model.http.base.BaseResponseInfo;
import com.wjj.easy.easyandroid.mvp.EasyBasePresenter;

import java.util.List;

public interface HomeDetailedContract {
    interface View extends BaseView {
        void setHomeDetailedResult(HomeDetailedBean homeDetailedBean);
        void setHomeDetailedAddVoteResult(String str);
        void setHomeDetailedAddFeedbackResult(String str, int position);
        void setHomeDetailedAddForwardResult(String str);
        void setHomeDetailedUpdateFeedbackResult(List<FeedbacksBean> feedbacksBeanList);
    }

    interface Presenter extends EasyBasePresenter<View> {
        void getHomeDetailedResult(int id, boolean countReadNum);
        void getHomeDetailedAddVoteResult(String ids, int viId);
        void getHomeDetailedAddFeedbackResult(int cId, int cftId, int position);
        void getHomeDetailedAddForwardResult(int id);
        void getHomeDetailedUpdateFeedbackResult(int cId);
    }
}
