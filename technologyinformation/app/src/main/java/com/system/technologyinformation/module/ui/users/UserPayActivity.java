package com.system.technologyinformation.module.ui.users;

import android.app.ActionBar;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.system.technologyinformation.R;
import com.system.technologyinformation.common.ui.BaseActivity;
import com.system.technologyinformation.common.ui.SimpleActivity;
import com.system.technologyinformation.model.LoginBean;
import com.system.technologyinformation.model.PayOrderBean;
import com.system.technologyinformation.model.StoreInfoBean;
import com.system.technologyinformation.model.StoreOrderBean;
import com.system.technologyinformation.model.UserRechargeAliBean;
import com.system.technologyinformation.model.UserRechargeWxBean;
import com.system.technologyinformation.model.UserWxPayBean;
import com.system.technologyinformation.model.http.base.BaseResponseInfo;
import com.system.technologyinformation.module.contract.UserPayContract;
import com.system.technologyinformation.module.presenter.UserPayPresenter;
import com.system.technologyinformation.utils.ActivityCollector;
import com.system.technologyinformation.utils.ArithmeticUtils;
import com.system.technologyinformation.utils.TextUtil;
import com.system.technologyinformation.widget.CircleImageView;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import sign.AuthResult;
import sign.PayResult;

public class UserPayActivity extends BaseActivity<UserPayPresenter> implements UserPayContract.View {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_right)
    TextView iv_right;
    @BindView(R.id.iv_store_ico)
    CircleImageView iv_store_ico;
    @BindView(R.id.tv_store_name)
    TextView tv_store_name;
    @BindView(R.id.tv_money_bs)
    TextView tvMoneyBs;
    @BindView(R.id.et_pay_money)
    EditText et_pay_money;
    @BindView(R.id.tv_pay_money)
    TextView tv_pay_money;
    @BindView(R.id.cb_pay_select)
    CheckBox cbPaySelect;
    @BindView(R.id.tv_pay_wx)
    TextView tvPayWx;
    @BindView(R.id.tv_pay_zfb)
    TextView tvPayZfb;
    @BindView(R.id.tv_pay_point)
    TextView tvPayPoint;
    @BindView(R.id.tv_money_bs_left)
    TextView tvMoneyBsLeft;
    @BindView(R.id.rel_pay_point)
    RelativeLayout relPayPoint;
    int storeId = 0;
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
    //微信支付
    private IWXAPI api;

    int orderId = 0;
    StoreOrderBean storeOrderResult;
    private StoreInfoBean storeInfoResult;
    NumberFormat nf;
    double mtotalPrice;
    int operation = 0;
    Double userIntegral = 0.0;
    double jmoney = 0.0;

    PayOrderBean payOrderBean;

    @Override
    protected int getContentView() {
        return R.layout.activity_user_pay;
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initEventAndData() {
        commonTheme();
        ActivityCollector.addActivity(this);
        nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(2);
        addWxPlayReceiver();
        toolbarTitle.setText("支付");
        toolbar.setNavigationIcon(R.mipmap.ic_return);
        iv_right.setVisibility(View.GONE);
        mtotalPrice = getIntent().getDoubleExtra("mtotalPrice", 0.00);
        operation = 1;
        api = WXAPIFactory.createWXAPI(this, null);
        api.registerApp("wxb2911332cf274f1f");
        storeInfoResult = (StoreInfoBean) getIntent().getSerializableExtra("storeInfoBean");
        if (storeInfoResult != null) {
            storeId = storeInfoResult.getId();
            Glide.with(getBaseContext()).load(storeInfoResult.getIcon()).error(R.mipmap.userphotomr).into(iv_store_ico);
            tv_store_name.setText(storeInfoResult.getName());
            tvPayPoint.setText("使用积分抵扣");
            if (storeInfoResult.isAliPay()) {
                tvPayZfb.setVisibility(View.VISIBLE);
            } else {
                tvPayZfb.setVisibility(View.GONE);
            }
            if (storeInfoResult.isWxPay()) {
                tvPayWx.setVisibility(View.VISIBLE);
            } else {
                tvPayWx.setVisibility(View.GONE);
            }
            if(storeInfoResult.isUsePoint()){
                cbPaySelect.setChecked(true);
                relPayPoint.setVisibility(View.VISIBLE);
            }else{
                relPayPoint.setVisibility(View.GONE);
            }
        }
        getPresenter().getUpdateUserInfoResult(0);
        et_pay_money.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().length() > 0) {
                    tvMoneyBs.setVisibility(View.GONE);
                    tvMoneyBsLeft.setVisibility(View.VISIBLE);
                    DecimalFormat decimalFormat = new DecimalFormat("0.00");
                    if (storeInfoResult != null && storeInfoResult.getPointPercentage() > 0.0 && cbPaySelect.isChecked() && Double.valueOf(s.toString().trim()) >= 0.1) {
                        userIntegral = ArithmeticUtils.div(ArithmeticUtils.mul(Double.valueOf(s.toString().trim()), storeInfoResult.getPointPercentage()), storeInfoResult.getPointConvert(), 1);
                        if (login != null && login.getPoint() > 0) {
                            if (Double.valueOf(userIntegral).intValue() > login.getPoint()) {
                                tvPayPoint.setText("使用积分" + login.getPoint() + "抵扣" + ArithmeticUtils.mul(login.getPoint(), storeInfoResult.getPointConvert(), 2));
                                tv_pay_money.setText("¥" + ArithmeticUtils.sub(Double.valueOf(s.toString().trim()).doubleValue(), ArithmeticUtils.mul(login.getPoint(), storeInfoResult.getPointConvert(), 2)));
                            } else {
                                tvPayPoint.setText("使用积分" + Double.valueOf(userIntegral).intValue() + "抵扣" + ArithmeticUtils.mul(Double.valueOf(s.toString().trim()), storeInfoResult.getPointPercentage(), 2));
                                tv_pay_money.setText("¥" + ArithmeticUtils.mul(Double.valueOf(s.toString().trim()), (1 - storeInfoResult.getPointPercentage()), 2));
                            }
                        } else {
                            tvPayPoint.setText("使用积分抵扣");
                            tv_pay_money.setText("¥" + decimalFormat.format(Double.valueOf(s.toString().trim())));
                        }
                    } else {
                        tvPayPoint.setText("使用积分抵扣");
                        tv_pay_money.setText("¥" + decimalFormat.format(Double.valueOf(s.toString().trim())));
                        //et_pay_money.setText("¥" + s.toString().trim());
                    }
                } else {
                    tvMoneyBs.setVisibility(View.VISIBLE);
                    tvMoneyBsLeft.setVisibility(View.GONE);
                    tvPayPoint.setText("使用积分抵扣");
                    tv_pay_money.setText("¥" + 0.00);
                    //et_pay_money.setText("");
                }
            }
        });
    }

    @OnClick({R.id.toolbar,
            R.id.tv_pay_wx,
            R.id.tv_pay_zfb,
            R.id.cb_pay_select
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar:
                finish();
                break;
            case R.id.tv_pay_wx:
                if (EmptyUtils.isEmpty(et_pay_money.getText().toString().trim())) {
                    toast("请输入支付金额");
                    return;
                }
                showLoading();
                operation = 2;
                getPresenter().getUserRechargeWx(storeId, cbPaySelect.isChecked(), Double.valueOf(et_pay_money.getText().toString().trim()), Double.valueOf(tv_pay_money.getText().toString().replace("¥", "")));
                break;
            case R.id.tv_pay_zfb:
                if (EmptyUtils.isEmpty(et_pay_money.getText().toString().trim())) {
                    toast("请输入支付金额");
                    return;
                }
                showLoading();
                operation = 2;
                getPresenter().getUserRechargeZfb(storeId, cbPaySelect.isChecked(), Double.valueOf(et_pay_money.getText().toString().trim()), Double.valueOf(tv_pay_money.getText().toString().replace("¥", "")));
                break;
            case R.id.cb_pay_select:
                if (!EmptyUtils.isEmpty(et_pay_money.getText().toString().trim())) {
                    tvMoneyBs.setVisibility(View.GONE);
                    tvMoneyBsLeft.setVisibility(View.VISIBLE);
                    DecimalFormat decimalFormat = new DecimalFormat("0.00");
                    if (storeInfoResult != null && storeInfoResult.getPointPercentage() > 0.0 && cbPaySelect.isChecked() && Double.valueOf(et_pay_money.getText().toString().trim()) >= 0.1) {
                        userIntegral = ArithmeticUtils.div(ArithmeticUtils.mul(Double.valueOf(et_pay_money.getText().toString().trim()), storeInfoResult.getPointPercentage()), storeInfoResult.getPointConvert(), 1);
                        if (login != null && login.getPoint() > 0) {
                            if (Double.valueOf(userIntegral).intValue() > login.getPoint()) {
                                tvPayPoint.setText("使用积分" + login.getPoint() + "抵扣" + ArithmeticUtils.mul(login.getPoint(), storeInfoResult.getPointConvert(), 2));
                                tv_pay_money.setText("¥" + ArithmeticUtils.sub(Double.valueOf(et_pay_money.getText().toString().trim()).doubleValue(), ArithmeticUtils.mul(login.getPoint(), storeInfoResult.getPointConvert(), 2)));
                            } else {
                                tvPayPoint.setText("使用积分" + Double.valueOf(userIntegral).intValue() + "抵扣" + ArithmeticUtils.mul(Double.valueOf(et_pay_money.getText().toString().trim()), storeInfoResult.getPointPercentage(), 2));
                                tv_pay_money.setText("¥" + ArithmeticUtils.mul(Double.valueOf(et_pay_money.getText().toString().trim()), (1 - storeInfoResult.getPointPercentage()), 2));
                            }
                        } else {
                            tvPayPoint.setText("使用积分抵扣");
                            tv_pay_money.setText("¥" + decimalFormat.format(Double.valueOf(et_pay_money.getText().toString().trim())));
                        }
                    } else {
                        tvPayPoint.setText("使用积分抵扣");
                        tv_pay_money.setText("¥" + decimalFormat.format(Double.valueOf(et_pay_money.getText().toString().trim())));
                        //et_pay_money.setText("¥" + s.toString().trim());
                    }
                }
                break;
        }
    }

    @Override
    public void showErrorMsg(String msg, int code) {
        super.showErrorMsg(msg, code);
        hiddenLoading();
        if (code == -1) {
            if (login != null && showLog == false) {
                showLog = true;
                getPresenter().getUserLoginResult(login.getPhone(), "", "", login.getToken());
            } else {
                openLogin();
            }
            return;
        } else if (code == -10) {
            return;
        }
        toast(msg);
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
    public void setStoreInfoResult(StoreInfoBean storeInfoResult) {
        if (storeInfoResult != null) {
            Glide.with(getBaseContext()).load(storeInfoResult.getIcon()).error(R.mipmap.userphotomr).into(iv_store_ico);
            tv_store_name.setText(storeInfoResult.getName());
            tvPayPoint.setText("使用积分抵扣");
            if (storeInfoResult.isAliPay()) {
                tvPayZfb.setVisibility(View.VISIBLE);
            } else {
                tvPayZfb.setVisibility(View.GONE);
            }
            if (storeInfoResult.isWxPay()) {
                tvPayWx.setVisibility(View.VISIBLE);
            } else {
                tvPayWx.setVisibility(View.GONE);
            }
            this.storeInfoResult = storeInfoResult;
            cbPaySelect.setChecked(true);
        }
    }

    @Override
    public void setStoreOrderResult(StoreOrderBean storeOrderResult) {
        if (storeOrderResult != null) {
            this.storeOrderResult = storeOrderResult;
        } else {
            toast("预览订单生成失败");
        }
    }

    @Override
    public void setUserRechargeWx(UserWxPayBean userWxPayBean) {
        hiddenLoading();
        if (userWxPayBean != null && userWxPayBean.getOrder() != null) {
            orderId = userWxPayBean.getOrder().getId();
            payOrderBean = userWxPayBean.getOrder();
            if (userWxPayBean.getOrder().getFinalPrice() == 0 || userWxPayBean.getOrder().getFinalPrice() == 0.0 || userWxPayBean.getOrder().getFinalPrice() == 0.00) {
                new FreeOfChargeView(getBaseContext(), tv_pay_money, userWxPayBean.getOrder().getId(), 1);
            } else if (userWxPayBean.getOrder().getRemissionPrice() != 0 || userWxPayBean.getOrder().getRemissionPrice() != 0.0 || userWxPayBean.getOrder().getRemissionPrice() != 0.00) {
                jmoney = userWxPayBean.getOrder().getRemissionPrice();
                new FeeExemptionsView(getBaseContext(), tv_pay_money, userWxPayBean.getOrder().getRemissionPrice(), 1, userWxPayBean.getOrder().getFinalPrice(), null, userWxPayBean.getWxPayRequest());
            } else {
                if (userWxPayBean.getWxPayRequest() != null) {
                    PayReq req = new PayReq();
                    //req.appId = "wxf8b4f85f3a794e77";  // 测试用appId
                    req.appId = userWxPayBean.getWxPayRequest().getAppId();
                    req.partnerId = "1528772321";
                    req.prepayId = userWxPayBean.getWxPayRequest().getPrypayId();
                    req.nonceStr = userWxPayBean.getWxPayRequest().getNonceStr();
                    req.timeStamp = userWxPayBean.getWxPayRequest().getTimeStamp();
                    req.packageValue = userWxPayBean.getWxPayRequest().getPackageX();
                    req.sign = userWxPayBean.getWxPayRequest().getPaySign();
                    api.sendReq(req);
                } else {
                    toast("微信支付订单生成失败！");
                }
            }
        }
    }

    @Override
    public void setUserRechargeZfb(UserRechargeAliBean userRechargeAliBean) {
        hiddenLoading();
        if (userRechargeAliBean != null && userRechargeAliBean.getOrder() != null) {
            orderId = userRechargeAliBean.getOrder().getId();
            payOrderBean = userRechargeAliBean.getOrder();
            if (userRechargeAliBean.getOrder().getFinalPrice() == 0 || userRechargeAliBean.getOrder().getFinalPrice() == 0.0 || userRechargeAliBean.getOrder().getFinalPrice() == 0.00) {
                new FreeOfChargeView(getBaseContext(), tv_pay_money, userRechargeAliBean.getOrder().getId(), 0);
            } else if (userRechargeAliBean.getOrder().getRemissionPrice() != 0 || userRechargeAliBean.getOrder().getRemissionPrice() != 0.0 || userRechargeAliBean.getOrder().getRemissionPrice() != 0.00) {
                jmoney = userRechargeAliBean.getOrder().getRemissionPrice();
                new FeeExemptionsView(getBaseContext(), tv_pay_money, userRechargeAliBean.getOrder().getRemissionPrice(), 0, userRechargeAliBean.getOrder().getFinalPrice(), userRechargeAliBean.getAliOrderNo(), null);
            } else {
                pay(userRechargeAliBean.getAliOrderNo());
            }
        } else {
            toast("支付宝订单生成失败");
        }
    }

    @Override
    public void setUserRechargeXj(String str) {
        if (str != null) {
            finish();
            Intent intent = new Intent(UserPayActivity.this, UserPaySuccessActivity.class);
            intent.putExtra("paytype", 3);
            startActivity(intent);
        } else {
            toast("现金支付订单生成失败");
        }
    }

    @Override
    public void setUserOrderCancelResult(String str) {

    }

    @Override
    public void setUserLoginResult(LoginBean loginBean) {
        if (loginBean != null) {
            clearUserInfo();
            SPUtils spUtils = new SPUtils("saveUser");
            spUtils.put("userInfo", new Gson().toJson(loginBean));
            loadUserInfo();
          /*  if (operation == 1) {
                getPresenter().getStoreInfoResult(storeId);
            } else {
                toast("网络异常，请重新操作！");
            }*/
        } else {
            openLogin();
        }
    }

    @Override
    public void setUserFreeOfChargeResult(String str) {
        hiddenLoading();
        Intent intent = new Intent(UserPayActivity.this, UserPaySuccessActivity.class);
        if (storeInfoResult != null && payOrderBean != null) {
            if (payOrderBean.getPoint() == 0) {
                intent.putExtra("dmoney", 0.00);
            } else {
                intent.putExtra("dmoney", ArithmeticUtils.mul(payOrderBean.getPoint(), storeInfoResult.getPointConvert(), 2));
            }
            intent.putExtra("jmoney", payOrderBean.getRemissionPrice());
            intent.putExtra("totalPrice", payOrderBean.getTotalPrice());
            intent.putExtra("storeId", storeId);
            intent.putExtra("paytype", 3);
        }
        startActivity(intent);
        finish();
    }

    @Override
    public void setShopInfoResult(StoreInfoBean storeInfoBean) {
        if (storeInfoBean != null) {
            Glide.with(getBaseContext()).load(storeInfoBean.getIcon()).error(R.mipmap.userphotomr).into(iv_store_ico);
            tv_store_name.setText(storeInfoBean.getName());
            tvPayPoint.setText("使用积分抵扣");
            if (storeInfoBean.isAliPay()) {
                tvPayZfb.setVisibility(View.VISIBLE);
            } else {
                tvPayZfb.setVisibility(View.GONE);
            }
            if (storeInfoBean.isWxPay()) {
                tvPayWx.setVisibility(View.VISIBLE);
            } else {
                tvPayWx.setVisibility(View.GONE);
            }
            this.storeInfoResult = storeInfoBean;
            cbPaySelect.setChecked(true);
        } else {
            toast("商铺信息获取失败");
        }
    }

    @Override
    public void setUpdateUserInfoResult(LoginBean loginBean) {
        if (loginBean != null) {
            clearUserInfo();
            SPUtils spUtils = new SPUtils("saveUser");
            spUtils.put("userInfo", new Gson().toJson(loginBean));
            loadUserInfo();
        } else {
            clearUserInfo();
            openLogin();
        }
    }


    BroadcastReceiver broadcastReceiver;

    private void addWxPlayReceiver() {
        broadcastReceiver = new BroadcastReceiver()

        {
            @Override
            public void onReceive(Context context, Intent intent) {
                int type = intent.getIntExtra("type", 2);
                if (type == 1) {
                    finish();
                    intent = new Intent(UserPayActivity.this, UserPaySuccessActivity.class);
                    if (storeInfoResult != null && payOrderBean != null) {
                        if (payOrderBean.getPoint() == 0) {
                            intent.putExtra("dmoney", 0.00);
                        } else {
                            intent.putExtra("dmoney", ArithmeticUtils.mul(payOrderBean.getPoint(), storeInfoResult.getPointConvert(), 2));
                        }
                        intent.putExtra("jmoney", payOrderBean.getRemissionPrice());
                        intent.putExtra("totalPrice", payOrderBean.getTotalPrice());
                        intent.putExtra("storeId", storeId);
                        intent.putExtra("paytype", 3);
                    }
                    startActivity(intent);
                } else if (type == 3) {
                    getPresenter().getUserOrderCancelResult(orderId);
                } else {
                    toast("支付失败");
                }
            }
        };
        IntentFilter intentToReceiveFilter = new IntentFilter();
        intentToReceiveFilter.addAction("wxplay");
        registerReceiver(broadcastReceiver, intentToReceiveFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
        }
    }


    /**
     * call alipay sdk pay. 调用SDK支付
     */
    public void pay(final String ranking) {
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(UserPayActivity.this);
                String rankings = ranking;
                if (rankings == null) {
                } else {
                    Map<String, String> result = alipay.payV2(rankings, true);
                    Message msg = new Message();
                    msg.what = SDK_PAY_FLAG;
                    msg.obj = result;
                    mHandler.sendMessage(msg);
                }


            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */

                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        finish();
                        Intent intent = new Intent(UserPayActivity.this, UserPaySuccessActivity.class);
                        if (payOrderBean != null) {
                            if (payOrderBean.getPoint() == 0) {
                                intent.putExtra("dmoney", 0.00);
                            } else {
                                intent.putExtra("dmoney", ArithmeticUtils.mul(payOrderBean.getPoint(), storeInfoResult.getPointConvert(), 2));
                            }
                            intent.putExtra("jmoney", payOrderBean.getRemissionPrice());
                            intent.putExtra("totalPrice", payOrderBean.getTotalPrice());
                            intent.putExtra("storeId", storeId);
                            intent.putExtra("paytype", 3);
                        }
                        startActivity(intent);
                    } else if (TextUtils.equals(resultStatus, "6001")) {
                        getPresenter().getUserOrderCancelResult(orderId);
                    } else if (TextUtils.equals(resultStatus, "4000")) {
                        toast("支付失败");
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        toast("支付异常，请在消费历史查看！");
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        toast("授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()));
                    } else {
                        // 其他状态值则为授权失败
                        toast("授权失败" + String.format("authCode:%s", authResult.getAuthCode()));
                    }
                    break;

                }
                default:
                    break;
            }
        }

        ;
    };

    @Override
    protected void onPause() {
        super.onPause();
        operation = 0;
    }


    public class FreeOfChargeView extends PopupWindow {

        public FreeOfChargeView(Context mContext, View parent, final int orderId, final int paytype) {

            View view = View.inflate(mContext, R.layout.free_charge_loading, null);
            view.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.filter));
            // LinearLayout ll_popup = (LinearLayout) view
            // .findViewById(R.id.ll_popup);
            // ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
            // R.anim.push_bottom_in_1));

            setWidth(WindowManager.LayoutParams.FILL_PARENT);
            setHeight(WindowManager.LayoutParams.FILL_PARENT);
            setBackgroundDrawable(new BitmapDrawable());
            setFocusable(false);
            setOutsideTouchable(false);
            // 实例化一个ColorDrawable颜色为半透明
            ColorDrawable dw = new ColorDrawable(0xb0000000);
            // 设置SelectPicPopupWindow弹出窗体的背景
            this.setBackgroundDrawable(dw);
            setContentView(view);
            showAtLocation(parent, Gravity.CENTER, 0, 0);
            TextView btn_dialog_confirm = (TextView) view.findViewById(R.id.btn_dialog_confirm);
            btn_dialog_confirm.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    dismiss();
                    getPresenter().getUserFreeOfChargeResult(orderId, paytype);
                }
            });
        }
    }

    public class FeeExemptionsView extends PopupWindow {

        public FeeExemptionsView(Context mContext, View parent, double remissionPrice, final int paytype, double finalPrice, final String aliOrderNo, final UserRechargeWxBean wxPayRequest) {

            View view = View.inflate(mContext, R.layout.fee_exemptions_loading, null);
            view.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.filter));
            // LinearLayout ll_popup = (LinearLayout) view
            // .findViewById(R.id.ll_popup);
            // ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
            // R.anim.push_bottom_in_1));

            setWidth(WindowManager.LayoutParams.FILL_PARENT);
            setHeight(WindowManager.LayoutParams.FILL_PARENT);
            setBackgroundDrawable(new BitmapDrawable());
            setFocusable(false);
            setOutsideTouchable(false);
            // 实例化一个ColorDrawable颜色为半透明
            ColorDrawable dw = new ColorDrawable(0xb0000000);
            // 设置SelectPicPopupWindow弹出窗体的背景
            this.setBackgroundDrawable(dw);
            setContentView(view);
            showAtLocation(parent, Gravity.CENTER, 0, 0);
            TextView btn_dialog_confirm = (TextView) view.findViewById(R.id.btn_dialog_confirm);
            TextView tv_content = (TextView) view.findViewById(R.id.tv_content);
            tv_content.setText(TextUtil.FontHighlighting(getBaseContext(), "随机减免", "¥" + remissionPrice, R.style.feeExemptions1, R.style.feeExemptions2));
            TextView tv_content1 = (TextView) view.findViewById(R.id.tv_content1);
            tv_content1.setText(TextUtil.FontHighlighting(getBaseContext(), "还需支付", "¥" + finalPrice, R.style.feeExemptions1, R.style.feeExemptions2));
            btn_dialog_confirm.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (paytype == 0) {
                        pay(aliOrderNo);
                    } else {
                        if (wxPayRequest != null) {
                            PayReq req = new PayReq();
                            //req.appId = "wxf8b4f85f3a794e77";  // 测试用appId
                            req.appId = wxPayRequest.getAppId();
                            req.partnerId = "1528772321";
                            req.prepayId = wxPayRequest.getPrypayId();
                            req.nonceStr = wxPayRequest.getNonceStr();
                            req.timeStamp = wxPayRequest.getTimeStamp();
                            req.packageValue = wxPayRequest.getPackageX();
                            req.sign = wxPayRequest.getPaySign();
                            api.sendReq(req);
                        } else {
                            toast("微信支付订单生成失败！");
                        }
                    }
                    dismiss();
                }
            });
        }
    }
}
