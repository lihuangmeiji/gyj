package com.system.technologyinformation.module.ui.users;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.system.technologyinformation.R;
import com.system.technologyinformation.common.ui.BaseActivity;
import com.system.technologyinformation.common.ui.SimpleActivity;
import com.system.technologyinformation.model.IdentificationInfosBean;
import com.system.technologyinformation.utils.ActivityCollector;

import butterknife.BindView;
import butterknife.OnClick;

public class UserNameCertificationActivity extends SimpleActivity {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_right)
    TextView iv_right;

    @BindView(R.id.tv_code)
    TextView tv_code;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.iv_nc_identity_number_zm)
    ImageView iv_nc_identity_number_zm;
    @BindView(R.id.iv_nc_identity_number_fm)
    ImageView iv_nc_identity_number_fm;
    private IdentificationInfosBean nameIdentificationInfosBean = null;

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initEventAndData() {
        commonTheme();
        ActivityCollector.addActivity(this);
        toolbarTitle.setText("实名认证");
        toolbar.setNavigationIcon(R.mipmap.ic_return);
        iv_right.setVisibility(View.GONE);
        nameIdentificationInfosBean = (IdentificationInfosBean) getIntent().getSerializableExtra("namerz");
        if (nameIdentificationInfosBean != null) {
            //Glide.with(getBaseContext()).load(nameIdentificationInfosBean.getImgFront()).error(R.mipmap.userphotomr).into(iv_nc_identity_number_zm);
            //Glide.with(getBaseContext()).load(nameIdentificationInfosBean.getImgBack()).error(R.mipmap.userphotomr).into(iv_nc_identity_number_fm);
        }
    }


    @Override
    protected int getContentView() {
        return R.layout.activity_user_name_certification;
    }

    @OnClick({R.id.toolbar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar:
                finish();
                break;
        }
    }

    @Override
    public void showLoading() {
        super.showLoading();
    }

    @Override
    public void hiddenLoading() {
        super.hiddenLoading();
    }

    @Override
    public void showErrorMsg(String msg, int code) {
        super.showErrorMsg(msg, code);
        if (msg.contains("重置本地信息")) {
            openLogin();
            return;
        }
        toast(msg);
    }
}
