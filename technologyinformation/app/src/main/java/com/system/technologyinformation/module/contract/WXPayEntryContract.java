package com.system.technologyinformation.module.contract;

import com.system.technologyinformation.common.mvp.BaseView;
import com.system.technologyinformation.model.http.base.BaseResponseInfo;
import com.wjj.easy.easyandroid.mvp.EasyBasePresenter;

public interface WXPayEntryContract {
    interface View extends BaseView {
        void setUserOrderCancelResult(BaseResponseInfo baseResponseInfo);

    }

    interface Presenter extends EasyBasePresenter<View> {
        void getUserOrderCancelResult(int orderId);
    }
}
