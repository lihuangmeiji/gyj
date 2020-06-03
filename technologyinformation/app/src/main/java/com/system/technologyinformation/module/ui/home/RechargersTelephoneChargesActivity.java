package com.system.technologyinformation.module.ui.home;

import android.Manifest;
import android.app.ActionBar;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.View;
import android.view.ViewStub;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.SPUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.system.technologyinformation.R;
import com.system.technologyinformation.common.ui.BaseActivity;
import com.system.technologyinformation.common.ui.SimpleActivity;
import com.system.technologyinformation.model.LoginBean;
import com.system.technologyinformation.model.PayMethodBean;
import com.system.technologyinformation.model.RechargersTelephoneChargesBean;
import com.system.technologyinformation.model.RtcOrderBean;
import com.system.technologyinformation.model.UserRechargeAliBean;
import com.system.technologyinformation.model.UserRechargeWxBean;
import com.system.technologyinformation.module.adapter.RechargersTelephoneChargesAdapter;
import com.system.technologyinformation.module.contract.RechargersTelephoneChargesContract;
import com.system.technologyinformation.module.demo2.Demo2Activity;
import com.system.technologyinformation.module.presenter.RechargersTelephoneChargesPresenter;
import com.system.technologyinformation.module.ui.users.UserPayActivity;
import com.system.technologyinformation.module.ui.users.UserPaySuccessActivity;
import com.system.technologyinformation.utils.ActivityCollector;
import com.system.technologyinformation.utils.ArithmeticUtils;
import com.system.technologyinformation.utils.SoftUpdate;
import com.system.technologyinformation.utils.TextUtil;
import com.system.technologyinformation.widget.GridDividerItemDecoration;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.commonsdk.debug.E;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;
import sign.AuthResult;
import sign.PayResult;

