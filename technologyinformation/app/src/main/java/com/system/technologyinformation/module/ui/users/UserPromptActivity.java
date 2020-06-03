package com.system.technologyinformation.module.ui.users;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.system.technologyinformation.R;
import com.system.technologyinformation.common.ui.SimpleActivity;
import com.system.technologyinformation.module.ui.account.ForgotPasswordActivity;
import com.system.technologyinformation.module.ui.account.RegisterActivity;
import com.system.technologyinformation.utils.ActivityCollector;

import butterknife.BindView;
import butterknife.OnClick;

public class UserPromptActivity extends SimpleActivity {
    @BindView(R.id.tv_prompt_content)
    TextView tv_prompt_content;
    int redEnvelope;
    boolean isPrompt;
    @Override
    protected int getContentView() {
        return R.layout.activity_user_prompt;
    }

    @Override
    protected void initEventAndData() {
        redEnvelope=getIntent().getIntExtra("redEnvelope",0);
        isPrompt=getIntent().getBooleanExtra("isPrompt",false);
        if(redEnvelope>0){
            tv_prompt_content.setText("立即领红包（"+redEnvelope+"）");
        }else{
            if(isPrompt){
                tv_prompt_content.setText("去做任务");
            }else{
                tv_prompt_content.setText("明日再战");
            }
        }
    }

    @OnClick({R.id.iv_home_prompt_close, R.id.tv_prompt_content})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_home_prompt_close:
                finish();
                overridePendingTransition(0,0);
                break;
            case R.id.tv_prompt_content:
                ActivityCollector.finishAll();
                Intent intent = new Intent("completeTheTask");
                sendBroadcast(intent);
                finish();
                overridePendingTransition(0,0);
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
