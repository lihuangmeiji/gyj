package com.system.technologyinformation.module.contract;

import com.system.technologyinformation.common.mvp.BaseView;
import com.system.technologyinformation.model.AliyunPlayerSkinBean;
import com.system.technologyinformation.model.OnDemandBean;
import com.system.technologyinformation.model.http.base.BaseResponseInfo;
import com.wjj.easy.easyandroid.mvp.EasyBasePresenter;

public interface AliyunPlayerSkinContract {
    interface View extends BaseView {
        void setAliyunPlayerSkinInfoResult(AliyunPlayerSkinBean aliyunPlayerSkinBean);
        void setOnDemandListInfoResult(OnDemandBean onDemandBean);
        void setOnDemandDetailesInfoResult(BaseResponseInfo baseResponseInfo);
    }

    interface Presenter extends EasyBasePresenter<View> {
        void getAliyunPlayerSkinInfoResult();
        void getOnDemandListInfoResult(int vodPageNum, int vodPageSize);
        void getOnDemandDetailesInfoResult(String vodId);
    }
}
