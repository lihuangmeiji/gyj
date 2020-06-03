package com.system.technologyinformation.module.ui.entertainment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewStub;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.SPUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.system.technologyinformation.R;
import com.system.technologyinformation.common.ui.BaseActivity;
import com.system.technologyinformation.common.ui.SimpleActivity;
import com.system.technologyinformation.model.ConfirmOrderBean;
import com.system.technologyinformation.model.DeliveryBean;
import com.system.technologyinformation.model.LoginBean;
import com.system.technologyinformation.model.OnlineSupermarketBean;
import com.system.technologyinformation.model.OnlineSupermarketOrderBean;
import com.system.technologyinformation.model.OnlineSupermarketTypeBean;
import com.system.technologyinformation.model.TableReservationBean;
import com.system.technologyinformation.model.http.base.BaseResponseInfo;
import com.system.technologyinformation.module.adapter.TableReservationAdapter;
import com.system.technologyinformation.module.contract.TableReservationContract;
import com.system.technologyinformation.module.presenter.TableReservationPresenter;
import com.system.technologyinformation.module.ui.account.LoginActivity;
import com.system.technologyinformation.module.ui.users.UserAddressActivity;
import com.system.technologyinformation.utils.ActivityCollector;
import com.system.technologyinformation.widget.GridDividerItemDecoration;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class TableReservationActivity extends BaseActivity<TableReservationPresenter> implements TableReservationContract.View, TableReservationAdapter.ShopCarModifyCountInterface, BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
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
    @BindView(R.id.tv_pay_money)
    TextView tv_pay_money;
    @BindView(R.id.tv_dg_money)
    TextView tv_dg_money;
    @BindView(R.id.tv_pay)
    TextView tv_pay;
    @BindView(R.id.tv_buy_number)
    TextView tv_buy_number;

    TableReservationAdapter tableReservationAdapter;


    List<OnlineSupermarketBean> tableReservationBeanList;
    List<OnlineSupermarketOrderBean> onlineSupermarketOrderBeanList;
    int currentPage;
    int pageNumber = 10;
    private double mtotalPrice = 0.00;
    private int number = 0;
    NumberFormat nf;
    String address;
    int operation = 0;

    @Override
    protected int getContentView() {
        return R.layout.activity_table_reservation;
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initEventAndData() {
        commonTheme();
        isClose=false;
        ActivityCollector.addActivity(this);
        nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(2);
        toolbarTitle.setText("订餐");
        toolbar.setNavigationIcon(R.mipmap.ic_return);
        iv_right.setVisibility(View.GONE);
        tableReservationBeanList = new ArrayList<>();
        onlineSupermarketOrderBeanList = new ArrayList<>();
        tableReservationAdapter = new TableReservationAdapter(R.layout.item_table_reservation);
        tableReservationAdapter.setShopCarModifyCountInterface(this);
        tableReservationAdapter.setOnLoadMoreListener(this, recyclerView);
        tableReservationAdapter.setEnableLoadMore(true);
        tableReservationAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        recyclerView.setLayoutManager(new GridLayoutManager(getBaseContext(), 2));
        recyclerView.addItemDecoration(new GridDividerItemDecoration(20, getBaseContext().getResources().getColor(R.color.color25)));
        recyclerView.setAdapter(tableReservationAdapter);
        refresh();
        tv_pay_money.setText("￥0.00");
        tv_dg_money.setText("(积分可抵扣￥0.00)");
        if (login != null) {
            getPresenter().getUpdateUserInfoResult(login.getId());
        }
        tv_pay.setText("立即下单");
    }


    @OnClick({R.id.toolbar,
            R.id.tv_pay
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar:
                finish();
                break;
            case R.id.tv_pay:
                if (login != null) {
                    if (EmptyUtils.isEmpty(address)) {
                        new AddressRzView(getBaseContext(), recyclerView);
                        return;
                    }
                    if (onlineSupermarketOrderBeanList.size() == 0) {
                        toast("请先选择商品!");
                        return;
                    }
                   // operation = 2;
                    Gson gson = new Gson();
                    Intent intent = new Intent(TableReservationActivity.this, UserConfirmAnOrderActivity.class);
                    intent.putExtra("onlineSupermarketBeanList", (Serializable) tableReservationBeanList);
                    intent.putExtra("orderData",gson.toJson(onlineSupermarketOrderBeanList));
                    intent.putExtra("address", address);
                    intent.putExtra("mtotalPrice", mtotalPrice);
                    intent.putExtra("goodstype", 1);
                    startActivityForResult(intent, 2);
                    //showLoading();
                    //getPresenter().getOnlineSupermarketGoodsOreder(gson.toJson(onlineSupermarketOrderBeanList));
                } else {
                    openLogin();
                }
                break;
        }
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
    public void setTableReservationList(List<OnlineSupermarketBean> onlineSupermarketGoodsList) {
        swipeLayout.setRefreshing(false);
        tableReservationAdapter.setEnableLoadMore(true);
        swipeLayout.setEnabled(true);
        if (onlineSupermarketGoodsList == null || onlineSupermarketGoodsList.size() == 0) {
            tableReservationAdapter.loadMoreEnd();
            if (currentPage == 1) {
                vs_showerror.setLayoutResource(R.layout.layout_empty);
                vs_showerror.setVisibility(View.VISIBLE);
                swipeLayout.setVisibility(View.GONE);
            }
            return;
        }
        tableReservationAdapter.addData(onlineSupermarketGoodsList);
        if (onlineSupermarketGoodsList.size() < pageNumber) {
            tableReservationAdapter.loadMoreEnd();
            return;
        }
        tableReservationAdapter.loadMoreComplete();
        currentPage++;
        vs_showerror.setVisibility(View.GONE);
        swipeLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void setOnlineSupermarketGoodsOreder(ConfirmOrderBean confirmOrderBean) {
        hiddenLoading();
        if (confirmOrderBean != null) {
            Intent intent = new Intent(TableReservationActivity.this, UserConfirmAnOrderActivity.class);
            intent.putExtra("onlineSupermarketBeanList", (Serializable) tableReservationBeanList);
            intent.putExtra("address", address);
            intent.putExtra("goodstype", 1);
            intent.putExtra("mtotalPrice", confirmOrderBean.getTotalPrice());
            intent.putExtra("deliveryId", confirmOrderBean.getDeliveryId());
            intent.putExtra("orderId", confirmOrderBean.getId());
            startActivityForResult(intent, 2);
        } else {
            toast("预订单生成失败");
        }
    }

    @Override
    public void refreshUserTimeResult(BaseResponseInfo result) {
        if (result.getCode() == 0) {
            toast("无响应，请重试!");
        } else {
            openLogin();
        }
    }

    @Override
    public void setUserLoginResult(LoginBean loginBean) {
        if (loginBean != null) {
            clearUserInfo();
            SPUtils spUtils = new SPUtils("saveUser");
            spUtils.put("userInfo", new Gson().toJson(loginBean));
            loadUserInfo();
            if (operation == 1) {
                getPresenter().getTableReservationList(null, currentPage, pageNumber);
            } else if (operation == 2) {
                Gson gson = new Gson();
                getPresenter().getOnlineSupermarketGoodsOreder(gson.toJson(onlineSupermarketOrderBeanList));
            }
        } else {
            openLogin();
        }
    }

    @Override
    public void setUpdateUserInfoResult(LoginBean loginBean) {
        if (loginBean != null) {
            clearUserInfo();
            SPUtils spUtils = new SPUtils("saveUser");
            spUtils.put("userInfo", new Gson().toJson(loginBean));
            loadUserInfo();
            if (login != null && login.getConstructionPlace() != null) {
                tv_pay.setSelected(true);
                address = login.getConstructionPlace().getName();
            } else {
                tv_pay.setSelected(false);
                address = null;
            }
        } else {
            clearUserInfo();
            loadUserInfo();
            if (login != null && login.getConstructionPlace() != null) {
                tv_pay.setSelected(true);
                address = login.getConstructionPlace().getName();
            } else {
                tv_pay.setSelected(false);
                address = null;
            }
        }
    }

    @Override
    public void onRefresh() {
        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                operation = 1;
                currentPage = 1;
                tableReservationAdapter.getData().clear();
                tableReservationAdapter.notifyDataSetChanged();
                getPresenter().getTableReservationList(null, pageNumber, currentPage);
            }
        }, 100);
    }

    @Override
    public void onLoadMoreRequested() {
        swipeLayout.setEnabled(false);
        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                operation = 1;
                getPresenter().getTableReservationList(null, pageNumber, currentPage);
            }
        }, 100);
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


    @Override
    public void doIncrease() {
        tableReservationAdapter.notifyDataSetChanged();
        calulate();
    }

    @Override
    public void doDecrease() {
        tableReservationAdapter.notifyDataSetChanged();
        calulate();
    }

    @Override
    public void childDelete(OnlineSupermarketBean onlineSupermarketBean) {

    }

    @Override
    public void checkChild(boolean isChecked) {
    }


    /**
     * 计算商品总价格，操作步骤
     * 1.先清空全局计价,计数
     * 2.遍历所有的子元素，只要是被选中的，就进行相关的计算操作
     * 3.给textView填充数据
     */
    private void calulate() {
        mtotalPrice = 0.00;
        number = 0;
        onlineSupermarketOrderBeanList.clear();
        tableReservationBeanList.clear();
        for (int i = 0; i < tableReservationAdapter.getData().size(); i++) {
            OnlineSupermarketBean onlineSupermarketBean = tableReservationAdapter.getData().get(i);
            if (onlineSupermarketBean.isSc_isChoosed()) {
                mtotalPrice += onlineSupermarketBean.getCurrentPrice() * onlineSupermarketBean.getShopnumber();
                OnlineSupermarketOrderBean onlineSupermarketOrderBean = new OnlineSupermarketOrderBean();
                onlineSupermarketOrderBean.setNum(onlineSupermarketBean.getShopnumber());
                onlineSupermarketOrderBean.setProductId(onlineSupermarketBean.getId());
                onlineSupermarketOrderBeanList.add(onlineSupermarketOrderBean);
                tableReservationBeanList.add(onlineSupermarketBean);
                number = number + onlineSupermarketBean.getShopnumber();
            }
        }
        DecimalFormat df = new DecimalFormat("######0.00");
        tv_pay_money.setText("￥" + df.format(mtotalPrice) + "");
        tv_dg_money.setText("(积分可抵扣￥" + df.format(mtotalPrice / 10) + ")");
        tv_buy_number.setText("" + number);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1) {
            loadUserInfo();
            if (login != null && login.getConstructionPlace() != null) {
                tv_pay.setSelected(true);
                address = login.getConstructionPlace().getName();
            } else {
                tv_pay.setSelected(false);
                address = null;
            }
            Intent intent = new Intent("userupdate");
            sendBroadcast(intent);
        }

        if (requestCode == 2) {
            tableReservationBeanList.clear();
            onlineSupermarketOrderBeanList.clear();
            tv_pay_money.setText("￥0.00");
            number = 0;
            onlineSupermarketOrderBeanList.clear();
            tv_buy_number.setText("" + number);
            refresh();
        }

        if (requestCode == 3 && resultCode == 1) {
            getPresenter().getUpdateUserInfoResult(login.getId());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        operation = 0;
    }

    Intent intent;

    public class AddressRzView extends PopupWindow {

        public AddressRzView(final Context mContext, View parent) {

            View view = View.inflate(mContext, R.layout.view_address_rz, null);
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
                    intent = new Intent(mContext, UserAddressActivity.class);
                    startActivityForResult(intent, 3);
                }
            });
        }
    }


}
