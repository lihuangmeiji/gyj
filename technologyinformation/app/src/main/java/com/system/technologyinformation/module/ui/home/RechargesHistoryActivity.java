package com.system.technologyinformation.module.ui.home;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewStub;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.system.technologyinformation.R;
import com.system.technologyinformation.common.ui.BaseActivity;
import com.system.technologyinformation.model.LoginBean;
import com.system.technologyinformation.model.RechargesHistoryBean;
import com.system.technologyinformation.module.adapter.RechargesHistoryAdapter;
import com.system.technologyinformation.module.contract.RechargesHistoryContract;
import com.system.technologyinformation.module.presenter.RechargesHistoryPresenter;
import com.system.technologyinformation.utils.ActivityCollector;
import com.system.technologyinformation.widget.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class RechargesHistoryActivity extends BaseActivity<RechargesHistoryPresenter> implements RechargesHistoryContract.View, RechargesHistoryAdapter.RechargesHistoryInterface, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

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
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;

    int currentPage;
    int pageNumber = 10;
    RechargesHistoryAdapter rechargesHistoryAdapter;


    @Override
    protected int getContentView() {
        return R.layout.recharges_history;
    }

    @Override
    protected void initEventAndData() {
        commonTheme();
        ActivityCollector.addActivity(this);
        toolbarTitle.setText("充值记录");
        toolbarTitle.setTextColor(getBaseContext().getResources().getColor(R.color.white));
        toolbar.setNavigationIcon(R.mipmap.ic_return_video);
        iv_right.setVisibility(View.GONE);
        toolbar.setBackgroundColor(getResources().getColor(R.color.color37));

        rechargesHistoryAdapter = new RechargesHistoryAdapter(R.layout.item_recharges_order);
        rechargesHistoryAdapter.setRechargesHistoryInterface(this);
        rechargesHistoryAdapter.setOnLoadMoreListener(this, recyclerView);
        rechargesHistoryAdapter.setEnableLoadMore(true);
        rechargesHistoryAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);

        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerView.addItemDecoration(new SpaceItemDecoration(0, 30));
        recyclerView.setAdapter(rechargesHistoryAdapter);
        refresh();
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void setUserLoginResult(LoginBean loginBean) {
        if (loginBean != null) {
            clearUserInfo();
            SPUtils spUtils = new SPUtils("saveUser");
            spUtils.put("userInfo", new Gson().toJson(loginBean));
            loadUserInfo();
        } else {
            openLogin();
        }
    }

    @OnClick({R.id.toolbar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar:
                finish();
                break;
        }
    }


    @Override
    public void setRechargesHistoryResult(List<RechargesHistoryBean> rechargesHistory) {
        swipeLayout.setRefreshing(false);
        rechargesHistoryAdapter.setEnableLoadMore(true);
        swipeLayout.setEnabled(true);
        if (rechargesHistory == null || rechargesHistory.size() == 0) {
            rechargesHistoryAdapter.loadMoreEnd();
            if (currentPage == 1) {
                vs_showerror.setLayoutResource(R.layout.layout_empty);
                vs_showerror.setVisibility(View.VISIBLE);
                swipeLayout.setVisibility(View.GONE);
            }
            return;
        }
        rechargesHistoryAdapter.addData(rechargesHistory);
        vs_showerror.setVisibility(View.GONE);
        swipeLayout.setVisibility(View.VISIBLE);
        rechargesHistoryAdapter.loadMoreComplete();
        currentPage++;
        if (rechargesHistory.size() < pageNumber) {
            rechargesHistoryAdapter.loadMoreEnd();
            return;
        }
    }

    @Override
    public void chargeAgain(RechargesHistoryBean bean) {

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
                rechargesHistoryAdapter.getData().clear();
                rechargesHistoryAdapter.notifyDataSetChanged();
                getPresenter().getRechargesHistoryResult(currentPage, pageNumber);
            }
        }, 100);
    }

    @Override
    public void onLoadMoreRequested() {
        swipeLayout.setEnabled(false);
        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                getPresenter().getRechargesHistoryResult(currentPage, pageNumber);
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
    public void hiddenLoading() {
        super.hiddenLoading();
    }

    @Override
    public void showLoading() {
        super.showLoading();
    }


}
