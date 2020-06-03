package com.system.technologyinformation.common.ui.interfaces;

import com.wjj.easy.easyandroid.mvp.di.modules.FragmentModule;
import com.system.technologyinformation.common.di.FragmentCommonComponent;

/**
 * 依赖注入Fragment模块接口
 * Created by wujiajun on 2017/11/1.
 */
public interface DiFragmentSupport {
    FragmentCommonComponent getFragmentComponent();

    FragmentModule getFragmentModule();

    void initInject();
}
