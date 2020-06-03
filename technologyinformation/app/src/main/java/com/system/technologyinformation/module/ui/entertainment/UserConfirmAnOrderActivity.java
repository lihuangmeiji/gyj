package com.system.technologyinformation.module.ui.entertainment;

import android.app.ActionBar;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewStub;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.contrarywind.listener.OnItemSelectedListener;
import com.contrarywind.view.WheelView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.system.technologyinformation.R;
import com.system.technologyinformation.common.ui.BaseActivity;
import com.system.technologyinformation.model.ConfirmOrderBean;
import com.system.technologyinformation.model.LoginBean;
import com.system.technologyinformation.model.OnlineSupermarketBean;
import com.system.technologyinformation.model.PayMethodBean;
import com.system.technologyinformation.model.UserRechargeWxBean;
import com.system.technologyinformation.model.http.base.BaseResponseInfo;
import com.system.technologyinformation.module.adapter.ConfirmOrderAdapter;
import com.system.technologyinformation.module.contract.UserConfirmAnOrderContract;
import com.system.technologyinformation.module.presenter.UserConfirmAnOrderPresenter;
import com.system.technologyinformation.module.ui.MainActivity;
import com.system.technologyinformation.module.ui.users.UserPayActivity;
import com.system.technologyinformation.module.ui.users.UserPaySuccessActivity;
import com.system.technologyinformation.utils.ActivityCollector;
import com.system.technologyinformation.utils.ArithmeticUtils;
import com.system.technologyinformation.utils.TextUtil;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.OnClick;
import sign.AuthResult;
import sign.PayResult;

public class UserConfirmAnOrderActivity extends BaseActivity<UserConfirmAnOrderPresenter> implements UserConfirmAnOrderContract.View {
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
    @BindView(R.id.tv_pay_money)
    TextView tv_pay_money;
    @BindView(R.id.tv_order_user_info)
    TextView tvOrderUserInfo;
    @BindView(R.id.tv_order_time)
    TextView tv_order_time;
    @BindView(R.id.tv_order_address)
    TextView tv_order_address;
    @BindView(R.id.tv_pay)
    TextView tv_pay;
    @BindView(R.id.lin_order_time)
    LinearLayout linOrderTime;

    ConfirmOrderAdapter confirmOrderAdapter;
    List<OnlineSupermarketBean> onlineSupermarketBeanList;
    private double mtotalPrice = 0.00;
    NumberFormat nf;
    TimePickerView pvTime;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String address;
    int goodstype = 0;
    String dateTime;
    String ymm;  //年月日
    String hms;  //时分秒
    int deliveryId;
    int orderId;
    //微信支付
    private IWXAPI api;

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
    Calendar calendar1;
    boolean todayBuy;
    List<String> stringTimeList;
    String orderData;
    PayMethodBean payMethodBean;

