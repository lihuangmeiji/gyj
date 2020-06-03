package com.system.technologyinformation.module.ui.users;

import android.app.ActionBar;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewStub;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.system.technologyinformation.R;
import com.system.technologyinformation.common.ui.BaseActivity;
import com.system.technologyinformation.model.DeliveryBean;
import com.system.technologyinformation.model.DeliveryInfoBean;
import com.system.technologyinformation.model.LoginBean;
import com.system.technologyinformation.model.OnlineSupermarketBean;
import com.system.technologyinformation.model.OnlineSupermarketOrderBean;
import com.system.technologyinformation.model.RefundsBean;
import com.system.technologyinformation.model.UserOrderBean;
import com.system.technologyinformation.model.UserRechargeWxBean;
import com.system.technologyinformation.model.VersionShowBean;
import com.system.technologyinformation.module.adapter.RefundsAdapter;
import com.system.technologyinformation.module.adapter.UserLogisticsAdapter;
import com.system.technologyinformation.module.adapter.UserOrderAdapter;
import com.system.technologyinformation.module.adapter.UserOrdreTitleAdapter;
import com.system.technologyinformation.module.contract.UserOrderContract;
import com.system.technologyinformation.module.presenter.UserOrderPresenter;
import com.system.technologyinformation.module.ui.MainActivity;
import com.system.technologyinformation.module.ui.account.LoginActivity;
import com.system.technologyinformation.module.ui.entertainment.TableReservationActivity;
import com.system.technologyinformation.module.ui.entertainment.UserConfirmAnOrderActivity;
import com.system.technologyinformation.utils.ActivityCollector;
import com.system.technologyinformation.widget.GridDividerItemDecoration;
import com.system.technologyinformation.widget.RecycleViewDivider;
import com.system.technologyinformation.widget.SpaceItemDecoration;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import sign.AuthResult;
import sign.PayResult;

