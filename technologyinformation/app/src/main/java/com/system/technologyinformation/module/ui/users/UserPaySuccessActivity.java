package com.system.technologyinformation.module.ui.users;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.EmptyUtils;
import com.system.technologyinformation.R;
import com.system.technologyinformation.common.net.EnvConstant;
import com.system.technologyinformation.common.ui.BaseActivity;
import com.system.technologyinformation.common.ui.SimpleActivity;
import com.system.technologyinformation.utils.ActivityCollector;
import com.system.technologyinformation.utils.AudioUtils;
import com.system.technologyinformation.utils.PushMessagePlayUtil;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.OnClick;

public class UserPaySuccessActivity extends SimpleActivity {
    private static final String TAG = "UserPaySuccessActivity";
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_right)
    TextView iv_right;
    @BindView(R.id.rel_info)
    RelativeLayout relInfo;
    @BindView(R.id.tv_d_money)
    TextView tv_d_money;
    @BindView(R.id.tv_pay_money_jian)
    TextView tv_pay_money_jian;
    @BindView(R.id.tv_total_price)
    TextView tvTotalPrice;
    double dmoney;
    double jmoney;
    boolean ischeck;
    int paytype;
    double totalPrice;
    private Handler handler;

    @Override
    protected int getContentView() {
        return R.layout.activity_user_pay_success;
    }

    @Override
    protected void initEventAndData() {
        commonTheme();
        ActivityCollector.addActivity(this);
        toolbar.setNavigationIcon(R.mipmap.ic_return_video);
        iv_right.setVisibility(View.GONE);
        toolbarTitle.setText("支付详情");
        toolbarTitle.setTextColor(getBaseContext().getResources().getColor(R.color.white));
        toolbar.setBackgroundColor(getResources().getColor(R.color.color37));
        dmoney = getIntent().getDoubleExtra("dmoney", 0.0);
        jmoney = getIntent().getDoubleExtra("jmoney", 0.0);
        ischeck = getIntent().getBooleanExtra("ischeck", false);
        tv_d_money.setText("本次支付积分抵扣¥" + dmoney);
        tv_pay_money_jian.setText("本次支付随机减免¥" + jmoney);
        totalPrice = getIntent().getDoubleExtra("totalPrice", 0.0);
        tvTotalPrice.setText("¥ " + totalPrice);
        paytype = getIntent().getIntExtra("paytype", 0);
        if (dmoney > 0) {
            tv_d_money.setVisibility(View.VISIBLE);
        } else {
            tv_d_money.setVisibility(View.GONE);
        }
        if (jmoney > 0) {
            tv_pay_money_jian.setVisibility(View.VISIBLE);
        } else {
            tv_pay_money_jian.setVisibility(View.GONE);
        }
        if (paytype == 3) {
            handler = new Handler(getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    try {
                        synchronized (EnvConstant.PUSH_MESSAGE) {
                            Log.i(TAG, "-----播放开始-----");
                            AudioUtils.getInstance().init(getApplicationContext()); //初始化语音对象
                            if ((dmoney + jmoney) > 0) {
                                AudioUtils.getInstance().speakText("工友家付款" + totalPrice + "元，优惠" + (dmoney + jmoney) + "元"); //播放语音
                            } else {
                                AudioUtils.getInstance().speakText("工友家付款" + totalPrice + "元"); //播放语音
                            }
                            Thread.sleep(8000);
                            Log.i(TAG, "-----播放完成-----");
                            EnvConstant.PUSH_MESSAGE.notify();
                        }
                    } catch (Exception e) {
                        Log.i(TAG, "-----播放异常-----");
                    }
                }
            }, 500);
        }
    }

    @OnClick({R.id.toolbar
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar:
                setResult(1);
                finish();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                setResult(1);
                finish();
                return true;
        }
        return super.onKeyDown(keyCode, event);
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
        toast(msg);
    }
}
