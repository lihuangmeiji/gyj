package com.system.technologyinformation.module.ui.users;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.system.technologyinformation.R;
import com.system.technologyinformation.common.ui.SimpleActivity;
import com.system.technologyinformation.utils.ActivityCollector;

import butterknife.BindView;
import butterknife.OnClick;

public class UserMessageDetailedActivity extends SimpleActivity {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_right)
    TextView iv_right;
    @BindView(R.id.tv_user_message_detcontent)
    TextView tv_user_message_detcontent;

    String mesContent;

    @Override
    protected int getContentView() {
        return R.layout.activity_user_message_detailed;
    }

    @Override
    protected void initEventAndData() {
        commonTheme();
        ActivityCollector.addActivity(this);
        toolbarTitle.setText("系统消息");
        toolbar.setNavigationIcon(R.mipmap.ic_return);
        iv_right.setVisibility(View.GONE);
        mesContent = getIntent().getStringExtra("mesContent");
        tv_user_message_detcontent.setText(mesContent);
    }

    @OnClick(R.id.toolbar)
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
    public void showErrorMsg(String msg,int code) {
        super.showErrorMsg(msg,code);
        if(code==-1){
            openLogin();
            return;
        }
        toast(msg);
    }

}