public class UserOrderActivity extends BaseActivity<UserOrderPresenter> implements UserOrderContract.View, UserOrderAdapter.UserOrderInterface, BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_right)
    TextView iv_right;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;
    @BindView(R.id.vs_showerror)
    ViewStub vs_showerror;
    @BindView(R.id.lin_order_bg)
    LinearLayout lin_order_bg;
    @BindView(R.id.recycler_view_title)
    RecyclerView recyclerViewTitle;

    RefundsAdapter refundsAdapter;
    int currentPage;
    int pageNumber = 10;
    String address;
    UserOrderAdapter userOrderAdapter;
    UserOrdreTitleAdapter userOrdreTitleAdapter;
    List<OnlineSupermarketBean> onlineSupermarketBeanList;
    List<OnlineSupermarketOrderBean> onlineSupermarketOrderBeanList;
    Integer status = null;
    //微信支付
    private IWXAPI api;

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
    String content = null;
    private double mtotalPrice = 0.00;
    @Override
    protected int getContentView() {
        return R.layout.activity_user_order;
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initEventAndData() {
        commonTheme();
        ActivityCollector.addActivity(this);
        api = WXAPIFactory.createWXAPI(this, null);
        api.registerApp("wxb2911332cf274f1f");
        addWxPlayReceiver();
        onlineSupermarketBeanList = new ArrayList<>();
        onlineSupermarketOrderBeanList = new ArrayList<>();
        toolbarTitle.setText("我的订单");
        toolbarTitle.setTextColor(getBaseContext().getResources().getColor(R.color.white));
        toolbar.setNavigationIcon(R.mipmap.ic_return_video);
        iv_right.setVisibility(View.GONE);
        toolbar.setBackgroundColor(getResources().getColor(R.color.color37));
        userOrdreTitleAdapter = new UserOrdreTitleAdapter(R.layout.item_user_order_title);
        userOrdreTitleAdapter.setSelIndex(0);
        recyclerViewTitle.setLayoutManager(new GridLayoutManager(getBaseContext(), 3));
        recyclerViewTitle.setAdapter(userOrdreTitleAdapter);
        List<String> stringList = new ArrayList<>();
        stringList.add("全部订单");
        stringList.add("待付款");
        stringList.add("退款");
        userOrdreTitleAdapter.addData(stringList);
        userOrdreTitleAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                userOrdreTitleAdapter.setSelIndex(i);
                userOrdreTitleAdapter.notifyDataSetChanged();
                if (i == 0) {
                    status = null;
                } else if (i == 1) {
                    status = 1;
                } else if (i == 2) {
                    status = 6;
                }
                refresh();
            }
        });
        userOrderAdapter = new UserOrderAdapter(R.layout.item_user_order);
        userOrderAdapter.setUserOrderInterface(this);
        userOrderAdapter.setOnLoadMoreListener(this, recyclerView);
        userOrderAdapter.setEnableLoadMore(true);
        userOrderAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerView.addItemDecoration(new SpaceItemDecoration(0, 30));
        recyclerView.setAdapter(userOrderAdapter);
        refresh();
    }

    @Override
    public void setUserOrderListResult(List<UserOrderBean> userOrderListResult) {
        swipeLayout.setRefreshing(false);
        userOrderAdapter.setEnableLoadMore(true);
        swipeLayout.setEnabled(true);
        if (userOrderListResult == null || userOrderListResult.size() == 0) {
            userOrderAdapter.loadMoreEnd();
            if (currentPage == 1) {
                vs_showerror.setLayoutResource(R.layout.layout_empty);
                vs_showerror.setVisibility(View.VISIBLE);
                swipeLayout.setVisibility(View.GONE);
            }
            return;
        }
        userOrderAdapter.addData(userOrderListResult);
        vs_showerror.setVisibility(View.GONE);
        swipeLayout.setVisibility(View.VISIBLE);
        userOrderAdapter.loadMoreComplete();
        currentPage++;
        if (userOrderListResult.size() < pageNumber) {
            userOrderAdapter.loadMoreEnd();
            return;
        }
    }

    @Override
    public void setOnlineSupermarketGoodsOreder(List<DeliveryBean> deliveryBeanList) {
        if (deliveryBeanList != null && deliveryBeanList.size() > 0) {
            Intent intent = new Intent(UserOrderActivity.this, UserConfirmAnOrderActivity.class);
            intent.putExtra("onlineSupermarketBeanList", (Serializable) onlineSupermarketBeanList);
            intent.putExtra("address", address);
            intent.putExtra("goodstype", 1);
            intent.putExtra("deliveryId", deliveryBeanList.get(0).getDeliveryId());
            startActivity(intent);
        } else {
            toast("预订单生成失败");
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
    public void addUserOrderRefundsResult(String str) {
        toast("已提交，后续工作人员会联系你！");
    }

    private void refresh() {
        swipeLayout.setRefreshing(true);
        swipeLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                onRefresh();
            }
        }, 100);
    }

    @Override
    public void onRefresh() {
        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                currentPage = 1;
                userOrderAdapter.getData().clear();
                userOrderAdapter.notifyDataSetChanged();
                getPresenter().getUserOrderListResult(currentPage, pageNumber, status);
            }
        }, 100);
    }

    @Override
    public void onLoadMoreRequested() {
        swipeLayout.setEnabled(false);
        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                getPresenter().getUserOrderListResult(currentPage, pageNumber, status);
            }
        }, 100);
    }

    @Override
    public void showErrorMsg(String msg, int code) {
        super.showErrorMsg(msg, code);
        if (code == -1) {
            if (login != null && showLog == false) {
                showLog = true;
                getPresenter().getUserLoginResult(login.getPhone(), "", "", login.getToken());
            } else {
                openLogin();
            }
            return;
        } else if (code == 405) {
            swipeLayout.setVisibility(View.GONE);
            vs_showerror.setLayoutResource(R.layout.layout_no_network);
            //vs_showerror.inflate();
            vs_showerror.setVisibility(View.VISIBLE);
            LinearLayout lin_load = (LinearLayout) findViewById(R.id.lin_load);
            lin_load.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    refresh();
                }
            });
        } else if (code == -10) {
            return;
        } else {
            swipeLayout.setVisibility(View.GONE);
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


    @OnClick({R.id.toolbar
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar:
                finish();
                Intent intentuserupdate = new Intent("userupdate");
                sendBroadcast(intentuserupdate);
                break;
        }
    }

    @Override
    public void BuyAgain(UserOrderBean item) {
        if (EmptyUtils.isEmpty(address)) {
            toast("请先认证工地");
        } else {
          /*  for (int i = 0; i < item.getDeliveryVo().getOrderVoList().size(); i++) {
                OnlineSupermarketOrderBean onlineSupermarketOrderBean = new OnlineSupermarketOrderBean();
                onlineSupermarketOrderBean.setNum(item.getDeliveryVo().getOrderVoList().get(i).getNum());
                onlineSupermarketOrderBean.setProductId(item.getDeliveryVo().getOrderVoList().get(i).getProductId());
                onlineSupermarketOrderBeanList.add(onlineSupermarketOrderBean);
                OnlineSupermarketBean onlineSupermarketBean = item.getDeliveryVo().getOrderVoList().get(i).getProduct();
                onlineSupermarketBean.setShopnumber(item.getDeliveryVo().getOrderVoList().get(i).getNum());
                onlineSupermarketBeanList.add(onlineSupermarketBean);
            }
            Gson gson = new Gson();
            getPresenter().getOnlineSupermarketGoodsOreder(gson.toJson(onlineSupermarketOrderBeanList));*/
        }
    }

    @Override
    public void ShowQrCodeImg(int model, String qrCode) {
        new ShowQrCodeView(getBaseContext(), recyclerView, qrCode, model);
    }

    @Override
    public void ShowLogistics(List<DeliveryInfoBean> deliveryInfoBeanList, int type) {
     /*   List<DeliveryInfoBean> deliveryInfoBeanList1 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            DeliveryInfoBean deliveryInfoBean = new DeliveryInfoBean();
            deliveryInfoBean.setInfo(TimeUtils.date2String(new Date()));
            deliveryInfoBeanList1.add(deliveryInfoBean);
        }*/
        new ShowLogisticsView(getBaseContext(), recyclerView, deliveryInfoBeanList, type);
    }

    @Override
    public void payOrder(UserOrderBean userOrderBean) {
        new PayWayView(getBaseContext(), recyclerView, userOrderBean);
    }

    @Override
    public void refundsOrder(int orderId) {
        new ShowRefundsView(getBaseContext(), recyclerView, orderId);
    }

    @Override
    public void UserOrderNoCopy(String orderNo) {
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("orderNo", orderNo);
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
        toast("复制成功");
    }


    public class ShowQrCodeView extends PopupWindow {
        public ShowQrCodeView(Context mContext, View parent, String qrCodeImg, int model) {
            View view = View.inflate(mContext, R.layout.view_show_code, null);
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
            showAtLocation(parent, Gravity.BOTTOM, 0, 0);
            TextView tv_code_title = view.findViewById(R.id.tv_code_title);
            if (model == 2) {
                tv_code_title.setText("我的兑换码");
            } else {
                tv_code_title.setText("我的取货码");
            }
            ImageView iv_show_code_close = (ImageView) view.findViewById(R.id.iv_show_code_close);
            ImageView iv_show_code = (ImageView) view.findViewById(R.id.iv_show_code);
            Glide.with(getBaseContext()).load(qrCodeImg).error(R.mipmap.userphotomr).into(iv_show_code);
            iv_show_code_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    refresh();
                }
            });
        }
    }

    public class ShowLogisticsView extends PopupWindow {
        public ShowLogisticsView(Context mContext, View parent, List<DeliveryInfoBean> deliveryInfoBeanList, int type) {
            View view = View.inflate(mContext, R.layout.view_show_logistics, null);
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
            showAtLocation(parent, Gravity.BOTTOM, 0, 0);
            TextView tv_code_title = view.findViewById(R.id.tv_code_title);
            if (type == 1) {
                tv_code_title.setText("查看物流");
            } else {
                tv_code_title.setText("查看状态");
            }
            ImageView iv_show_code_close = (ImageView) view.findViewById(R.id.iv_show_code_close);
            RecyclerView recycler_view_logistics = (RecyclerView) view.findViewById(R.id.recycler_view_logistics);
            UserLogisticsAdapter userLogisticsAdapter = new UserLogisticsAdapter(R.layout.item_user_logistics);
            recycler_view_logistics.setLayoutManager(new LinearLayoutManager(getBaseContext()));
            recycler_view_logistics.setAdapter(userLogisticsAdapter);
            userLogisticsAdapter.addData(deliveryInfoBeanList);
            iv_show_code_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }
    }

    public class ShowRefundsView extends PopupWindow {
        public ShowRefundsView(Context mContext, View parent, final int orderId) {
            View view = View.inflate(mContext, R.layout.view_show_refunds, null);
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
            showAtLocation(parent, Gravity.BOTTOM, 0, 0);
            TextView tv_code_title = view.findViewById(R.id.tv_code_title);
            tv_code_title.setText("退款原因");
            ImageView iv_show_code_close = (ImageView) view.findViewById(R.id.iv_show_code_close);
            List<RefundsBean> refundsBeanList = new ArrayList<>();
            refundsBeanList.add(addData("买多了/买错了", true));
            refundsBeanList.add(addData("后悔了，不想要了", false));
            refundsBeanList.add(addData("商家发错了", false));
            refundsBeanList.add(addData("商品与描述不符", false));
            refundsBeanList.add(addData("收到商品少件/破损或污渍", false));
            refundsBeanList.add(addData("其他", false));
            RecyclerView recycler_view_refunds = (RecyclerView) view.findViewById(R.id.recycler_view_refunds);
            if (refundsBeanList != null && refundsBeanList.size() > 0) {
                content = refundsBeanList.get(0).getR_content();
            }
            refundsAdapter = new RefundsAdapter(R.layout.item_refunds);
            refundsAdapter.setSelectId(0);
            recycler_view_refunds.setLayoutManager(new LinearLayoutManager(getBaseContext()));
            recycler_view_refunds.setAdapter(refundsAdapter);
            recycler_view_refunds.addItemDecoration(new RecycleViewDivider(getBaseContext(), LinearLayoutManager.VERTICAL));
            refundsAdapter.addData(refundsBeanList);
            refundsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                    content = refundsAdapter.getItem(i).getR_content();
                    refundsAdapter.setSelectId(i);
                    refundsAdapter.notifyDataSetChanged();
                }
            });
            TextView tv_refunds_sub = view.findViewById(R.id.tv_refunds_sub);
            tv_refunds_sub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (EmptyUtils.isEmpty(content)) {
                        toast("请选择退款原因");
                        return;
                    }
                    getPresenter().addUserOrderRefunds(content, orderId);
                }
            });
            iv_show_code_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }
    }

    public RefundsBean addData(String content, boolean isSelect) {
        RefundsBean refundsBean = new RefundsBean();
        refundsBean.setR_content(content);
        refundsBean.setSelcet(isSelect);
        return refundsBean;
    }

    public class PayWayView extends PopupWindow {

        public PayWayView(Context mContext, View parent, final UserOrderBean userOrderBean) {

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
            LinearLayout ll_popup = view.findViewById(R.id.ll_popup);
            View view_pay = view.findViewById(R.id.view_pay);
            final ImageView iv_wx_select = (ImageView) view.findViewById(R.id.iv_wx_select);
            final ImageView iv_zfb_select = (ImageView) view.findViewById(R.id.iv_zfb_select);
            final ImageView iv_xj_select = (ImageView) view.findViewById(R.id.iv_xj_select);
            if (userOrderBean != null) {
                mtotalPrice=userOrderBean.getFinalPrice();
                tv_pay_money.setText("￥" + userOrderBean.getFinalPrice());
            }
            lin_pay_wx.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iv_wx_select.setImageResource(R.mipmap.zfyx);
                    iv_zfb_select.setImageResource(R.mipmap.zfwx);
                    iv_xj_select.setImageResource(R.mipmap.zfwx);
                    if (userOrderBean != null && userOrderBean.getDelivery() != null) {
                        getPresenter().getUserConfirmAnOrderPayWx(userOrderBean.getDelivery().getId(), userOrderBean.getId(), userOrderBean.getDelivery().getTime());
                    } else {
                        toast("订单信息有误！");
                    }
                    dismiss();
                }
            });
            lin_pay_zfb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iv_wx_select.setImageResource(R.mipmap.zfwx);
                    iv_zfb_select.setImageResource(R.mipmap.zfyx);
                    iv_xj_select.setImageResource(R.mipmap.zfwx);
                    if (userOrderBean != null && userOrderBean.getDelivery() != null) {
                        getPresenter().getUserConfirmAnOrderPayZfb(userOrderBean.getDelivery().getId(), userOrderBean.getId(), userOrderBean.getDelivery().getTime());
                    } else {
                        toast("订单信息有误！");
                    }
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


    BroadcastReceiver broadcastReceiver;

    private void addWxPlayReceiver() {
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int type = intent.getIntExtra("type", 2);
                if (type == 1) {
                    Intent intent1 = new Intent(UserOrderActivity.this, UserPaySuccessActivity.class);
                    intent1.putExtra("dmoney", 0.00);
                    intent1.putExtra("jmoney", 0.00);
                    intent1.putExtra("totalPrice", mtotalPrice);
                    startActivityForResult(intent1,3);
                }
            }
        };
        IntentFilter intentToReceiveFilter = new IntentFilter();
        intentToReceiveFilter.addAction("wxplay");
        registerReceiver(broadcastReceiver, intentToReceiveFilter);
    }


    /**
     * call alipay sdk pay. 调用SDK支付
     */
    public void pay(final String ranking) {
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(UserOrderActivity.this);
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
                        Intent intent1 = new Intent(UserOrderActivity.this, UserPaySuccessActivity.class);
                        intent1.putExtra("dmoney", 0.00);
                        intent1.putExtra("jmoney", 0.00);
                        intent1.putExtra("totalPrice", mtotalPrice);
                        startActivityForResult(intent1,3);
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==3&&resultCode==1){
            refresh();
        }
    }
}