@RuntimePermissions
public class RechargersTelephoneChargesActivity extends BaseActivity<RechargersTelephoneChargesPresenter> implements RechargersTelephoneChargesContract.View {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_right)
    TextView iv_right;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.vs_showerror)
    ViewStub vs_showerror;
    @BindView(R.id.et_rtc_phone)
    EditText etRtcPhone;
    @BindView(R.id.tv_rtc_phone)
    TextView tvRtcPhone;
    @BindView(R.id.iv_get_phone)
    ImageView iv_get_phone;
    @BindView(R.id.iv_groupinfo)
    ImageView iv_groupinfo;

    RechargersTelephoneChargesAdapter rechargersTelephoneChargesAdapter;
    //微信支付
    private IWXAPI api;
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
    RechargersTelephoneChargesBean rechargersTelephoneChargesBean;
    PayMethodBean payMethodBean;

    @Override
    protected int getContentView() {
        return R.layout.activity_rechargers_telephone_charges;
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initEventAndData() {
        commonTheme();
        isClose = true;
        ActivityCollector.addActivity(this);
        toolbarTitle.setText("手机充值");
        toolbarTitle.setTextColor(getBaseContext().getResources().getColor(R.color.white));
        toolbar.setNavigationIcon(R.mipmap.ic_return_video);

        iv_right.setVisibility(View.GONE);
        iv_groupinfo.setVisibility(View.VISIBLE);
        toolbar.setBackgroundColor(getResources().getColor(R.color.color37));
        rechargersTelephoneChargesAdapter = new RechargersTelephoneChargesAdapter(R.layout.item_rechargers_telephone_charges);
        recyclerView.setLayoutManager(new GridLayoutManager(getBaseContext(), 3));
        recyclerView.addItemDecoration(new GridDividerItemDecoration(20, getBaseContext().getResources().getColor(R.color.white)));
        recyclerView.setAdapter(rechargersTelephoneChargesAdapter);
        getPresenter().getRechargersTelephoneChargesListResult();
        rechargersTelephoneChargesAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                rechargersTelephoneChargesBean = rechargersTelephoneChargesAdapter.getItem(i);
                rechargersTelephoneChargesAdapter.setSelectId(i);
                rechargersTelephoneChargesAdapter.notifyDataSetChanged();
            }
        });
        api = WXAPIFactory.createWXAPI(this, null);
        api.registerApp("wxb2911332cf274f1f");
        addWxPlayReceiver();
        if (login != null) {
            etRtcPhone.setText(login.getPhone());
            etRtcPhone.setEnabled(false);
        }
        iv_get_phone.setEnabled(true);
        getPresenter().getMethodOfPayment();
        //tvRtcPhone.setText(TextUtil.FontHighlighting(getBaseContext(),"售后客服电话：","400-882-7715",R.style.tv_integral_rules1,R.style.tv_integral_rules2));
    }


    @Override
    public void setRechargersTelephoneChargesListResult(List<RechargersTelephoneChargesBean> rechargersTelephoneChargesListResult) {
        rechargersTelephoneChargesAdapter.getData().clear();
        if (rechargersTelephoneChargesListResult == null || rechargersTelephoneChargesListResult.size() == 0) {
            vs_showerror.setLayoutResource(R.layout.layout_empty);
            vs_showerror.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            return;
        }
        rechargersTelephoneChargesAdapter.addData(rechargersTelephoneChargesListResult);
        rechargersTelephoneChargesAdapter.setSelectId(-1);
        rechargersTelephoneChargesAdapter.notifyDataSetChanged();
        vs_showerror.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void getRtcOrderResult(RtcOrderBean rtcOrderBean) {
        hiddenLoading();
        if (rtcOrderBean != null) {
            new PayWayView(getBaseContext(), recyclerView, rtcOrderBean.getId(), rtcOrderBean.getFinalPrice());
        }

    }

    @Override
    public void setUserLoginResult(LoginBean loginBean) {
        if (loginBean != null) {
            clearUserInfo();
            SPUtils spUtils = new SPUtils("saveUser");
            spUtils.put("userInfo", new Gson().toJson(loginBean));
            loadUserInfo();
            getPresenter().getRechargersTelephoneChargesListResult();
        } else {
            openLogin();
        }
    }

    @Override
    public void setRtcRechargeWx(UserRechargeWxBean userRechargeWxBean) {
        if (userRechargeWxBean != null) {
            PayReq req = new PayReq();
            //req.appId = "wxf8b4f85f3a794e77";  // 测试用appId
            req.appId = userRechargeWxBean.getAppId();
            req.partnerId = "1528772321";
            req.prepayId = userRechargeWxBean.getPrypayId();
            req.nonceStr = userRechargeWxBean.getNonceStr();
            req.timeStamp = userRechargeWxBean.getTimeStamp();
            req.packageValue = userRechargeWxBean.getPackageX();
            req.sign = userRechargeWxBean.getPaySign();
            api.sendReq(req);
        } else {
            toast("微信支付信息生成失败");
        }
    }

    @Override
    public void setRtcrRechargeZfb(String str) {
        if (str != null) {
            pay(str);
        } else {
            toast("支付宝支付信息生成失败");
        }
    }

    @Override
    public void setMethodOfPayment(PayMethodBean payMethodBean) {
        this.payMethodBean = payMethodBean;
    }


    @OnClick({R.id.toolbar,
            R.id.iv_get_phone,
            R.id.tv_rtc,
            R.id.tv_rtc_phone,
            R.id.iv_groupinfo
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar:
                finish();
                break;
            case R.id.iv_get_phone:
                RechargersTelephoneChargesActivityPermissionsDispatcher.getPhoneWithCheck(this);
                break;
            case R.id.tv_rtc:
                if (EmptyUtils.isEmpty(etRtcPhone.getText().toString())) {
                    toast("请输入充值手机号");
                    return;
                }
                if (!isMobile(etRtcPhone.getText().toString().trim())) {
                    toast("手机号格式不正确");
                    return;
                }
                if (EmptyUtils.isEmpty(rechargersTelephoneChargesBean)) {
                    toast("请选择充值套餐");
                    return;
                }
                new RechargersTelephoneChargesView(getBaseContext(), recyclerView, etRtcPhone.getText().toString(), rechargersTelephoneChargesBean);
                break;
            case R.id.tv_rtc_phone:
                try {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    Uri data = Uri.parse("tel:" + "4008827715");
                    intent.setData(data);
                    startActivity(intent);
                } catch (Exception e) {
                    toast("呼叫失败");
                }
                break;
            case R.id.iv_groupinfo:
                Intent intent = new Intent(this, RechargesHistoryActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void showErrorMsg(String msg, int code) {
        super.showErrorMsg(msg, code);
        hiddenLoading();
        if (rechargersTelephoneChargesBean != null) {
            rechargersTelephoneChargesAdapter.setSelectId(-1);
            rechargersTelephoneChargesAdapter.notifyDataSetChanged();
        }
        if (code == -1) {
            if (login != null && showLog == false) {
                showLog = true;
                getPresenter().getUserLoginResult(login.getPhone(), "", "", login.getToken());
            } else {
                openLogin();
            }
            return;
        } else if (code == 405) {
            recyclerView.setVisibility(View.GONE);
            vs_showerror.setLayoutResource(R.layout.layout_no_network);
            //vs_showerror.inflate();
            vs_showerror.setVisibility(View.VISIBLE);
            LinearLayout lin_load = (LinearLayout) findViewById(R.id.lin_load);
            lin_load.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getPresenter().getRechargersTelephoneChargesListResult();
                }
            });
        } else if (code == 1) {

        } else if (code == -10) {
            return;
        } else {
            recyclerView.setVisibility(View.GONE);
            vs_showerror.setLayoutResource(R.layout.layout_empty);
            vs_showerror.setVisibility(View.VISIBLE);
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
    public void toast(String msg) {
        super.toast(msg);
    }


    public class RechargersTelephoneChargesView extends PopupWindow {

        public RechargersTelephoneChargesView(Context mContext, View parent, final String phone, final RechargersTelephoneChargesBean rechargersTelephoneChargesBean) {

            View view = View.inflate(mContext, R.layout.rechargers_telephone_loading, null);
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
            TextView btn_dialog_cancel = (TextView) view.findViewById(R.id.btn_dialog_cancel);
            TextView tv_content = (TextView) view.findViewById(R.id.tv_content);
            tv_content.setText("确定给号码(" + phone + ")充值?");
            btn_dialog_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    rechargersTelephoneChargesAdapter.setSelectId(-1);
                    rechargersTelephoneChargesAdapter.notifyDataSetChanged();
                    dismiss();
                }
            });
            btn_dialog_confirm.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    dismiss();
                    showLoading();
                    getPresenter().addRtcOrderResult(phone, rechargersTelephoneChargesBean.getSourcePrice());
                }
            });
        }
    }


    public class PayWayView extends PopupWindow {

        public PayWayView(Context mContext, View parent, final int billId, double mtotalPrice) {

            View view = View.inflate(mContext, R.layout.view_pay_way, null);
            view.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.filter));
            // LinearLayout ll_popup = (LinearLayout) view
            // .findViewById(R.id.ll_popup);
            // ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
            // R.anim.push_bottom_in_1));

            setWidth(ActionBar.LayoutParams.FILL_PARENT);
            setHeight(ActionBar.LayoutParams.FILL_PARENT);
            setBackgroundDrawable(new BitmapDrawable());
            setFocusable(true);
            setOutsideTouchable(true);
            // 实例化一个ColorDrawable颜色为半透明
            ColorDrawable dw = new ColorDrawable(0xb0000000);
            // 设置SelectPicPopupWindow弹出窗体的背景
            this.setBackgroundDrawable(dw);
            setContentView(view);
            showAtLocation(parent, Gravity.BOTTOM, 0, 0);
            TextView tv_pay_money = (TextView) view.findViewById(R.id.tv_pay_money);
            TextView tv_pay_integral = (TextView) view.findViewById(R.id.tv_pay_integral);
            LinearLayout lin_pay_wx = (LinearLayout) view.findViewById(R.id.lin_pay_wx);
            LinearLayout lin_pay_zfb = (LinearLayout) view.findViewById(R.id.lin_pay_zfb);
            LinearLayout lin_pay_xj = (LinearLayout) view.findViewById(R.id.lin_pay_xj);
            if (EmptyUtils.isEmpty(payMethodBean)) {
                lin_pay_wx.setVisibility(View.GONE);
                lin_pay_zfb.setVisibility(View.GONE);
            } else {
                if (payMethodBean.isAliPay()) {
                    lin_pay_zfb.setVisibility(View.VISIBLE);
                } else {
                    lin_pay_zfb.setVisibility(View.GONE);
                }
                if (payMethodBean.isWxPay()) {
                    lin_pay_wx.setVisibility(View.VISIBLE);
                } else {
                    lin_pay_wx.setVisibility(View.GONE);
                }
            }
            LinearLayout ll_popup = view.findViewById(R.id.ll_popup);
            View view_pay = view.findViewById(R.id.view_pay);
            final ImageView iv_wx_select = (ImageView) view.findViewById(R.id.iv_wx_select);
            final ImageView iv_zfb_select = (ImageView) view.findViewById(R.id.iv_zfb_select);
            final ImageView iv_xj_select = (ImageView) view.findViewById(R.id.iv_xj_select);
            tv_pay_money.setText("￥" + mtotalPrice);
            lin_pay_wx.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iv_wx_select.setImageResource(R.mipmap.zfyx);
                    iv_zfb_select.setImageResource(R.mipmap.zfwx);
                    iv_xj_select.setImageResource(R.mipmap.zfwx);
                    getPresenter().getRtcRechargeWx(billId);
                    dismiss();
                }
            });
            lin_pay_zfb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iv_wx_select.setImageResource(R.mipmap.zfwx);
                    iv_zfb_select.setImageResource(R.mipmap.zfyx);
                    iv_xj_select.setImageResource(R.mipmap.zfwx);
                    getPresenter().getRtcrRechargeZfb(billId);
                    dismiss();
                }
            });
            lin_pay_xj.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iv_wx_select.setImageResource(R.mipmap.zfwx);
                    iv_zfb_select.setImageResource(R.mipmap.zfwx);
                    iv_xj_select.setImageResource(R.mipmap.zfyx);
                    //getPresenter().getUserRechargeXj(storeOrderResult.getId());
                    dismiss();
                }
            });

            view_pay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (rechargersTelephoneChargesBean != null) {
                        rechargersTelephoneChargesAdapter.setSelectId(-1);
                        rechargersTelephoneChargesAdapter.notifyDataSetChanged();
                    }
                    dismiss();
                }
            });
            ll_popup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1) {
            getPresenter().getRechargersTelephoneChargesListResult();
        }

        if (requestCode == 0x30) {
            if (data != null) {
                Uri uri = data.getData();
                String phoneNum = null;
                String contactName = null;
                // 创建内容解析者
                ContentResolver contentResolver = getContentResolver();
                Cursor cursor = null;
                if (uri != null) {
                    cursor = contentResolver.query(uri,
                            new String[]{"display_name", "data1"}, null, null, null);
                }
                while (cursor.moveToNext()) {
                    contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    phoneNum = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                }
                cursor.close();
                //  把电话号码中的  -  符号 替换成空格
                if (phoneNum != null) {
                    phoneNum = phoneNum.replaceAll("-", " ");
                    // 空格去掉  为什么不直接-替换成"" 因为测试的时候发现还是会有空格 只能这么处理
                    phoneNum = phoneNum.replaceAll(" ", "");
                }
                etRtcPhone.setText(phoneNum);
            }
        }
    }


    BroadcastReceiver broadcastReceiver;

    private void addWxPlayReceiver() {
        broadcastReceiver = new BroadcastReceiver()

        {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (rechargersTelephoneChargesBean != null) {
                    rechargersTelephoneChargesAdapter.setSelectId(-1);
                    rechargersTelephoneChargesAdapter.notifyDataSetChanged();
                }
                int type = intent.getIntExtra("type", 2);

                if (type == 1) {
                    toast("支付成功");
                    getPresenter().getRechargersTelephoneChargesListResult();
                } else if (type == 3) {
                    toast("取消充值");
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
                PayTask alipay = new PayTask(RechargersTelephoneChargesActivity.this);
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
                    if (rechargersTelephoneChargesBean != null) {
                        rechargersTelephoneChargesAdapter.setSelectId(-1);
                        rechargersTelephoneChargesAdapter.notifyDataSetChanged();
                    }
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        toast("支付成功");
                        getPresenter().getRechargersTelephoneChargesListResult();
                    } else if (TextUtils.equals(resultStatus, "6001")) {
                        toast("取消支付");
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


    // 单个权限
    // @NeedsPermission(Manifest.permission.CAMERA)
    // 多个权限
    @NeedsPermission({Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS})
    void getPhone() {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.PICK");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setType("vnd.android.cursor.dir/phone_v2");
        startActivityForResult(intent, 0x30);
    }

    // 向用户说明为什么需要这些权限（可选）
    @OnShowRationale({Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS})
    void showRationaleForGetPhone(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setMessage("获取联系人手机号")
                .setPositiveButton("知道了", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();//再次执行请求
                    }
                })
                .show();
    }

    // 用户拒绝授权回调（可选）
    @OnPermissionDenied({Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS})
    void showDeniedForGetPhone() {
        Toast.makeText(this, "小主，给个权限吧！", Toast.LENGTH_SHORT).show();
    }

    // 用户勾选了“不再提醒”时调用（可选）
    @OnNeverAskAgain({Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS})
    void showNeverAskForGetPhone() {
        Toast.makeText(this, "很开心，以后不打扰你了！", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // 代理权限处理到自动生成的方法
        RechargersTelephoneChargesActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

}