    @Override
    protected int getContentView() {
        return R.layout.activity_user_confirm_an_order;
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initEventAndData() {
        commonTheme();
        api = WXAPIFactory.createWXAPI(this, null);
        api.registerApp("wxb2911332cf274f1f");
        addWxPlayReceiver();
        ActivityCollector.addActivity(this);
        stringTimeList = new ArrayList<>();
        nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(2);
        toolbar.setNavigationIcon(R.mipmap.ic_return_video);
        iv_right.setVisibility(View.GONE);
        toolbar.setBackgroundColor(getResources().getColor(R.color.color37));
        orderId = getIntent().getIntExtra("orderId", 0);
        address = getIntent().getStringExtra("address");
        goodstype = getIntent().getIntExtra("goodstype", 0);
        mtotalPrice = getIntent().getDoubleExtra("mtotalPrice", 0.00);
        toolbarTitle.setTextColor(getBaseContext().getResources().getColor(R.color.white));
        if (goodstype == 0) {
            toolbarTitle.setText("抢购商品");
            linOrderTime.setVisibility(View.GONE);
        } else {
            toolbarTitle.setText("订餐");
            linOrderTime.setVisibility(View.VISIBLE);
        }
        deliveryId = getIntent().getIntExtra("deliveryId", 0);
        if (!EmptyUtils.isEmpty(address)) {
            tv_order_address.setText(address);
        }
        orderData = getIntent().getStringExtra("orderData");
        onlineSupermarketBeanList = (List<OnlineSupermarketBean>) getIntent().getSerializableExtra("onlineSupermarketBeanList");

        todayBuy = false;
        if (onlineSupermarketBeanList != null && onlineSupermarketBeanList.size() > 0) {
            for (int i = 0; i < onlineSupermarketBeanList.size(); i++) {
                if (onlineSupermarketBeanList.get(i).isTodayBuy()) {
                    todayBuy = true;
                } else {
                    todayBuy = false;
                    break;
                }
            }
        }
        tv_pay_money.setText(TextUtil.FontHighlighting(getBaseContext(), "合计  ", "￥" + mtotalPrice, R.style.tv_pay_money1, R.style.tv_pay_money2));
        //calulate();
        if (login != null) {
            tvOrderUserInfo.setText(login.getNickName() + "   " + login.getPhone());
        }
        confirmOrderAdapter = new ConfirmOrderAdapter(R.layout.item_confirm_order);
        confirmOrderAdapter.addData(onlineSupermarketBeanList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerView.setAdapter(confirmOrderAdapter);
        tv_pay.setSelected(true);
        calendar1 = GregorianCalendar.getInstance();
        if (!todayBuy) {
            calendar1.add(Calendar.DATE, 1);
        }
        int myear = calendar1.get(Calendar.YEAR);
        int mMonth = calendar1.get(Calendar.MONTH) + 1;// 获取当前月份
        int day = calendar1.get(Calendar.DAY_OF_MONTH);// 获取当前天数
        int hour = calendar1.get(Calendar.HOUR_OF_DAY);// 获取当前天数
        ymm = myear + "-" + mMonth + "-" + day;
        if (todayBuy) {
            if (hour <= 12) {
                hms = " 12:00:00";
                tv_order_time.setText(mMonth + "月" + day + "日(今天)  午餐");
            } else {
                hms = " 18:00:00";
                tv_order_time.setText(mMonth + "月" + day + "日(今天)  晚餐");
            }
        } else {
            hms = " 12:00:00";
            tv_order_time.setText(mMonth + "月" + day + "日(明天)  午餐");
        }
        dateTime = ymm + hms;
        getPresenter().getMethodOfPayment();
    }

    @OnClick({R.id.toolbar,
            R.id.tv_pay,
            R.id.lin_order_time
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar:
                setResult(1);
                finish();
                break;
            case R.id.tv_pay:
                if (goodstype == 1 && EmptyUtils.isEmpty(dateTime)) {
                    toast("请选择配送时间");
                    return;
                }
                if (EmptyUtils.isEmpty(orderData)) {
                    toast("商品信息获取失败，请返回列表重新获取");
                    return;
                }
                new PayWayView(getBaseContext(), recyclerView);
                break;
            case R.id.lin_order_time:
                new OrderTimeView(getBaseContext(), recyclerView);
                break;
        }
    }

    @Override
    public void getDeliveryResult(BaseResponseInfo baseResponseInfo) {
        if (baseResponseInfo.getCode() == 0) {
            Intent intent = new Intent(UserConfirmAnOrderActivity.this, UserPayActivity.class);
            intent.putExtra("storeId", 0);
            intent.putExtra("mtotalPrice", mtotalPrice);
            startActivity(intent);
            finish();
        } else {
            toast("配送信息添加失败");
        }
    }

    @Override
    public void setUserConfirmAnOrderPayWx(UserRechargeWxBean userRechargeWxBean) {
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
    public void setUserConfirmAnOrderPayZfb(String str) {
        if (str != null) {
            pay(str);
        } else {
            toast("支付宝支付信息生成失败");
        }
    }

    @Override
    public void setUserLoginResult(LoginBean loginBean) {
        if (loginBean != null) {
            clearUserInfo();
            SPUtils spUtils = new SPUtils("saveUser");
            spUtils.put("userInfo", new Gson().toJson(loginBean));
            loadUserInfo();
            toast("服务器未响应，请重新操作！");
        } else {
            openLogin();
        }
    }

    @Override
    public void setOnlineSupermarketGoodsOreder(ConfirmOrderBean confirmOrderBean, int payType) {
        hiddenLoading();
        if (confirmOrderBean != null) {
            mtotalPrice = confirmOrderBean.getTotalPrice();
            deliveryId = confirmOrderBean.getDeliveryId();
            orderId = confirmOrderBean.getId();
            if (payType == 1) {
                getPresenter().getUserConfirmAnOrderPayWx(deliveryId, orderId, dateTime);
            } else if (payType == 2) {
                getPresenter().getUserConfirmAnOrderPayZfb(deliveryId, orderId, dateTime);
            }
        } else {
            toast("预订单生成失败");
        }
    }

    @Override
    public void setMethodOfPayment(PayMethodBean payMethodBean) {
        this.payMethodBean = payMethodBean;
    }


    public class PayWayView extends PopupWindow {

        public PayWayView(Context mContext, View parent) {

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
                    showLoading();
                    getPresenter().getOnlineSupermarketGoodsOreder(orderData, dateTime, 1);
                    dismiss();
                }
            });
            lin_pay_zfb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iv_wx_select.setImageResource(R.mipmap.zfwx);
                    iv_zfb_select.setImageResource(R.mipmap.zfyx);
                    iv_xj_select.setImageResource(R.mipmap.zfwx);
                    showLoading();
                    getPresenter().getOnlineSupermarketGoodsOreder(orderData, dateTime, 2);
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

    public class OrderTimeView extends PopupWindow {

        public OrderTimeView(Context mContext, View parent) {

            View view = View.inflate(mContext, R.layout.view_order_time, null);
            view.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.filter
            ));
            setWidth(WindowManager.LayoutParams.FILL_PARENT);
            setHeight(WindowManager.LayoutParams.FILL_PARENT);
            setBackgroundDrawable(new BitmapDrawable());
            setFocusable(true);
            setOutsideTouchable(true);
            // 实例化一个ColorDrawable颜色为半透明
            ColorDrawable dw = new ColorDrawable(0xb0000000);
            // 设置SelectPicPopupWindow弹出窗体的背景
            this.setBackgroundDrawable(dw);
            setContentView(view);
            showAtLocation(parent, Gravity.BOTTOM, 0, 0);
            ImageView iv_order_time = view.findViewById(R.id.iv_order_time);
            iv_order_time.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });

