package com.system.technologyinformation.common.ui.interfaces;

import com.wjj.easy.easyandroid.mvp.di.modules.ActivityModule;
import com.system.technologyinformation.common.di.ActivityCommonComponent;

/**
 * Created by wujiajun on 2017/11/1.
 */

public interface DiActivitySupport {
    ActivityCommonComponent getActivityComponent();

    ActivityModule getActivityModule();

    void initInject();
}
