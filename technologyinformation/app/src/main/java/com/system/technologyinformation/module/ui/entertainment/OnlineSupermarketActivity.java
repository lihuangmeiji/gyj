package com.system.technologyinformation.module.ui.entertainment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.SPUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.system.technologyinformation.R;


import com.system.technologyinformation.common.ui.BaseActivity;
import com.system.technologyinformation.common.ui.SimpleActivity;
import com.system.technologyinformation.model.DeliveryBean;
import com.system.technologyinformation.model.LoginBean;
import com.system.technologyinformation.model.OnlineSupermarketBean;
import com.system.technologyinformation.model.OnlineSupermarketOrderBean;
import com.system.technologyinformation.model.OnlineSupermarketTypeBean;
import com.system.technologyinformation.model.http.base.BaseResponseInfo;
import com.system.technologyinformation.module.adapter.OnlineSupermarketAdapter;
import com.system.technologyinformation.module.adapter.OnlineSupermarketTitleAdapter;
import com.system.technologyinformation.module.adapter.OnlineSupermarketTypeAdapter;
import com.system.technologyinformation.module.contract.OnlineSupermarketContract;
import com.system.technologyinformation.module.presenter.OnlineSupermarketPresenter;
import com.system.technologyinformation.module.ui.MainActivity;
import com.system.technologyinformation.module.ui.account.LoginActivity;
import com.system.technologyinformation.utils.ActivityCollector;
import com.system.technologyinformation.widget.GridDividerItemDecoration;
import com.system.technologyinformation.widget.RecycleViewDivider;
import com.wenld.multitypeadapter.MultiTypeAdapter;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class OnlineSupermarketActivity extends BaseActivity<OnlineSupermarketPresenter> implements OnlineSupermarketContract.View, OnlineSupermarketAdapter.ShopCarModifyCountInterface {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_right)
    TextView iv_right;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.recycler_view_type)
    RecyclerView recycler_view_type;
    @BindView(R.id.vs_showerror)
    ViewStub vs_showerror;
    @BindView(R.id.lin_content)
    LinearLayout lin_content;
    @BindView(R.id.tv_pay_money)
    TextView tv_pay_money;
    @BindView(R.id.tv_dg_money)
    TextView tv_dg_money;
    @BindView(R.id.tv_pay)
    TextView tv_pay;
    @BindView(R.id.tv_buy_number)
    TextView tv_buy_number;


    int currentPage;
    int pageNumber = 10;
    private double mtotalPrice = 0.00;
    private int number = 0;
    NumberFormat nf;
    OnlineSupermarketTypeAdapter onlineSupermarketTypeAdapter;
    List<OnlineSupermarketTypeBean> onlineSupermarketTypeBeanList;
    OnlineSupermarketAdapter onlineSupermarketAdapter;
    List<OnlineSupermarketOrderBean> onlineSupermarketOrderBeanList;
    List<OnlineSupermarketBean> onlineSupermarketBeanList;
    private MultiTypeAdapter adapter;
    List<Object> items;

    String address;


    @Override
    protected int getContentView() {
        return R.layout.activity_online_supermarket;
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
        toolbarTitle.setText("线上超市");
        toolbar.setNavigationIcon(R.mipmap.ic_return);
        iv_right.setVisibility(View.GONE);
        onlineSupermarketOrderBeanList = new ArrayList<>();
        onlineSupermarketBeanList = new ArrayList<>();
        items = new ArrayList<>();
        adapter = new MultiTypeAdapter();
        onlineSupermarketAdapter = new OnlineSupermarketAdapter();
        onlineSupermarketAdapter.setShopCarModifyCountInterface(this);
        adapter.register(String.class, new OnlineSupermarketTitleAdapter());
        adapter.register(OnlineSupermarketBean.class, onlineSupermarketAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView1, int newState) {
                super.onScrollStateChanged(recyclerView1, newState);
                if (mShouldScroll && RecyclerView.SCROLL_STATE_IDLE == newState) {
                    mShouldScroll = false;
                    smoothMoveToPosition(recyclerView, mToPosition);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //右侧滑动到的item索引
                int firstVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                Log.i("当前item索引", "onScrolled: " + firstVisibleItemPosition);
                //分类产品累计相加
                if (mShouldScroll == false) {
                    int index = 0;
                    for (int i = 0; i < onlineSupermarketTypeBeanList.size(); i++) {
                        //当前i的分类的大小
                        index = index + onlineSupermarketTypeBeanList.get(i).getProducts().size() + 1;
                        Log.i("index", "index: " + index);
                        //当i相等于分类的大小 不用计算i的下一个
                        if (i == onlineSupermarketTypeBeanList.size() - 1) {
                            onlineSupermarketTypeAdapter.setSelectId(onlineSupermarketTypeAdapter.getItem(i).getId());
                            onlineSupermarketTypeAdapter.notifyDataSetChanged();
                            break;
                        } else {
                            //下一个index的大小
                            int nextindex = index + onlineSupermarketTypeBeanList.get(i + 1).getProducts().size() + 1;
                            Log.i("nextindex", "nextindex: " + nextindex);
                            if (index > firstVisibleItemPosition && firstVisibleItemPosition < nextindex) {
                                onlineSupermarketTypeAdapter.setSelectId(onlineSupermarketTypeAdapter.getItem(i).getId());
                                onlineSupermarketTypeAdapter.notifyDataSetChanged();
                                break;
                            }
                        }
                    }
                }
            }
        });
        //recyclerView.addItemDecoration(new RecycleViewDivider(getBaseContext(), LinearLayoutManager.VERTICAL));
        onlineSupermarketTypeBeanList = new ArrayList<>();
        onlineSupermarketTypeAdapter = new OnlineSupermarketTypeAdapter(R.layout.item_table_reservation_type);
        onlineSupermarketTypeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //onlineSupermarketTypeAdapter.setSelectId(onlineSupermarketTypeAdapter.getItem(position).getId());
                //onlineSupermarketTypeAdapter.notifyDataSetChanged();
                int index = 0;
                for (int i = 0; i < position; i++) {
                    index = index + onlineSupermarketTypeBeanList.get(i).getProducts().size() + 1;
                }
                smoothMoveToPosition(recyclerView, index);
                //recyclerView.smoothScrollToPosition(index);
            }
        });
        recycler_view_type.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recycler_view_type.setAdapter(onlineSupermarketTypeAdapter);
        getPresenter().getOnlineSupermarketGoodsAndTypeList();
        tv_pay_money.setText("￥0.00");
        tv_dg_money.setText("(积分可抵扣￥0.00)");
       /* if (login != null && login.getCareerInfo() != null && login.getCareerInfo().getAddressDetail() != null) {
            //tv_pay.setEnabled(true);
            tv_pay.setSelected(true);
            address = login.getCareerInfo().getAddressDetail();
        } else {
            //tv_pay.setEnabled(false);
            tv_pay.setSelected(false);
            address = null;
        }*/
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
                        toast("请进行工地认证!");
                        return;
                    }
                    if (onlineSupermarketOrderBeanList.size() == 0) {
                        toast("请先选择商品!");
                        return;
                    }
                    Gson gson = new Gson();
                    getPresenter().getOnlineSupermarketGoodsOreder(gson.toJson(onlineSupermarketOrderBeanList));
                } else {
                    Intent intent = new Intent(OnlineSupermarketActivity.this, LoginActivity.class);
                    startActivityForResult(intent, 1);
                }
                break;
        }
    }

    @Override
    public void setOnlineSupermarketGoodsList(List<OnlineSupermarketBean> onlineSupermarketGoodsList) {

    }

    @Override
    public void setOnlineSupermarketGoodsTypeList(List<OnlineSupermarketTypeBean> onlineSupermarketGoodsTypeList) {
        onlineSupermarketTypeBeanList = onlineSupermarketGoodsTypeList;
        onlineSupermarketTypeAdapter.addData(onlineSupermarketTypeBeanList);
        onlineSupermarketTypeAdapter.notifyDataSetChanged();
    }

    @Override
    public void setOnlineSupermarketGoodsAndTypeList(List<OnlineSupermarketTypeBean> onlineSupermarketGoodsTypeList) {
        if (onlineSupermarketGoodsTypeList != null && onlineSupermarketGoodsTypeList.size() > 0) {
            onlineSupermarketTypeBeanList = onlineSupermarketGoodsTypeList;
            for (int i = 0; i < onlineSupermarketTypeBeanList.size(); i++) {
                items.add(onlineSupermarketTypeBeanList.get(i).getName());
                items.addAll(onlineSupermarketTypeBeanList.get(i).getProducts());
            }
            adapter.setItems(items);
            adapter.notifyDataSetChanged();
            onlineSupermarketTypeAdapter.setSelectId(onlineSupermarketTypeBeanList.get(0).getId());
            onlineSupermarketTypeAdapter.addData(onlineSupermarketTypeBeanList);
            onlineSupermarketTypeAdapter.notifyDataSetChanged();
            vs_showerror.setVisibility(View.GONE);
            lin_content.setVisibility(View.VISIBLE);
        } else {
            vs_showerror.setLayoutResource(R.layout.layout_empty);
            vs_showerror.setVisibility(View.VISIBLE);
            lin_content.setVisibility(View.GONE);
        }
    }

    @Override
    public void setOnlineSupermarketGoodsOreder(List<DeliveryBean> deliveryBeanList) {
        if (deliveryBeanList != null && deliveryBeanList.size() > 0) {
            Intent intent = new Intent(OnlineSupermarketActivity.this, UserConfirmAnOrderActivity.class);
            intent.putExtra("onlineSupermarketBeanList", (Serializable) onlineSupermarketBeanList);
            intent.putExtra("address", address);
            intent.putExtra("goodstype", 0);
            intent.putExtra("deliveryId", deliveryBeanList.get(0).getDeliveryId());
            startActivity(intent);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1) {
            loadUserInfo();
           /* if (login != null && login.getCareerInfo() != null && login.getCareerInfo().getAddressProvince() != null) {
                //tv_pay.setEnabled(true);
                tv_pay.setSelected(true);
                address = login.getCareerInfo().getAddressDetail();
            } else {
                //tv_pay.setEnabled(false);

                tv_pay.setSelected(false);
                address = null;
            }*/
            Intent intent = new Intent("userupdate");
            sendBroadcast(intent);
        }
    }

    //目标项是否在最后一个可见项之后
    private boolean mShouldScroll;
    //记录目标项位置
    private int mToPosition;

    /**
     * 滑动到指定位置
     */
    private void smoothMoveToPosition(RecyclerView mRecyclerView, final int position) {
        // 第一个可见位置
        int firstItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(0));
        // 最后一个可见位置
        int lastItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(mRecyclerView.getChildCount() - 1));
        if (position < firstItem) {
            // 第一种可能:跳转位置在第一个可见位置之前，使用smoothScrollToPosition
            mRecyclerView.smoothScrollToPosition(position);
        } else if (position <= lastItem) {
            // 第二种可能:跳转位置在第一个可见位置之后，最后一个可见项之前
            int movePosition = position - firstItem;
            if (movePosition >= 0 && movePosition < mRecyclerView.getChildCount()) {
                int top = mRecyclerView.getChildAt(movePosition).getTop();
                // smoothScrollToPosition 不会有效果，此时调用smoothScrollBy来滑动到指定位置
                mRecyclerView.smoothScrollBy(0, top);
            }
        } else {
            // 第三种可能:跳转位置在最后可见项之后，则先调用smoothScrollToPosition将要跳转的位置滚动到可见位置
            // 再通过onScrollStateChanged控制再次调用smoothMoveToPosition，执行上一个判断中的方法
            mRecyclerView.smoothScrollToPosition(position);
            mToPosition = position;
            mShouldScroll = true;
        }
    }

    @Override
    public void toast(String msg) {
        super.toast(msg);
    }

    @Override
    public void doIncrease() {
        adapter.notifyDataSetChanged();
        calulate();
    }

    @Override
    public void doDecrease() {
        adapter.notifyDataSetChanged();
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
        onlineSupermarketBeanList.clear();
        for (int i = 0; i < onlineSupermarketTypeBeanList.size(); i++) {
            OnlineSupermarketTypeBean onlineSupermarketTypeBean = onlineSupermarketTypeBeanList.get(i);
            for (int j = 0; j < onlineSupermarketTypeBean.getProducts().size(); j++) {
                OnlineSupermarketBean onlineSupermarketBean = onlineSupermarketTypeBean.getProducts().get(j);
                if (onlineSupermarketBean.isSc_isChoosed()) {
                    number = number + onlineSupermarketBean.getShopnumber();
                    mtotalPrice += onlineSupermarketBean.getCurrentPrice() * onlineSupermarketBean.getShopnumber();
                    OnlineSupermarketOrderBean onlineSupermarketOrderBean = new OnlineSupermarketOrderBean();
                    onlineSupermarketOrderBean.setNum(onlineSupermarketBean.getShopnumber());
                    onlineSupermarketOrderBean.setProductId(onlineSupermarketBean.getId());
                    onlineSupermarketOrderBeanList.add(onlineSupermarketOrderBean);
                    onlineSupermarketBeanList.add(onlineSupermarketBean);
                }
            }
        }
        DecimalFormat df = new DecimalFormat("######0.00");
        tv_pay_money.setText("￥" + df.format(mtotalPrice) + "");
        tv_dg_money.setText("(积分可抵扣￥" + df.format(mtotalPrice / 10) + ")");
        tv_buy_number.setText("" + number);
    }

    @Override
    public void showErrorMsg(String msg, int code) {
        super.showErrorMsg(msg, code);
        if (code == -1) {
            getPresenter().refreshUserTime();
            return;
        } else if (code == 405) {
            lin_content.setVisibility(View.GONE);
            vs_showerror.setLayoutResource(R.layout.layout_no_network);
            //vs_showerror.inflate();
            vs_showerror.setVisibility(View.VISIBLE);
            LinearLayout lin_load = (LinearLayout) findViewById(R.id.lin_load);
            lin_load.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getPresenter().getOnlineSupermarketGoodsAndTypeList();
                }
            });
        } else {
            lin_content.setVisibility(View.GONE);
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


}