            WheelView wheelviewtype = view.findViewById(R.id.wheelviewtype);
            wheelviewtype.setCyclic(false);
            wheelviewtype.setItemHeight(100);
            wheelviewtype.setTextSize(16);
            wheelviewtype.setCurrentItem(0);
            wheelviewtype.setTextColorOut(getResources().getColor(R.color.color27));
            wheelviewtype.setTextColorCenter(getResources().getColor(R.color.color5));
            final List<String> mOptionsTypeItems = new ArrayList<>();
            mOptionsTypeItems.add("午餐");
            hms = " 12:00:00";
            mOptionsTypeItems.add("晚餐");
            wheelviewtype.setAdapter(new ArrayWheelAdapter(mOptionsTypeItems));
            wheelviewtype.setOnItemSelectedListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(int index) {
                    if (index == 0) {
                        hms = " 12:00:00";
                    } else {
                        hms = " 18:00:00";
                    }
                }
            });
            final List<String> mOptionsItems = new ArrayList<>();
            if (todayBuy) {
                for (int i = 0; i < 11; i++) {
                    String strTime = retStringTime(i);
                    stringTimeList.add(strTime);
                    if (i == 0) {
                        mOptionsItems.add("今天");
                        ymm = strTime;
                    } else if (i == 1) {
                        mOptionsItems.add("明天");
                    } else if (i == 2) {
                        mOptionsItems.add("后天");
                    } else {
                        mOptionsItems.add(strTime);
                    }
                }
            } else {
                for (int i = 0; i < 10; i++) {
                    String strTime = retStringTime(i + 1);
                    stringTimeList.add(strTime);
                    if (i == 0) {
                        ymm = strTime;
                        mOptionsItems.add("明天");
                    } else if (i == 1) {
                        mOptionsItems.add("后天");
                    } else {
                        mOptionsItems.add(strTime);
                    }
                }
            }
            WheelView wheelView = view.findViewById(R.id.wheelview);
            wheelView.setCyclic(false);
            wheelView.setItemHeight(100);
            wheelView.setTextSize(16);
            wheelView.setCurrentItem(0);
            wheelView.setTextColorOut(getResources().getColor(R.color.color27));
            wheelView.setTextColorCenter(getResources().getColor(R.color.color5));
            wheelView.setAdapter(new ArrayWheelAdapter(mOptionsItems));
            wheelView.setOnItemSelectedListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(int index) {
                    ymm = stringTimeList.get(index);
                }
            });

            TextView tv_pay = view.findViewById(R.id.tv_pay);
            tv_pay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    dateTime = ymm + hms;
                    calendar1.setTime(TimeUtils.string2Date(dateTime));
                    if (calendar1 != null) {
                        int mMonth = calendar1.get(Calendar.MONTH) + 1;// 获取当前月份
                        int day = calendar1.get(Calendar.DAY_OF_MONTH);// 获取当前天数
                        int hour = calendar1.get(Calendar.HOUR_OF_DAY);// 获取当前天数
                        Calendar calendar2 = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
                        int mday = calendar2.get(Calendar.DAY_OF_MONTH);// 获取当前天数
                        if (day - mday == 0) {
                            if (hour <= 12) {
                                tv_order_time.setText(mMonth + "月" + day + "日(今天)  午餐");
                            } else {
                                tv_order_time.setText(mMonth + "月" + day + "日(今天)  晚餐");
                            }
                        } else if (day - mday == 1) {
                            if (hour <= 12) {
                                tv_order_time.setText(mMonth + "月" + day + "日(明天)  午餐");
                            } else {
                                tv_order_time.setText(mMonth + "月" + day + "日(明天)  晚餐");
                            }
                        } else if (day - mday == 2) {
                            if (hour <= 12) {
                                tv_order_time.setText(mMonth + "月" + day + "日(后天)  午餐");
                            } else {
                                tv_order_time.setText(mMonth + "月" + day + "日(后天)  晚餐");
                            }
                        } else {
                            if (hour <= 12) {
                                tv_order_time.setText(mMonth + "月" + day + "日  午餐");
                            } else {
                                tv_order_time.setText(mMonth + "月" + day + "日  晚餐");
                            }
                        }

                    }
                }
            });
        }
    }

    public String retStringTime(int index) {
        Calendar calendar2 = Calendar.getInstance();
        calendar2.add(Calendar.DATE, index);
        return TimeUtils.date2String(calendar2.getTime(), "yyyy-MM-dd");
    }

    /**
     * 计算商品总价格，操作步骤
     * 1.先清空全局计价,计数
     * 2.遍历所有的子元素，只要是被选中的，就进行相关的计算操作
     * 3.给textView填充数据
     */
    private void calulate() {
        for (int j = 0; j < onlineSupermarketBeanList.size(); j++) {
            OnlineSupermarketBean onlineSupermarketBean = onlineSupermarketBeanList.get(j);
            mtotalPrice += onlineSupermarketBean.getCurrentPrice() * onlineSupermarketBean.getShopnumber();
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
    public void toast(String msg) {
        super.toast(msg);
    }

    BroadcastReceiver broadcastReceiver;

    private void addWxPlayReceiver() {
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int type = intent.getIntExtra("type", 2);
                if (type == 1) {
                    Intent intent1 = new Intent(UserConfirmAnOrderActivity.this, UserPaySuccessActivity.class);
                    intent1.putExtra("dmoney", 0.00);
                    intent1.putExtra("jmoney", 0.00);
                    intent1.putExtra("totalPrice", mtotalPrice);
                    startActivity(intent1);
                    finish();
                } else if (type == 3) {
                    toast("取消支付");
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                setResult(1);
                finish();
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * call alipay sdk pay. 调用SDK支付
     */
    public void pay(final String ranking) {
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(UserConfirmAnOrderActivity.this);
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
                        Intent intent1 = new Intent(UserConfirmAnOrderActivity.this, UserPaySuccessActivity.class);
                        intent1.putExtra("dmoney", 0.00);
                        intent1.putExtra("jmoney", 0.00);
                        intent1.putExtra("totalPrice", mtotalPrice);
                        startActivity(intent1);
                        finish();
                    } else if (TextUtils.equals(resultStatus, "6001")) {
                        toast("取消支付");
                    } else if (TextUtils.equals(resultStatus, "4000")) {
                        toast("支付失败");
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
    protected void onDestroy() {
        super.onDestroy();
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
        }
    }
}
