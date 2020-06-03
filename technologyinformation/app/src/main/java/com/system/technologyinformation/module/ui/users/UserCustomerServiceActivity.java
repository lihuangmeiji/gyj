package com.system.technologyinformation.module.ui.users;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.system.technologyinformation.R;
import com.system.technologyinformation.common.ui.SimpleActivity;
import com.system.technologyinformation.utils.ActivityCollector;
import com.system.technologyinformation.utils.TextUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class UserCustomerServiceActivity extends SimpleActivity {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_right)
    TextView iv_right;
    @BindView(R.id.tv_cs_phone)
    TextView tvCsPhone;
    @BindView(R.id.tv_cs_call_phone)
    TextView tvCsCallPhone;
    @BindView(R.id.tv_kf_y)
    TextView tvKfy;
    @BindView(R.id.tv_kf_e)
    TextView tvKfe;
    @Override
    protected int getContentView() {
        return R.layout.activity_user_customer_service;
    }

    @Override
    protected void initEventAndData() {
        commonTheme();
        ActivityCollector.addActivity(this);
        toolbarTitle.setText("客服热线");
        toolbar.setNavigationIcon(R.mipmap.ic_return);
        iv_right.setVisibility(View.GONE);
        tvKfy.setText(TextUtil.FontHighlighting(getBaseContext(),"工作人员一号：","15580995485",R.style.tv_kf1,R.style.tv_kf2));
        tvKfe.setText(TextUtil.FontHighlighting(getBaseContext(),"工作人员二号：","15980452566",R.style.tv_kf1,R.style.tv_kf2));
    }

    @OnClick({R.id.toolbar,
            R.id.tv_cs_call_phone,
            R.id.tv_kf_y,
            R.id.tv_kf_e
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar:
                finish();
                break;
            case R.id.tv_cs_call_phone:
                try {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    Uri data = Uri.parse("tel:" + tvCsPhone.getText().toString().trim().replace("-",""));
                    intent.setData(data);
                    startActivity(intent);
                }catch (Exception e){
                    toast("呼叫失败");
                }
                break;
            case R.id.tv_kf_y:
                try {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    Uri data = Uri.parse("tel:15580995485");
                    intent.setData(data);
                    startActivity(intent);
                }catch (Exception e){
                    toast("呼叫失败");
                }
                break;
            case R.id.tv_kf_e:
                try {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    Uri data = Uri.parse("tel:15980452566");
                    intent.setData(data);
                    startActivity(intent);
                }catch (Exception e){
                    toast("呼叫失败");
                }
                break;
        }
    }

    @Override
    public void toast(String msg) {
        super.toast(msg);
    }

    @Override
    public void showLoading() {
        super.showLoading();
    }

    @Override
    public void hiddenLoading() {
        super.hiddenLoading();
    }
}
