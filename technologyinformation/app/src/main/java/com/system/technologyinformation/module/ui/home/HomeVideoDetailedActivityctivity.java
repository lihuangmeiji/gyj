package com.system.technologyinformation.module.ui.home;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.system.technologyinformation.R;
import com.system.technologyinformation.common.ui.BaseActivity;
import com.system.technologyinformation.common.ui.SimpleActivity;

public class HomeVideoDetailedActivityctivity extends SimpleActivity {


    @Override
    protected int getContentView() {
        return R.layout.activity_home_video_detailed_activityctivity;
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initEventAndData() {
        commonTheme();
    }
}
