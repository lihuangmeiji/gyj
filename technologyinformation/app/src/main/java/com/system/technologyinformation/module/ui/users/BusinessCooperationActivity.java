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

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class BusinessCooperationActivity extends SimpleActivity {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_right)
    TextView iv_right;
    @BindView(R.id.tv_lx_phone1)
    TextView tv_lx_phone1;
    @BindView(R.id.tv_lx_phone2)
    TextView tv_lx_phone2;

    @Override
    protected int getContentView() {
        return R.layout.activity_business_cooperation;
    }

    @Override
    protected void initEventAndData() {
        commonTheme();
        ActivityCollector.addActivity(this);
        toolbarTitle.setText("商务合作");
        toolbar.setNavigationIcon(R.mipmap.ic_return);
        iv_right.setVisibility(View.GONE);
        tv_lx_phone1.setText(TextUtil.FontHighlighting(getBaseContext(), "联系电话:", "186-0076-4294", R.style.integralRules2, R.style.integralRules1));
        tv_lx_phone2.setText(TextUtil.FontHighlighting(getBaseContext(), "联系电话:", "188-4266-4541", R.style.integralRules2, R.style.integralRules1));
    }

    @OnClick({R.id.toolbar,
            R.id.tv_lx_phone1,
            R.id.tv_lx_phone2
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar:
                finish();
                break;
            case R.id.tv_lx_phone1:
                try {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    Uri data = Uri.parse("tel:" + "18600764294");
                    intent.setData(data);
                    startActivity(intent);
                } catch (Exception e) {
                    toast("呼叫失败");
                }
                break;
            case R.id.tv_lx_phone2:
                try {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    Uri data = Uri.parse("tel:" + "18842664541");
                    intent.setData(data);
                    startActivity(intent);
                } catch (Exception e) {
                    toast("呼叫失败");
                }
                break;
        }
    }
}
