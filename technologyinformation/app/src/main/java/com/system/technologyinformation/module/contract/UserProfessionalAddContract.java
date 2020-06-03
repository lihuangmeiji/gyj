package com.system.technologyinformation.module.contract;

import com.system.technologyinformation.common.mvp.BaseView;
import com.system.technologyinformation.model.ProfessionalTypeBean;
import com.system.technologyinformation.model.http.base.BaseResponseInfo;
import com.wjj.easy.easyandroid.mvp.EasyBasePresenter;

import java.io.File;
import java.util.List;

public interface UserProfessionalAddContract {
    interface View extends BaseView {
        void setProfessionalTypeListResult(List<ProfessionalTypeBean> professionalTypeListResult);
        void setFileUploadResult(String str);
        void setProfessionalAddResult(String str);
        void setProfessionalUpdateResult(String str);
    }

    interface Presenter extends EasyBasePresenter<View> {
        void getProfessionalTypeListResult();
        void getFileUploadResult(File file);
        void getProfessionalAddResult(int type, int ckId, String carrerCertificate);
        void getProfessionalUpdateResult(int id, int type, int ckId, String carrerCertificate);
    }
}
