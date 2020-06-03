package com.system.technologyinformation.module.contract;

import com.system.technologyinformation.common.mvp.BaseView;
import com.system.technologyinformation.model.IdentificationInfosBean;
import com.system.technologyinformation.model.UserNameInfoBean;
import com.system.technologyinformation.model.http.base.BaseResponseInfo;
import com.wjj.easy.easyandroid.mvp.EasyBasePresenter;

import java.io.File;

public interface UserNameCertificationAddContract {
    interface View extends BaseView {
        void setFileUploadResult(String str, final int type);

        void setNameCertificationAddResult(String str);

        //作废
        void setNameCertificationUpdateResult(String str);


        void setNameCertificationInfoResult(UserNameInfoBean userNameInfoBean);
    }

    interface Presenter extends EasyBasePresenter<View> {
        void getFileUploadResult(File file, final int type);

        void getNameCertificationAddResult(int type, String realname, String idCode, String imgFront, String imgBack,String cardInfo,String validityPeriod);

        //作废
        void getNameCertificationUpdateResult(int id, int type, String realname, String idCode, String imgFront, String imgBack);


        void getNameCertificationInfoResult();
    }

}
