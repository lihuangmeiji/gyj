package com.system.technologyinformation.module.contract;

import com.wjj.easy.easyandroid.mvp.EasyBasePresenter;
import com.system.technologyinformation.common.mvp.BaseView;
import com.system.technologyinformation.model.ProductItemInfo;

import java.util.List;

/**
 * Created by wujiajun on 2017/10/18.
 */

public interface ProductContract {
    interface View extends BaseView {
        void callback(boolean success, List<ProductItemInfo> data);
    }

    interface Presenter extends EasyBasePresenter<View> {
        void getData(int limit, int size);
    }
}
